package com.sap.i40aas.datamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class DataManagerApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(DataManagerApplication.class, args);

    System.out.println("Data-manager Service started ");


  }

}
