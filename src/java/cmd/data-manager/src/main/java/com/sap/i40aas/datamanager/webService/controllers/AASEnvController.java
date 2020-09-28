package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.validation.IdURLConstraint;
import com.sap.i40aas.datamanager.webService.services.AASEnvObjectsService;
import identifiables.AssetAdministrationShellEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.AASObjectsDeserializer;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Slf4j
public class AASEnvController {


  @Autowired
  private final AASEnvObjectsService aasEnvService;

  //need for mock testing
  public AASEnvController(AASEnvObjectsService aasEnvService) {

    this.aasEnvService = aasEnvService;
  }

  @RequestMapping(value = "/env", method = POST)
  public AssetAdministrationShellEnv createAASEnv(@RequestBody String body) {

    //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson
    AssetAdministrationShellEnv env = AASObjectsDeserializer.Companion.deserializeAASEnv(body);
    aasEnvService.addAASEnv(env);

    log.info("AASEnv received");

    return env;
  }

  //use the params to filter by HTTP parameters
  @GetMapping(value = "/env", params = "aasId")
  public AssetAdministrationShellEnv getAssetAdministrationShellEnv(@RequestParam(name = "aasId", required = true) @IdURLConstraint String aasId) {

    return aasEnvService.getAASEnv(aasId);
  }


}
