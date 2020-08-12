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


  @RequestMapping("/submodels")
  public List<Submodel> submodels() {
    return submodelService.getAllSubmodels();
  }


  @RequestMapping("/submodels/{id}")
  public Submodel getSubmodel(@PathVariable String id) {
    return submodelService.getSubmodel(id);
  }


  @PutMapping("/submodels/{id}")
  public void updateSubmodel(@RequestBody String body, @PathVariable String id) {


    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    Submodel sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
    submodelService.updateSubmodel(id, sb);
  }


  @RequestMapping(method = RequestMethod.DELETE, value = "/submodels/{id}")
  public void deleteTopic(@PathVariable String id) {
    submodelService.deleteSubmodel(id);
  }

  //Example if we wanted query params
    /*
    @RequestMapping(method = RequestMethod.DELETE, value = "/submodels/{idShort}")
     public void deleteTopic(@PathVariable String id) {
        submodelObjectService.deleteTopic(id);
        }
     */


}
