package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;

@Service
public class SubmodelServiceImpl extends SubmodelObjectsService {

  @Autowired
  private SubmodelRepository submodelRepository;

  @Override
  public Submodel getSubmodel(String submodel_id) {
    SubmodelEntity submodelEntityFound = submodelRepository.findById(submodel_id).get();
    Submodel sbFound = AASObjectsDeserializer.Companion.deserializeSubmodel(submodelEntityFound.getSubmodelObj());

    return sbFound;
  }
}
