package com.sap.i40aas.datamanager.webService.services;

import identifiables.AssetAdministrationShell;
import org.springframework.stereotype.Service;
import utils.SampleAASFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AssetAdminShellObjectsService {

  private AssetAdminShellObjectsService aasObjectService;


  private final List<AssetAdministrationShell> aasArray = new ArrayList<>(Arrays.asList(
    SampleAASFactory.Companion.getSampleAAS("sampleAas_1"),
    SampleAASFactory.Companion.getSampleAAS("sampleAas_2")
  ));


  public AssetAdministrationShell getAssetAdministrationShell(String aasId) {
//TODO: replace idshort with the following

    AssetAdministrationShell aasFound = aasArray.stream().filter(submodel -> submodel.getIdentification().getId().equals(aasId)).findFirst().get();
    return aasFound;
  }


  public List<AssetAdministrationShell> getAllAASs() {
    return aasArray;
  }

  public void updateAAS(String aasId, AssetAdministrationShell aas) {
    if (aasArray.stream().anyMatch(aas1 -> aas.getIdentification().getId().equals(aasId))) {
      aasArray.set(aasArray.indexOf(aasArray.stream().filter(aas1 -> aas.getIdentification().getId().equals(aasId)).findFirst().get()), aas);
    } else
      aasArray.add(aas);
  }

  public void addAas(AssetAdministrationShell aas) {
    aasArray.add(aas);
  }

  public void deleteAas(String aasId) {
    aasArray.removeIf(t -> t.getIdentification().getId().equals(aasId));
  }
}
