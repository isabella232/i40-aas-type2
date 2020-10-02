package com.sap.i40aas.datamanager;

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

    //THIS WILL FAIL WITH DOCKER
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));


    System.out.println("Data-manager Service started ");


  }

}
