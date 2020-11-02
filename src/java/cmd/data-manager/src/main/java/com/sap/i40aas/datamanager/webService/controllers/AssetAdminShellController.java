package com.sap.i40aas.datamanager.webService.controllers;

import baseClasses.Identifier;
import com.sap.i40aas.datamanager.errorHandling.AASObjectValidationException;
import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.validation.IdURLConstraint;
import com.sap.i40aas.datamanager.webService.services.AssetAdminShellObjectsService;
import identifiables.AssetAdministrationShell;
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

public class AssetAdminShellController {

  @Autowired
  private final AssetAdminShellObjectsService assetAdminShellObjectsService;

  @IdURLConstraint
  private String id;

  //need for mock testing
  public AssetAdminShellController(AssetAdminShellObjectsService assetAdminShellObjectsService) {
    this.assetAdminShellObjectsService = assetAdminShellObjectsService;
  }


  @GetMapping(value = "/shells")
  public List<AssetAdministrationShell> getAssetAdministrationShellsList() {

    log.info("List AAS request");
    return assetAdminShellObjectsService.getAllAssetAdministrationShells();
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/listaas")
  public List<Identifier> getAssetAdministrationShellIdList() {

    return assetAdminShellObjectsService.getAASIdList();
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/shells", params = "id")
  public AssetAdministrationShell getAssetAdministrationShells(@RequestParam(name = "id", required = false) @IdURLConstraint String id) {

    return assetAdminShellObjectsService.getAssetAdministrationShell(id);
  }

  @PutMapping("/shells")
  public ResponseEntity AssetAdministrationShell(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) throws DuplicateResourceException {

    log.debug("Received PUT /shells with id " + id);

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    AssetAdministrationShell aas;
    try {
      aas = AASObjectsDeserializer.Companion.deserializeAAS(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }

    AssetAdministrationShell createdAAS = assetAdminShellObjectsService.createAssetAdministrationShell(id, aas);

    return new ResponseEntity<>(createdAAS, HttpStatus.CREATED);
  }

  @PatchMapping("/shells")
  public ResponseEntity updateSubmodel(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) {

    AssetAdministrationShell aas;
    try {
      aas = AASObjectsDeserializer.Companion.deserializeAAS(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    AssetAdministrationShell updatedAAS = assetAdminShellObjectsService.updateAssetAdministrationShell(id, aas);

    return new ResponseEntity<>(updatedAAS, HttpStatus.OK);
  }

  @PostMapping("/shells")
  public ResponseEntity updateAssetAdministrationShell(@RequestBody String body) {
    AssetAdministrationShell aas;
    try {
      aas = AASObjectsDeserializer.Companion.deserializeAAS(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    AssetAdministrationShell addedAAS = assetAdminShellObjectsService.addAssetAdministrationShell(aas);
    return new ResponseEntity<>(addedAAS, HttpStatus.OK);
  }


  @DeleteMapping("/shells")
  public void deleteAssetAdministrationShell(@RequestParam("id") @IdURLConstraint String id) {
    assetAdminShellObjectsService.deleteAssetAdministrationShell(id);

  }
}
