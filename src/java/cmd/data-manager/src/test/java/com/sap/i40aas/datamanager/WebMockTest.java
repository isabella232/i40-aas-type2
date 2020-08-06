package com.sap.i40aas.datamanager;

import com.sap.i40aas.datamanager.webService.SampleSubmodelFactory;
import com.sap.i40aas.datamanager.webService.controllers.RestResponseEntityExceptionHandler;
import com.sap.i40aas.datamanager.webService.controllers.SubmodelController;
import com.sap.i40aas.datamanager.webService.services.SubmodelsObjectsService;
import identifiables.Submodel;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubmodelController.class)
public class WebMockTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SubmodelsObjectsService submodelService;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(submodelService)
      .setControllerAdvice(new RestResponseEntityExceptionHandler())
      .build();
  }

  @Test
  public void getSumodelListShouldReturnListFromService() throws Exception {
    List<Submodel> submodels = new ArrayList<>(Arrays.asList(
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2")
    ));

    String sapleSerialized = AASObjectsDeserializer.Companion.serializeSubmodelList(submodels);

    when(submodelService.getAllSubmodels()).thenReturn(submodels);

    //expect as response a serialized list of submodelsd
    this.mockMvc.perform(get("/submodels")).andDo(print()).andExpect(status().isOk())
      .andExpect(content().json(sapleSerialized));
  }

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
    this.mockMvc.perform(get("/submodels/" + sampleShortId)).andDo(print()).andExpect(status().isOk())
      .andExpect(content().json(sampleSerialized));
  }

  @Test
  @WithMockUser
  public void getSumodelByIDShouldReturn400ErrorIfSubmodelNotFound() throws Exception {
    List<Submodel> submodels = new ArrayList<>(Arrays.asList(
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_1"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("sampleSb_2"),
      SampleSubmodelFactory.Companion.getSampleSubmodel("TestIdShort")
    ));

    String wrongId = "WrongId";
    //String sampleSerialized = AASDeserializer.Companion.serializeSubmodel(submodels.get(2));

    when(submodelService.getSubmodel(wrongId)).thenThrow(java.util.NoSuchElementException.class);

//        expect to return a 404 Error
    this.mockMvc.perform(get("/submodels/" + wrongId)).andDo(print()).andExpect(status().is4xxClientError());
  }
}
