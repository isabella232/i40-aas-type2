package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Table(name = "SUBMODEL")
public class SubmodelEntity {

  @Id
  @URL
  private String id;

  @GeneratedValue(strategy = GenerationType.AUTO)
  private long sessionId;

  @Lob
  @NotBlank(message = "Name is mandatory")
  String submodelObj;

  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }

  @Lob
  public String getSubmodelObj() {
    return submodelObj;
  }

  public void setSubmodelObj(String submodelObj) {
    this.submodelObj = submodelObj;
  }


  public SubmodelEntity(String id, String sb) {
    this.id = id;
    this.submodelObj = sb;
  }

  public SubmodelEntity() {

  }
}

