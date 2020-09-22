package com.sap.i40aas.datamanager;

import com.sap.i40aas.datamanager.webService.controllers.SubmodelController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DataManagerApplication.class)
public class SmokeTest {

  @Autowired
  private SubmodelController smController;


  @Test
  public void contexLoads() throws Exception {
    assertThat(smController).isNotNull();
  }
}
