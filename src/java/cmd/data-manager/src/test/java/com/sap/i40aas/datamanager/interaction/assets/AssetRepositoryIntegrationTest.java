package com.sap.i40aas.datamanager.interaction.assets;

import com.sap.i40aas.datamanager.persistence.entities.AssetEntity;
import com.sap.i40aas.datamanager.persistence.repositories.AssetRepository;
import identifiables.Asset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import utils.AASObjectsDeserializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

;

@Slf4j

//@RunWith(SpringRunner.class) provides a bridge between Spring Boot test features and JUnit
//@DataJpaTest provides some standard setup needed for testing the persistence layer:
//  configuring H2, an in-memory database
//  setting Hibernate, Spring Data, and the DataSource
//  performing an @EntityScan
//turning on SQL logging

@RunWith(SpringRunner.class)
@DataJpaTest
public class AssetRepositoryIntegrationTest {


  @Autowired
  private TestEntityManager entityManager;
//  The Spring Boot TestEntityManager is an alternative to the standard JPA EntityManager that provides methods commonly used when writing tests.


  @Autowired
  private AssetRepository assetRepository;

  // write test cases here
  @Test
  public void whenFindById_thenReturnAsset() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleAsset = new String(
      Files.readAllBytes(resource.toPath()));

    String id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";

    AssetEntity sampleAssetEntity = new AssetEntity(id, sampleAsset);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(sampleAssetEntity);
    entityManager.flush();

    // when
    Optional<AssetEntity> found = assetRepository.findById(id);

//    log.info("Id " + found.get().getId());
    // then
    assertThat(found.get().getId()).isEqualTo(sampleAssetEntity.getId());
  }

  @Test
  public void whenDeleteAssetThenCannotBeFound() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/asset-sample.json").getFile();
    String sampleAsset = new String(
      Files.readAllBytes(resource.toPath()));

    String id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";
    AssetEntity sampleAssetEntity = new AssetEntity(id, sampleAsset);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(sampleAssetEntity);
    entityManager.flush();

    // when
    Optional<AssetEntity> found = assetRepository.findById(id);
    assertThat(found.get().getId()).isEqualTo(sampleAssetEntity.getId());

//    log.info("Id " + found.get().getId());
    // then

    entityManager.remove(sampleAssetEntity);
    Optional<AssetEntity> nothingToFind = assetRepository.findById(id);

//    check that the record was correctly deleted
    assertThat(nothingToFind.isPresent()).isFalse();
  }

  @Test
  public void whenUpdateAssetThenUpdated() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleAsset = new String(
      Files.readAllBytes(resource.toPath()));

    String id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";
    AssetEntity entity = new AssetEntity(id, sampleAsset);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(entity);
    entityManager.flush();

    Optional<AssetEntity> found = assetRepository.findById(id);
    assertThat(found.get().getId()).isEqualTo(entity.getId());

//    log.info("Id " + found.get().getId());

    Asset asset = AASObjectsDeserializer.Companion.deserializeAsset(found.get().getAssetObj());
    asset.setIdShort("updatedIdShort");

    entity.setAssetObj(AASObjectsDeserializer.Companion.serializeAsset(asset));

    assetRepository.save(entity);

    found = assetRepository.findById(id);
    assertThat(AASObjectsDeserializer.Companion.deserializeAsset(found.get().getAssetObj()).getIdShort()).isEqualTo("updatedIdShort");


  }

}
