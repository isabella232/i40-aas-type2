package com.sap.i40aas.datamanager.webService.services;

import identifiables.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void addAASEnv(AssetAdministrationShellEnv env) {

    log.debug("Upserting aasEnv");

    this.createSubmodels(env.getSubmodels());
    this.createAssets(env.getAssets());
    this.createAASs(env.getAssetAdministrationShells());
    this.createConceptDescriptions(env.getConceptDescriptions());

  }

  public void createSubmodels(List<Submodel> submodelList) {

    submodelList.forEach(submodel -> submodelService.addSubmodel(submodel));

  }

  public void createAssets(List<Asset> assetList) {
    assetList.forEach(asset -> assetsObjectsServiceService.addAsset(asset));
  }

  public void createConceptDescriptions(List<ConceptDescription> conceptDescriptionList) {
    conceptDescriptionList.forEach(conceptDescription -> conceptDescriptionObjectsService.addConceptDescription(conceptDescription));
  }

  public void createAASs(List<AssetAdministrationShell> assetAdministrationShellList) {
    assetAdministrationShellList.forEach(assetAdministrationShell -> assetAdminShellObjectsService.addAssetAdministrationShell(assetAdministrationShell));
  }

}
