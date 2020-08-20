package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.webService.services.SubmodelsObjectsService;
import identifiables.Submodel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Slf4j

public class SubmodelController {


  @Autowired
  private final SubmodelsObjectsService submodelService;

  //need for mock testing
  public SubmodelController(SubmodelsObjectsService submodelService) {
    this.submodelService = submodelService;
  }


  @GetMapping(value = "/submodels")
  public List<Submodel> getSubmodels() {

    log.info("List Submodels request");
    return submodelService.getAllSubmodels();


  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/submodels", params = "id")
  public Submodel getSubmodels(@RequestParam(name = "id", required = false) String id) {

    return submodelService.getSubmodel(id);
  }

  @PutMapping("/submodels")
  public void updateSubmodel(@RequestBody String body, @RequestParam("id") String id) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    Submodel sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
    submodelService.updateSubmodel(id, sb);
  }


  @DeleteMapping("/submodels")
  public void deleteSubmodel(@RequestParam("id") String id) {
    submodelService.deleteSubmodel(id);
  }
}
