package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.webService.services.SubmodelsObjectsService;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
public class SubmodelController {


  @Autowired
  private final SubmodelsObjectsService submodelService;

  //need for mock testing
  public SubmodelController(SubmodelsObjectsService submodelService) {
    this.submodelService = submodelService;
  }


  @RequestMapping(value = "/submodels", method = RequestMethod.GET)
  public List<Submodel> submodels() {

    return submodelService.getAllSubmodels();
  }


  @GetMapping("/submodels")
  public Submodel getSubmodel(@RequestParam("id") String id) {

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
