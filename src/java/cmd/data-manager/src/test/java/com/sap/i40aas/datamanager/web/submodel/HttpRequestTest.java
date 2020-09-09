package com.sap.i40aas.datamanager.web.submodel;

import com.sap.i40aas.datamanager.DataManagerApplication;
import identifiables.Submodel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import utils.AASObjectsDeserializer;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DataManagerApplication.class,
  webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Value("${basic.name}")
  private String username;
  @Value("${basic.password}")
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

  @Test
  public void putSubmodelsWithNonURLIDParamShouldReturnAn4xxError() throws Exception {

    File resource = new ClassPathResource(
      "/submodel-sample.json").getFile();
    String sampleSb = new String(
      Files.readAllBytes(resource.toPath()));

    String submodel_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";


    String url = "http://localhost:" + port + "/submodels";
    Map<String, String> params = new HashMap<String, String>();
    params.put("id", "sample-submodelID");
    String requestBody = sampleSb;
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
      .queryParam("id", "sample-submodelID");

    HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = this.restTemplate.withBasicAuth(username, pass).exchange(
      builder.toUriString(),
      HttpMethod.PUT,
      entity,
      String.class);


// check the response, e.g. Location header,  Status, and body
    response.getHeaders().getLocation();
    response.getStatusCode();

    String responseBody = response.getBody();

    System.out.println("status " + response.getStatusCode());
    System.out.println("status " + response.getBody());
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();

  }
  //check that a list was returned


  @Test
  public void putSubmodelsWithInvalidContentShouldReturnAn4xxError() throws Exception {

    String url = "http://localhost:" + port + "/submodels";

    String submodel_id = "http://acplt.org/Submodels/Assets/TestAsset/Identification";

    Map<String, String> params = new HashMap<String, String>();
    params.put("id", submodel_id);
    String requestBody = "{\"foo\":\"bar\"}";
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
      .queryParam("id", submodel_id);

    HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = this.restTemplate.withBasicAuth(username, pass).exchange(
      builder.toUriString(),
      HttpMethod.PUT,
      entity,
      String.class);


// check the response, e.g. Location header,  Status, and body
    response.getHeaders().getLocation();
    response.getStatusCode();

    System.out.println("status " + response.getStatusCode());
    System.out.println("response body " + response.getBody());
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();

  }
}
