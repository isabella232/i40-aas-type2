package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.persistence.entities.ConceptDescriptionEntity;
import com.sap.i40aas.datamanager.persistence.repositories.ConceptDescriptionRepository;
import identifiables.ConceptDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;

import java.util.ArrayList;
import java.util.List;


@Service
public class ConceptDescriptionObjectsService {

  private ConceptDescriptionObjectsService aasObjectService;

  @Autowired
  private ConceptDescriptionRepository conceptDescriptionRepository;


  public ConceptDescription getConceptDescription(String id) {

    ConceptDescriptionEntity cdEntityFound = conceptDescriptionRepository.findById(id).get();
    ConceptDescription cdFound = AASObjectsDeserializer.Companion.deserializeConceptDescription(cdEntityFound.getConceptDescrObj());
    return cdFound;
  }


  public List<ConceptDescription> getAllConceptDescriptions() {
    List<ConceptDescription> conceptDescriptionist = new ArrayList<>();
    conceptDescriptionRepository.findAll().forEach(conceptDescriptionEntity -> {
      conceptDescriptionist.add(AASObjectsDeserializer.Companion.deserializeConceptDescription(conceptDescriptionEntity.getConceptDescrObj()));
    });
    return conceptDescriptionist;
  }

  public ConceptDescription updateConceptDescription(String id, ConceptDescription conceptDescr) {

    if (conceptDescriptionRepository.findById(id).isPresent()) {
      ConceptDescriptionEntity cdE = new ConceptDescriptionEntity(id, AASObjectsDeserializer.Companion.serializeConceptDescription(conceptDescr));
      conceptDescriptionRepository.save(cdE);
      return conceptDescr;
    } else
      throw new java.util.NoSuchElementException();
  }

  public ConceptDescription addConceptDescription(ConceptDescription cd) {

    ConceptDescriptionEntity sbE = new ConceptDescriptionEntity(cd.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeConceptDescription(cd));
    conceptDescriptionRepository.save(sbE);
    return cd;

  }

  public ConceptDescription createConceptDescription(String id, ConceptDescription conceptDescr) throws DuplicateResourceException {
// if Id not present create else if already there throw error
    if (conceptDescriptionRepository.findById(id).isPresent() == false) {

      String sbSer = AASObjectsDeserializer.Companion.serializeConceptDescription(conceptDescr);
      ConceptDescriptionEntity sbE = new ConceptDescriptionEntity(id, sbSer);
      conceptDescriptionRepository.save(sbE);
      return conceptDescr;
    } else
      throw new DuplicateResourceException();
  }

  public ConceptDescription deleteConceptDescription(String id) {
    if (conceptDescriptionRepository.findById(id).isPresent()) {
      //find the ConceptDescription so that it gets returned
      ConceptDescriptionEntity cdEntityFound = conceptDescriptionRepository.findById(id).get();
      ConceptDescription cdFound = AASObjectsDeserializer.Companion.deserializeConceptDescription(cdEntityFound.getConceptDescrObj());

      conceptDescriptionRepository.deleteById(id);
      return cdFound;
    } else
      throw new java.util.NoSuchElementException();
  }

}
