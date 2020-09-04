package com.sap.i40aas.datamanager.persistence.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "SUBMODEL")
public class SubmodelEntity {

  @Id
  private String id;
  @Lob
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

