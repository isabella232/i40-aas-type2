package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.webService.services.AssetsObjectsService;
import identifiables.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Slf4j

public class AssetController {


  @Autowired
  private final AssetsObjectsService assetsService;

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
  public Asset getAssets(@RequestParam(name = "id", required = false) String id) {

    return assetsService.getAsset(id);
  }

  @PutMapping("/assets")
  public void updateAsset(@RequestBody String body, @RequestParam("id") String id) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    Asset asset = AASObjectsDeserializer.Companion.deserializeAsset(body);
    assetsService.updateAsset(id, asset);
  }


  @DeleteMapping("/assets")
  public void deleteAsset(@RequestParam("id") String id) {

    assetsService.deleteAsset(id);
  }
}
