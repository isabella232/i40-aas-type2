package com.sap.i40aas.datamanager.webService.services;

import identifiables.ConceptDescription;
import org.springframework.stereotype.Service;
import utils.SampleConceptDescriptionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ConceptDescriptionObjectsService {

  private ConceptDescriptionObjectsService cdObjectService;


  private final List<ConceptDescription> cdList = new ArrayList<>(Arrays.asList(
    SampleConceptDescriptionFactory.Companion.getSampleConceptDescription("sampleCD_1"),
    SampleConceptDescriptionFactory.Companion.getSampleConceptDescription("sampleCD_2")
  ));


  public ConceptDescription getConceptDescription(String cdId) {
//TODO: replace idshort with the following

    ConceptDescription cdFound = cdList.stream().filter(cd -> cd.getIdentification().getId().equals(cdId)).findFirst().get();
    return cdFound;
  }


  public List<ConceptDescription> getAllConceptDescriptions() {
    return cdList;
  }

  public void updateConceptDescription(String cdId, ConceptDescription cd) {
    if (cdList.stream().anyMatch(asset1 -> cd.getIdentification().getId().equals(cdId))) {
      cdList.set(cdList.indexOf(cdList.stream().filter(asset1 -> cd.getIdentification().getId().equals(cdId)).findFirst().get()), cd);
    } else
      cdList.add(cd);
  }

  public void addConceptDescription(ConceptDescription cd) {
    cdList.add(cd);
  }

  public void deleteConceptDescription(String cdId) {
    cdList.removeIf(t -> t.getIdentification().getId().equals(cdId));
  }
}
