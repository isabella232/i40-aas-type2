package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.persistence.entities.AssetAdministrationShellEntity;
import com.sap.i40aas.datamanager.persistence.entities.AssetEntity;
import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.AssetAdministrationShellRepository;
import com.sap.i40aas.datamanager.persistence.repositories.AssetRepository;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import identifiables.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import types.KeyElementsEnum;
import utils.AASObjectsDeserializer;

import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class AASEnvObjectsService {

  @Autowired
  private SubmodelObjectsService submodelService;
  @Autowired
  private AssetsObjectsService assetsObjectsService;
  @Autowired
  private ConceptDescriptionObjectsService conceptDescriptionObjectsService;
  @Autowired
  private AssetAdminShellObjectsService assetAdminShellObjectsService;
  @Autowired
  private AssetRepository assetRepo;
  @Autowired
  private SubmodelRepository submodelRepo;
  @Autowired
  private AssetAdministrationShellRepository assetAdministrationShellRepository;


  public void addAASEnv(AssetAdministrationShellEnv env) {

    log.debug("Upserting aasEnv");

    this.createAASs(env.getAssetAdministrationShells());
    this.createAssets(env.getAssets());
    createSubmodels(env.getSubmodels());
    createConceptDescriptions(env.getConceptDescriptions());


    //get the list of assets
    ArrayList<Asset> assetsList = env.getAssets();

//    for every AssetAdminShell in the Env
    env.getAssetAdministrationShells().forEach(AssetAdministrationShell -> {

      assignAssetAdminShellToAsset(AssetAdministrationShell);
      //TODO: better define submodels as empty list to avoid nulls
      if (AssetAdministrationShell.getSubmodels() != null)
        this.assignAssetAdminShellToSubmodel(AssetAdministrationShell);

    });


  }

  public AssetAdministrationShellEnv getAASEnv(String aasId) {

    AssetAdministrationShellEnv aasEnv = new AssetAdministrationShellEnv();
    AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(aasId).get();
    AssetEntity assetEntityFound = aasEntityFound.getAsset();
    //Find the related Submodels for this AAS
    List<SubmodelEntity> submodelEntities = aasEntityFound.submodels;
    ArrayList<Submodel> submodelsFound = new ArrayList<>();

    submodelEntities.forEach(submodelEntity -> {
      submodelsFound.add(submodelEntity.getSubmodelDeserialised());
    });

    //find the related Asset
    AssetAdministrationShell aasfound = AASObjectsDeserializer.Companion.deserializeAAS(aasEntityFound.getAasObj());
    Asset assetFound = AASObjectsDeserializer.Companion.deserializeAsset(assetEntityFound.getAssetObj());

    aasEnv.setAssetAdministrationShells(new ArrayList<AssetAdministrationShell>() {
      {
        add(aasfound);
      }
    });
    aasEnv.setAssets(new ArrayList<Asset>() {
      {
        add(assetFound);
      }
    });
    aasEnv.setSubmodels(submodelsFound);

    return aasEnv;
  }


  public void createSubmodels(List<Submodel> submodelList) {
    submodelList.forEach(submodel -> submodelService.addSubmodel(submodel));
  }

  public void createAssets(List<Asset> assetList) {
    assetList.forEach(asset -> assetsObjectsService.addAsset(asset, null));
  }

  private void assignAssetAdminShellToAsset(AssetAdministrationShell aas) {
    AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(aas.getIdentification().getId()).get();
//      get the AssetId from the Reference Object in the AssetAdminShell
    String assetId = aas.getAsset().getKeys().stream()
      .filter(key -> key.getType().equals(KeyElementsEnum.Asset)).findFirst().get().getValue();

    //read the asset with this Id from the DB
    AssetEntity assetEntityFound = assetRepo.findById(assetId).get();
    //assign this AAS to the asset
    assetEntityFound.addAssetAdminShell(aasEntityFound);
    assetRepo.save(assetEntityFound);
  }

  private void assignAssetAdminShellToSubmodel(AssetAdministrationShell aas) {
    AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(aas.getIdentification().getId()).get();

    //add a relation for each submodel referenced by the AssetAdministrationShell
    aas.getSubmodels().forEach(reference -> {
      //read the submodel Id from the reference
      String submodelId = reference.getKeys().stream()
        .filter(key -> key.getType().equals(KeyElementsEnum.Submodel)).findFirst().get().getValue();
      //read the submodel with this Id from the DB
      SubmodelEntity submodelEntityFound = submodelRepo.findById(submodelId).get();
      //assign this submodel to the AAS

      //TODO: this can be optimized so that the whole list of submodels is added with one call (setSubmodels=
      aasEntityFound.addSubmodel(submodelEntityFound);

    });
    assetAdministrationShellRepository.save(aasEntityFound);


  }

  public void createConceptDescriptions(List<ConceptDescription> conceptDescriptionList) {
    conceptDescriptionList.forEach(conceptDescription -> conceptDescriptionObjectsService.addConceptDescription(conceptDescription));
  }

  public void createAASs(List<AssetAdministrationShell> assetAdministrationShellList) {
    assetAdministrationShellList.forEach(assetAdministrationShell -> assetAdminShellObjectsService.addAssetAdministrationShell(assetAdministrationShell));
  }


}
