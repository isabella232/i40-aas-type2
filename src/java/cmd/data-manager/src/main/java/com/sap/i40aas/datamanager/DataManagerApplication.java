package com.sap.i40aas.datamanager;

import identifiables.AssetAdministrationShellEnv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@SpringBootApplication
public class DataManagerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DataManagerApplication.class, args);

        //read an AASEnv.json
        File resource = new ClassPathResource(
                "/testAASEnv.json").getFile();
        String sampleJson = new String(
                Files.readAllBytes(resource.toPath()));

        //create a deserializer obj

        //parse the json
        AssetAdministrationShellEnv parsedAASEnv = AASObjectsDeserializer.Companion.deserializeAASEnv(sampleJson);

        System.out.println("Test property idshort: " + parsedAASEnv.getSubmodels().get(0).getIdShort());


    }

}
