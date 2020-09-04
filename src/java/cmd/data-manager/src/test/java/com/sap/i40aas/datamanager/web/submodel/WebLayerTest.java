package com.sap.i40aas.datamanager.web.submodel;

import com.sap.i40aas.datamanager.webService.controllers.RestResponseEntityExceptionHandler;
import com.sap.i40aas.datamanager.webService.controllers.SubmodelController;
import com.sap.i40aas.datamanager.webService.services.SubmodelObjectsService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubmodelController.class)
//tag::test[]
public class WebLayerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SubmodelObjectsService submodelService;


  //activate exception handler
  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(submodelService)
      .setControllerAdvice(new RestResponseEntityExceptionHandler())
      .build();
  }

  @Test
  @WithMockUser
  public void shouldReturnNoSubmodelFoundMessage() throws Exception {
    this.mockMvc.perform(get("/submodels/noID")).andDo(print()).andExpect(status().is4xxClientError())
      .andExpect(content().string(containsString("No Submodel with this ID was found")));
  }
}
//end::test[]
