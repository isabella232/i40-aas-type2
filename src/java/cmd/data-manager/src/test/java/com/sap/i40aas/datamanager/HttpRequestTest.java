package com.sap.i40aas.datamanager;

import identifiables.Submodel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import utils.AASObjectsDeserializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DataManagerApplication.class,
  webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Value("${user.name}")
  private String username;
  @Value("${user.password}")
  private String pass;


  @Test
  public void healthShouldReturnServerUpMessage() throws Exception {
    assertThat(this.restTemplate.withBasicAuth(username, pass).getForObject("http://localhost:" + port + "/health",
      String.class)).contains("Server UP!");
  }

  @Test
  public void getSubmodelsShouldReturnAListOfSubmodels() throws Exception {

    String response = this.restTemplate.withBasicAuth(username, pass).getForObject("http://localhost:" + port + "/submodels", String.class);

    List<Submodel> sbList = AASObjectsDeserializer.Companion.deserializeSubmodelList(response);

    assertThat(sbList).isInstanceOf(java.util.List.class);

    if (sbList.size() > 0)
      assertThat(sbList.get(0)).isInstanceOf(Submodel.class);


  }
  //check that a list was returned


}
