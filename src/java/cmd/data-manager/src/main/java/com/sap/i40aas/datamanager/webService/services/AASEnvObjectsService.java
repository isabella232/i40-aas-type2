package com.sap.i40aas.datamanager.webService.services;

import identifiables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;


@Service
public class AASEnvObjectsService {

  @Autowired
  private SubmodelsObjectsService submodelService;

  public ArrayList<String> addAASEnv(AssetAdministrationShellEnv env) {

    ArrayList list = new ArrayList<String>();
    list.addAll(env.getSubmodels());
    list.addAll(Arrays.asList(env.getAssets()));
    list.addAll(env.getAssetAdministrationShells());
    list.addAll(env.getConceptDescriptions());


    return list;

  }

  public void addSubmodel(Submodel submodel) {
    submodelService.addSubmodel(submodel);
  }

  public void addAsset(Asset asset) {
    throw new NotImplementedException();
  }

  public void addConceptDescription(ConceptDescription cd) {
    throw new NotImplementedException();
  }

  public void addAAS(AssetAdministrationShell aasObj) {
    throw new NotImplementedException();
  }

}
