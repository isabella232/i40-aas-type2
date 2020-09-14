package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.errorHandling.AASObjectValidationException;
import com.sap.i40aas.datamanager.validation.IdURLConstraint;
import com.sap.i40aas.datamanager.webService.services.AssetsObjectsService;
import identifiables.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Validated
@Slf4j

public class AssetController {


  @Autowired
  private final AssetsObjectsService assetsService;


  @IdURLConstraint
  private String id;


  //need for mock testing
  public AssetController(AssetsObjectsService assetsService) {
    this.assetsService = assetsService;
  }


  @GetMapping(value = "/assets")
  public List<Asset> getAssetsList() {

    log.info("List Assets request");
    return assetsService.getAllAssets();


  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/assets", params = "id")
  public Asset getAssets(@RequestParam(name = "id", required = false) @IdURLConstraint String id) {

    return assetsService.getAsset(id);
  }


  @PatchMapping("/assets")
  public ResponseEntity updateSubmodel(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) throws MissingServletRequestParameterException {

    Asset asset;
    try {
      asset = AASObjectsDeserializer.Companion.deserializeAsset(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    Asset createdAsset = assetsService.updateAsset(id, asset);

    return new ResponseEntity<>(createdAsset, HttpStatus.OK);
  }

  @PutMapping("/assets")
  public ResponseEntity createAsset(@RequestBody String body, @RequestParam("id") @IdURLConstraint String id) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    Asset asset;
    try {
      asset = AASObjectsDeserializer.Companion.deserializeAsset(body);
    } catch (Exception ex) {
      throw new AASObjectValidationException(ex.getMessage());
    }
    Asset createdAsset = assetsService.createAsset(id, asset);

    return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);

  }

  @DeleteMapping("/assets")
  public void deleteAsset(@RequestParam("id") @IdURLConstraint String id) {

    assetsService.deleteAsset(id);
  }
}
