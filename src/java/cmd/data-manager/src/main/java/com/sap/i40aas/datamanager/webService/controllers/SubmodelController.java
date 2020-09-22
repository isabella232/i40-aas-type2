package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.errorHandling.AASObjectValidationException;
import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.validation.IdURLConstraint;
import com.sap.i40aas.datamanager.webService.services.SubmodelObjectsService;
import identifiables.Submodel;
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

public class SubmodelController {

  @Autowired
  private final SubmodelObjectsService submodelService;

  @IdURLConstraint
  private String id;

  //need for mock testing
  public SubmodelController(SubmodelObjectsService submodelService) {
    this.submodelService = submodelService;
  }


  @GetMapping(value = "/submodels")
  public List<Submodel> getSubmodelsList() {

    log.info("List Submodels request");
    return submodelService.getAllSubmodels();
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/submodels", params = "id")
  public Submodel getSubmodels(@RequestParam(name = "id", required = false) @IdURLConstraint String id) {

    return submodelService.getSubmodel(id);
  }

  @PutMapping("/submodels")
  public ResponseEntity createSubmodel(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) throws DuplicateResourceException {

    log.debug("Received PUT /submodels with id " + id);

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    Submodel sb;
    try {
      sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }

    Submodel createdSubmodel = submodelService.createSubmodel(id, sb);

    return new ResponseEntity<>(createdSubmodel, HttpStatus.CREATED);
  }

  @PatchMapping("/submodels")
  public ResponseEntity updateSubmodel(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) {

    Submodel sb;
    try {
      sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    Submodel updatedSb = submodelService.updateSubmodel(id, sb);

    return new ResponseEntity<>(updatedSb, HttpStatus.OK);
  }

  @PostMapping("/submodels")
  public ResponseEntity updateSubmodel(@RequestBody String body) {
    Submodel sb;
    try {
      sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    Submodel addedSubmodel = submodelService.addSubmodel(sb);
    return new ResponseEntity<>(addedSubmodel, HttpStatus.OK);
  }


  @DeleteMapping("/submodels")
  public void deleteSubmodel(@RequestParam("id") @IdURLConstraint String id) {
    submodelService.deleteSubmodel(id);

  }
}
