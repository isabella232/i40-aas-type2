package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;

import java.util.ArrayList;
import java.util.List;


@Service
public class SubmodelObjectsService {

  private SubmodelObjectsService aasObjectService;

  @Autowired
  private SubmodelRepository submodelRepo;


  public Submodel getSubmodel(String id) {
//TODO: replace idshort with the following

    //Submodel sbFound = submodels.stream().filter(submodel -> submodel.getIdentification().getId().equals(submodelId)).findFirst().get();
    SubmodelEntity submodelEntityFound = submodelRepo.findById(id).get();
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

  public Submodel updateSubmodel(String id, Submodel submodel) {

    if (submodelRepo.findById(id).isPresent()) {
      SubmodelEntity sbE = new SubmodelEntity(id, AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
      submodelRepo.save(sbE);
      return submodel;
    } else
      throw new java.util.NoSuchElementException();
  }

  public void addSubmodel(Submodel submodel) {

    SubmodelEntity sbE = new SubmodelEntity(submodel.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
    submodelRepo.save(sbE);

  }

  public Submodel createSubmodel(String id, Submodel submodel) throws DuplicateResourceException {
// if Id not present create else if already there throw error
    if (submodelRepo.findById(id).isPresent() == false) {
      SubmodelEntity sbE = new SubmodelEntity(id, AASObjectsDeserializer.Companion.serializeSubmodel(submodel));
      submodelRepo.save(sbE);
      return submodel;
    } else
      throw new DuplicateResourceException();

  }

  public Submodel deleteSubmodel(String id) {
    if (submodelRepo.findById(id).isPresent()) {
      //find the Submodel so that it gets returned
      SubmodelEntity submodelEntityFound = submodelRepo.findById(id).get();
      Submodel sbFound = AASObjectsDeserializer.Companion.deserializeSubmodel(submodelEntityFound.getSubmodelObj());

      submodelRepo.deleteById(id);
      return sbFound;
    } else
      throw new java.util.NoSuchElementException();
  }

}
