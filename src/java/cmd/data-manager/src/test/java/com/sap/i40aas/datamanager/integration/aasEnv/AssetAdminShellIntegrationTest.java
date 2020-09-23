package com.sap.i40aas.datamanager.integration.aasEnv;

import com.sap.i40aas.datamanager.persistence.repositories.AssetAdministrationShellRepository;
import com.sap.i40aas.datamanager.persistence.repositories.AssetRepository;
import com.sap.i40aas.datamanager.persistence.repositories.SubmodelRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class AssetAdminShellIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;
//  The Spring Boot TestEntityManager is an alternative to the standard JPA EntityManager that provides methods commonly used when writing tests.


  @Autowired
  private SubmodelRepository submodelRepo;
  @Autowired
  private AssetRepository assetRepo;
  @Autowired
  private AssetAdministrationShellRepository aasRepo;


 /* @Test
  public void whenSaveAnAasWithoutRelationsItshouldBePersisted() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submode_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";

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
*/
/*
  @Test
  public void whenDeleteSubmodelThenCannotBeFound() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submodel_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";
    SubmodelEntity sampleSubmodelEntity = new SubmodelEntity(submodel_id, sampleSb);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(sampleSubmodelEntity);
    entityManager.flush();

    // when
    Optional<SubmodelEntity> found = submodelRepository.findById(submodel_id);
    assertThat(found.get().getId()).isEqualTo(sampleSubmodelEntity.getId());

    log.info("Id " + found.get().getId());
    // then

    entityManager.remove(sampleSubmodelEntity);
    Optional<SubmodelEntity> nothingToFind = submodelRepository.findById(submodel_id);

//    check that the record was correctly deleted
    assertThat(nothingToFind.isPresent()).isFalse();
  }
*/

/*
  @Test
  public void whenUpdateubmodelThenUpdated() throws IOException {
    // given
    //read an Submodel.json
    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submodel_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";
    SubmodelEntity sampleSubmodelEntity = new SubmodelEntity(submodel_id, sampleSb);

    //depends on the DB integration, will fail if DB not connected
    entityManager.persist(sampleSubmodelEntity);
    entityManager.flush();

    Optional<SubmodelEntity> found = submodelRepository.findById(submodel_id);
    assertThat(found.get().getId()).isEqualTo(sampleSubmodelEntity.getId());

    log.info("Id " + found.get().getId());

    Submodel sb = AASObjectsDeserializer.Companion.deserializeSubmodel(found.get().getSubmodelObj());
    sb.setIdShort("updatedIdShort");

    sampleSubmodelEntity.setSubmodelObj(AASObjectsDeserializer.Companion.serializeSubmodel(sb));

    submodelRepository.save(sampleSubmodelEntity);

    found = submodelRepository.findById(submodel_id);
    assertThat(AASObjectsDeserializer.Companion.deserializeSubmodel(found.get().getSubmodelObj()).getIdShort()).isEqualTo("updatedIdShort");


  }
*/

}
