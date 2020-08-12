package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.webService.SampleSubmodelFactory;
import identifiables.Submodel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class SubmodelsObjectsService {

  private SubmodelsObjectsService aasObjectService;


  private final List<Submodel> submodels = new ArrayList<>(Arrays.asList(
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
  ));


  public Submodel getSubmodel(String idShort) {
//TODO: replace idshort with the following
    return submodels.stream().filter(submodel -> submodel.getIdentification().getId().equals(idShort)).findFirst().get();
  }


  public List<Submodel> getAllSubmodels() {
    return submodels;
  }

  public void updateSubmodel(String idShort, Submodel submodel) {
    if (submodels.stream().anyMatch(submodel1 -> submodel.getIdShort().equals(idShort))) {
      submodels.set(submodels.indexOf(submodels.stream().filter(submodel1 -> submodel.getIdShort().equals(idShort)).findFirst().get()), submodel);
    } else
      submodels.add(submodel);
  }

  public void addSubmodel(Submodel submodel) {
    submodels.add(submodel);
  }

  public void deleteSubmodel(String idShort) {
    submodels.removeIf(t -> t.getIdShort().equals(idShort));
  }
}
