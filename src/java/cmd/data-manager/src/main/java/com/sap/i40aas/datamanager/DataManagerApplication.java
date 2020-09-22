package com.sap.i40aas.datamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DataManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataManagerApplication.class, args);

    System.out.println("Data-manager Service started ");


  }

}
