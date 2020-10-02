package com.sap.i40aas.datamanager.web.mocktests;

import com.sap.i40aas.datamanager.errorHandling.RestResponseEntityExceptionHandler;
import com.sap.i40aas.datamanager.webService.controllers.ConceptDescriptionController;
import com.sap.i40aas.datamanager.webService.services.ConceptDescriptionObjectsService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.AASObjectsDeserializer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//using @WebMvcTest will tell Spring Boot to instantiate only the web layer and not the entire context.
@WebMvcTest(controllers = ConceptDescriptionController.class)

public
class ConceptDescriptionTest {
  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


  private final String sampleAASEnv;

  @Autowired
  private MockMvc mockMvc;


  // mock the Service layer code for our unit tests
  @MockBean
  private ConceptDescriptionObjectsService conceptDescriptionObjectsService;


  public ConceptDescriptionTest() throws IOException {
    File resource = new ClassPathResource(
      "/testAASEnv.json").getFile();
    sampleAASEnv = new String(
      Files.readAllBytes(resource.toPath()));
  }

  //activate exception handler
  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(conceptDescriptionObjectsService)
      .setControllerAdvice(new RestResponseEntityExceptionHandler())
      .build();
  }

  @After
  public void reset_mocks() {
    Mockito.reset(conceptDescriptionObjectsService);
  }

  @WithMockUser //for basic auth
  @Test
  public void getConceptDescriptionsListShouldReturnListFromService() throws Exception {

    when(conceptDescriptionObjectsService.getAllConceptDescriptions()).thenReturn(AASObjectsDeserializer.Companion.deserializeAASEnv(sampleAASEnv).getConceptDescriptions());

    String sampleSerialised = AASObjectsDeserializer.Companion.serializeConceptDescriptionList(AASObjectsDeserializer.Companion.deserializeAASEnv(sampleAASEnv).getConceptDescriptions());

    //expect as response a serialized list of submodelsd
    this.mockMvc.perform(get("/conceptDescriptions"))
      .andDo(print()).andExpect(status().isOk())
      .andExpect(content().json(sampleSerialised));
  }


}
