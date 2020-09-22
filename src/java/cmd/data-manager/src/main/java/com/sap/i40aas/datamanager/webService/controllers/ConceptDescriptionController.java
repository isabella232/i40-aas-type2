package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.errorHandling.AASObjectValidationException;
import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.validation.IdURLConstraint;
import com.sap.i40aas.datamanager.webService.services.ConceptDescriptionObjectsService;
import identifiables.ConceptDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Validated
@Slf4j

public class ConceptDescriptionController {

  @Autowired
  private final ConceptDescriptionObjectsService conceptDescriptionObjectsService;

  @IdURLConstraint
  private String id;

  //need for mock testing
  public ConceptDescriptionController(ConceptDescriptionObjectsService conceptDescriptionObjectsService) {
    this.conceptDescriptionObjectsService = conceptDescriptionObjectsService;
  }


  @GetMapping(value = "/conceptDescriptions")
  public List<ConceptDescription> getConceptDescriptionsList() {

    log.info("List ConceptDescription request");
    return conceptDescriptionObjectsService.getAllConceptDescriptions();
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/conceptDescriptions", params = "id")
  public ConceptDescription getConceptDescriptions(@RequestParam(name = "id", required = false) @IdURLConstraint String id) {

    return conceptDescriptionObjectsService.getConceptDescription(id);
  }

  @PutMapping("/conceptDescriptions")
  public ResponseEntity createConceptDescription(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) throws DuplicateResourceException {

    log.debug("Received PUT /ConceptDescription with id " + id);

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    ConceptDescription sb;
    try {
      sb = AASObjectsDeserializer.Companion.deserializeConceptDescription(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }

    ConceptDescription createdConceptDescription = conceptDescriptionObjectsService.createConceptDescription(id, sb);

    return new ResponseEntity<>(createdConceptDescription, HttpStatus.CREATED);
  }

  @PatchMapping("/conceptDescriptions")
  public ResponseEntity updateConceptDescription(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) {

    ConceptDescription cd;
    try {
      cd = AASObjectsDeserializer.Companion.deserializeConceptDescription(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    ConceptDescription updatedCd = conceptDescriptionObjectsService.updateConceptDescription(id, cd);

    return new ResponseEntity<>(updatedCd, HttpStatus.OK);
  }

  @PostMapping("/conceptDescriptions")
  public ResponseEntity updateConceptDescription(@RequestBody String body) {
    ConceptDescription cd;
    try {
      cd = AASObjectsDeserializer.Companion.deserializeConceptDescription(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    ConceptDescription addedConceptDescription = conceptDescriptionObjectsService.addConceptDescription(cd);
    return new ResponseEntity<>(addedConceptDescription, HttpStatus.OK);
  }


  @DeleteMapping("/conceptDescriptions")
  public void deleteConceptDescription(@RequestParam("id") @IdURLConstraint String id) {
    conceptDescriptionObjectsService.deleteConceptDescription(id);

  }
}
