package com.sap.i40aas.datamanager.integration.submodels;


import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import com.sap.i40aas.datamanager.webService.services.SubmodelObjectsService;
import identifiables.Submodel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

//   use the mocking support provided by Spring Boot Test
//   to write and test our Service layer code without wiring in our full persistence layer.
@RunWith(SpringRunner.class)


public class SubmodelServiceImplIntegrationTest {
  @TestConfiguration
  static class EmployeeServiceImplTestContextConfiguration {
    //    To check the Service class, we need to have an instance of the Service class created and available as a @Bean so that we can @Autowire it in our test class
    @Bean
    public SubmodelObjectsService submodelService() {
      return new SubmodelObjectsService();
    }
  }

  @Autowired
  private SubmodelObjectsService submodelService;

  @MockBean
  private SubmodelRepository submodelRepository;

  // write test cases here

  @Before
  public void setUp() throws IOException {
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submode_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";

    SubmodelEntity sampleSubmodelEntity = new SubmodelEntity(submode_id, sampleSb);

    Mockito.when(submodelRepository.findById(sampleSubmodelEntity.getId()))
      .thenReturn(java.util.Optional.of(sampleSubmodelEntity));
  }

  @Test
  public void whenValidName_thenEmployeeShouldBeFound() {
    String submode_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";
    Submodel found = submodelService.getSubmodel(submode_id);

    assertThat(found.getIdentification().getId())
      .isEqualTo(submode_id);
  }


}
