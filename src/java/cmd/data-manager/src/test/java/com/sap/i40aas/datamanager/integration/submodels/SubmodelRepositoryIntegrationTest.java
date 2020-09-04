package com.sap.i40aas.datamanager.integration.submodels;

import com.sap.i40aas.datamanager.persistence.entities.SubmodelEntity;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j

//@RunWith(SpringRunner.class) provides a bridge between Spring Boot test features and JUnit
//@DataJpaTest provides some standard setup needed for testing the persistence layer:
//  configuring H2, an in-memory database
//  setting Hibernate, Spring Data, and the DataSource
//  performing an @EntityScan
//turning on SQL logging

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubmodelRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;
//  The Spring Boot TestEntityManager is an alternative to the standard JPA EntityManager that provides methods commonly used when writing tests.


  @Autowired
  private SubmodelRepository submodelRepository;

  // write test cases here
  @Test
  public void whenFindByName_thenReturnEmployee() throws IOException {
    // given

    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submode_id = "sb_id_1";

    SubmodelEntity sampleSubmodelEntity = new SubmodelEntity(submode_id, sampleSb);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(sampleSubmodelEntity);
    entityManager.flush();

    // when
    Optional<SubmodelEntity> found = submodelRepository.findById(submode_id);

    log.info("Id " + found.get().getId());

    // then
    assertThat(found.get().getId()).isEqualTo(sampleSubmodelEntity.getId());


  }

}
