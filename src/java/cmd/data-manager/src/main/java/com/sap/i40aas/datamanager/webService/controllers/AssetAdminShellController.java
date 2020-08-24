package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.webService.services.AssetAdminShellObjectsService;
import identifiables.AssetAdministrationShell;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Slf4j

public class AssetAdminShellController {


  @Autowired
  private final AssetAdminShellObjectsService aasService;

  //need for mock testing
  public AssetAdminShellController(AssetAdminShellObjectsService aasService) {
    this.aasService = aasService;
  }


  @GetMapping(value = "/shells")
  public List<AssetAdministrationShell> getShellList() {

    log.info("List AAS request");
    return aasService.getAllAASs();
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/shells", params = "id")
  public AssetAdministrationShell getShells(@RequestParam(name = "id", required = false) String id) {

    return aasService.getAssetAdministrationShell(id);
  }

  @PutMapping("/shells")
  public void updateAsset(@RequestBody String body, @RequestParam("id") String id) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    AssetAdministrationShell aas = AASObjectsDeserializer.Companion.deserializeAAS(body);
    aasService.updateAAS(id, aas);
  }


  @DeleteMapping("/shells")
  public void deleteShell(@RequestParam("id") String id) {

    aasService.deleteAas(id);
  }
}
