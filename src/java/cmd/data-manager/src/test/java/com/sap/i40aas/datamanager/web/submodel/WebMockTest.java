package com.sap.i40aas.datamanager.web.submodel;

import com.sap.i40aas.datamanager.webService.controllers.RestResponseEntityExceptionHandler;
import com.sap.i40aas.datamanager.webService.controllers.SubmodelController;
import com.sap.i40aas.datamanager.webService.services.SubmodelObjectsService;
import identifiables.Submodel;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.AASObjectsDeserializer;
import utils.SampleSubmodelFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.springframework.security.test.context.support.WithMockUser;

//import org.springframework.security.test.context.support.WithMockUser;

//using @WebMvcTest will tell Spring Boot to instantiate only the web layer and not the entire context.
@WebMvcTest(SubmodelController.class)
public class WebMockTest {
  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Autowired
  private MockMvc mockMvc;


  // mock the Service layer code for our unit tests
  @MockBean
  private SubmodelObjectsService submodelService;

  //activate exception handler
  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(submodelService)
      .setControllerAdvice(new RestResponseEntityExceptionHandler())
      .build();
  }

  @WithMockUser //for basic auth
  @Test
  public void getSumodelListShouldReturnListFromService() throws Exception {
    List<Submodel> submodels = new ArrayList<>(Arrays.asList(
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
    ));

    //deserialized list of submodels that should be returned as response
    String sapleSerialized = AASObjectsDeserializer.Companion.serializeSubmodelList(submodels);

    when(submodelService.getAllSubmodels()).thenReturn(submodels);

    //expect as response a serialized list of submodelsd
    this.mockMvc.perform(get("/submodels"))
      .andDo(print()).andExpect(status().isOk())
      .andExpect(content().json(sapleSerialized));
  }

  @WithMockUser
  @Test
  public void getSumodelByIDShouldReturnASubmodelWithIdFromService() throws Exception {
    List<Submodel> submodels = new ArrayList<>(Arrays.asList(
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("TestIdShort")
    ));

    String sampleShortId = "Test-IdShort";
    String sampleSerialized = AASObjectsDeserializer.Companion.serializeSubmodel(submodels.get(2));

    when(submodelService.getSubmodel(sampleShortId)).thenReturn(submodels.get(2));

    //expect as response a serialized list of submodelsd
    this.mockMvc.perform(get("/submodels")
      .param("id", sampleShortId))
      .andDo(print()).andExpect(status().isOk())
      .andExpect(content().json(sampleSerialized));
  }

  @Test
  @WithMockUser
  public void getSumodelByIDShouldReturn404ErrorIfSubmodelNotFound() throws Exception {
    List<Submodel> submodels = new ArrayList<>(Arrays.asList(
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("TestIdShort")
    ));

    String wrongId = "WrongId";
    //String sampleSerialized = AASDeserializer.Companion.serializeSubmodel(submodels.get(2));

    when(submodelService.getSubmodel(wrongId)).thenThrow(java.util.NoSuchElementException.class);

//        expect to return a 404 Error
    this.mockMvc.perform(get("/submodels")
      .param("id", wrongId))
      .andDo(print()).andExpect(status().is4xxClientError())
      .andExpect(content().string(containsString("Requested resource not found")));

  }

  @Test
  @WithMockUser
  public void putSumodelShouldReturnOKAndTheSubmodelObject() throws Exception {

    String id = "sampleId";
    Submodel sampleSubmodel = SampleSubmodelFactory.Companion.getSampleSubmodel(id);
    //String sampleSerialized = AASDeserializer.Companion.serializeSubmodel(submodels.get(2));
    String serializedSubmodel = AASObjectsDeserializer.Companion.serializeSubmodel(sampleSubmodel);

    when(submodelService.createSubmodel(id, sampleSubmodel)).thenReturn(sampleSubmodel);

//        expect to return a 202 OK with the submodel as content
    this.mockMvc.perform(put("/submodels")
      .contentType(APPLICATION_JSON_UTF8)
      .content(serializedSubmodel)
      .param("id", id))
      .andDo(print())
      .andExpect(status().is2xxSuccessful());

  }


}
