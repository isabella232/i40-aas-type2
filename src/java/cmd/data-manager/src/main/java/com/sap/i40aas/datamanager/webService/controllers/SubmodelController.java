package com.sap.i40aas.datamanager.webService.controllers;

import com.sap.i40aas.datamanager.AASObjectsDeserializer;
import com.sap.i40aas.datamanager.webService.services.SubmodelsObjectsService;
import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping("/submodels/{idShort}")
    public Submodel getSubmodel(@PathVariable String idShort) {
        return submodelService.getSubmodel(idShort);
    }


    @PutMapping("/submodels/{idShort}")
    public void updateSubmodel(@RequestBody String body, @PathVariable String idShort) {


        //NOTE: we give String in @Requestbody otherwise it will be deserialized with Jackson
        Submodel sb = AASObjectsDeserializer.Companion.deserializeSubmodel(body);
        submodelService.updateSubmodel(idShort, sb);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/submodels/{idShort}")
    public void deleteTopic(@PathVariable String idShort) {
        submodelService.deleteSubmodel(idShort);
    }

    //Example if we wanted query params
    /*
    @RequestMapping(method = RequestMethod.DELETE, value = "/submodels/{idShort}")
     public void deleteTopic(@PathVariable String id) {
        submodelObjectService.deleteTopic(id);
        }
     */


}
