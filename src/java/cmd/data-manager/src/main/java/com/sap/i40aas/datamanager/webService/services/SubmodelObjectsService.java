package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import com.sap.i40aas.datamanager.webService.controllers.DuplicateResourceException;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;
import utils.SampleSubmodelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class SubmodelObjectsService {

  private SubmodelObjectsService aasObjectService;

  @Autowired
  private SubmodelRepository submodelRepo;


  private final List<Submodel> submodels = new ArrayList<>(Arrays.asList(
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
    SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
  ));


  public Submodel getSubmodel(String submodelId) {
//TODO: replace idshort with the following

    //Submodel sbFound = submodels.stream().filter(submodel -> submodel.getIdentification().getId().equals(submodelId)).findFirst().get();
    SubmodelEntity submodelEntityFound = submodelRepo.findById(submodelId).get();
    Submodel sbFound = AASObjectsDeserializer.Companion.deserializeSubmodel(submodelEntityFound.getSubmodelObj());

    return sbFound;
  }


  public List<Submodel> getAllSubmodels() {
    List<Submodel> submodelsList = new ArrayList<>();
    submodelRepo.findAll().forEach(submodelEntity -> {
      submodelsList.add(AASObjectsDeserializer.Companion.deserializeSubmodel(submodelEntity.getSubmodelObj()));
    });
    return submodelsList;
  }

  public void updateSubmodel(String submodel_id, Submodel submodel) {

    if (submodel_id == null)
      throw new java.util.NoSuchElementException();


    if (submodelRepo.findById(submodel_id).isPresent()) {
      SubmodelEntity sbE = new SubmodelEntity(submodel_id, AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
      submodelRepo.save(sbE);
    } else
      throw new java.util.NoSuchElementException();
  }

  public void addSubmodel(Submodel submodel) {

    SubmodelEntity sbE = new SubmodelEntity(submodel.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
    submodelRepo.save(sbE);

  }

  public void createSubmodel(String submodel_id, Submodel submodel) throws DuplicateResourceException {
// if Id not present create else if already there throw error
    if (submodelRepo.findById(submodel_id).isPresent() == false) {
      SubmodelEntity sbE = new SubmodelEntity(submodel_id, AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
      submodelRepo.save(sbE);
    } else
      throw new DuplicateResourceException();

  }

  public void deleteSubmodel(String submodel_id) {
    if (submodelRepo.findById(submodel_id).isPresent()) {
      submodelRepo.deleteById(submodel_id);
    } else
      throw new java.util.NoSuchElementException();
  }

}
