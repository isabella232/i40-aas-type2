package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.persistence.entities.AssetAdministrationShellEntity;
import com.sap.i40aas.datamanager.persistence.entities.AssetEntity;
import com.sap.i40aas.datamanager.persistence.repositories.AssetAdministrationShellRepository;
import com.sap.i40aas.datamanager.persistence.repositories.AssetRepository;
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
  private AssetsObjectsService assetsObjectsServiceService;
  @Autowired
  private ConceptDescriptionObjectsService conceptDescriptionObjectsService;
  @Autowired
  private AssetAdminShellObjectsService assetAdminShellObjectsService;
  @Autowired
  private AssetRepository assetRepo;
  @Autowired
  private AssetAdministrationShellRepository assetAdministrationShellRepository;


  public void addAASEnv(AssetAdministrationShellEnv env) {

    log.debug("Upserting aasEnv");

    this.createAASs(env.getAssetAdministrationShells());
    this.createAssets(env.getAssets());

    //get the list of assets
    ArrayList<Asset> assetsList = env.getAssets();

//    for every AssetAdminShell in the Env find its associated asset
    env.getAssetAdministrationShells().forEach(AssetAdministrationShell -> {
      AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(AssetAdministrationShell.getIdentification().getId()).get();

//      get the AssetId from the Reference Object in the AssetAdminShell
      String assetId = AssetAdministrationShell.getAsset().getKeys().stream()
        .filter(key -> key.getType().equals(KeyElementsEnum.Asset)).findFirst().get().getValue();

      //read the asset with this Id from the DB
      AssetEntity assetEntityFound = assetRepo.findById(assetId).get();
      //assign this AAS to the asset
      assetEntityFound.addAssetAdminShell(aasEntityFound);

      assetRepo.save(assetEntityFound);

    });
    createSubmodels(env.getSubmodels());


    createConceptDescriptions(env.getConceptDescriptions());

  }

  public void createSubmodels(List<Submodel> submodelList) {
    submodelList.forEach(submodel -> submodelService.addSubmodel(submodel));
  }

  public void createAssets(List<Asset> assetList) {
    assetList.forEach(asset -> assetsObjectsServiceService.addAsset(asset, null));
  }

  private void assignAssetAdminShellToAsset(Asset asset, AssetAdministrationShell aas) {
    AssetEntity asE = new AssetEntity(asset.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeAsset(asset));
    AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(aas.getIdentification().getId()).get();

    asE.addAssetAdminShell(aasEntityFound);

    assetRepo.save(asE);
  }

  public void createConceptDescriptions(List<ConceptDescription> conceptDescriptionList) {
    conceptDescriptionList.forEach(conceptDescription -> conceptDescriptionObjectsService.addConceptDescription(conceptDescription));
  }

  public void createAASs(List<AssetAdministrationShell> assetAdministrationShellList) {
    assetAdministrationShellList.forEach(assetAdministrationShell -> assetAdminShellObjectsService.addAssetAdministrationShell(assetAdministrationShell));
  }

}
