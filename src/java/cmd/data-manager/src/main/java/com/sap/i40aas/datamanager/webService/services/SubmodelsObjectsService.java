package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;
import utils.SampleSubmodelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class SubmodelsObjectsService {

  private SubmodelsObjectsService aasObjectService;

  @Autowired
  private SubmodelRepository submodelRepo;


  private final List<Submodel> submodels = new ArrayList<>(Arrays.asList(
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
  ));


  public Submodel getSubmodel(String submodelId) {
//TODO: replace idshort with the following

    Submodel sbFound = submodels.stream().filter(submodel -> submodel.getIdentification().getId().equals(submodelId)).findFirst().get();
    return sbFound;
  }


  public List<Submodel> getAllSubmodels() {
    List<Submodel> submodelsList = new ArrayList<>();
    submodelRepo.findAll().forEach(submodelEntity -> {
      submodelsList.add(AASObjectsDeserializer.Companion.deserializeSubmodel(submodelEntity.getSubmodelObj()));
    });
    return submodelsList;
  }

  public void updateSubmodel(String idShort, Submodel submodel) {
    if (submodels.stream().anyMatch(submodel1 -> submodel.getIdShort().equals(idShort))) {
      submodels.set(submodels.indexOf(submodels.stream().filter(submodel1 -> submodel.getIdShort().equals(idShort)).findFirst().get()), submodel);
    } else
      submodels.add(submodel);
  }

  public void addSubmodel(Submodel submodel) {

    SubmodelEntity sbE = new SubmodelEntity(submodel.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
//    SubmodelEntity sbE = new SubmodelEntity(submodel.getIdentification().getId(), "test");

    submodelRepo.save(sbE);

  }

  public void deleteSubmodel(String idShort) {
    submodels.removeIf(t -> t.getIdShort().equals(idShort));
  }
}
