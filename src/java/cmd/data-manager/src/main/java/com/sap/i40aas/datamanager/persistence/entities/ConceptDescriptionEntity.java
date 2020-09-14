package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Table(name = "SUBMODEL")
public class ConceptDescriptionEntity {

  @Id
  @URL
  private String id;

  @GeneratedValue(strategy = GenerationType.AUTO)
  private long sessionId;

  @Lob
  @NotBlank(message = "Name is mandatory")
  String conceptDescrObj;

  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }

  @Lob
  public String getConceptDescrObj() {
    return conceptDescrObj;
  }

  public void setConceptDescrObj(String submodelObj) {
    this.conceptDescrObj = submodelObj;
  }


  public ConceptDescriptionEntity(String id, String cd) {
    this.id = id;
    this.conceptDescrObj = cd;
  }

  public ConceptDescriptionEntity() {

  }
}

