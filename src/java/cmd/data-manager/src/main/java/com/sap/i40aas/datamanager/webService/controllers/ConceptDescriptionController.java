package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.webService.services.ConceptDescriptionObjectsService;
import identifiables.ConceptDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import java.util.List;

@RestController
@Slf4j

public class ConceptDescriptionController {


  @Autowired
  private final ConceptDescriptionObjectsService cdService;

  //need for mock testing
  public ConceptDescriptionController(ConceptDescriptionObjectsService cdService) {
    this.cdService = cdService;
  }


  @GetMapping(value = "/conceptDescriptions")
  public List<ConceptDescription> getConceptDescriptionList() {

    log.info("List ConceptDescription request");
    return cdService.getAllConceptDescriptions();


  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/conceptDescriptions", params = "id")
  public ConceptDescription getConceptDescription(@RequestParam(name = "id", required = false) String id) {

    return cdService.getConceptDescription(id);
  }

  @PutMapping("/conceptDescriptions")
  public void updateConceptDescription(@RequestBody String body, @RequestParam("id") String id) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson. TODO: see if there are alternatives to this
    ConceptDescription cd = AASObjectsDeserializer.Companion.deserializeConceptDescription(body);
    cdService.updateConceptDescription(id, cd);
  }


  @DeleteMapping("/conceptDescriptions")
  public void deleteAsset(@RequestParam("id") String id) {

    cdService.deleteConceptDescription(id);
  }
}
