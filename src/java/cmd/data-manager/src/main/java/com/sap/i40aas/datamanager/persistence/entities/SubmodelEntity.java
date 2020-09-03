package com.sap.i40aas.datamanager.persistence.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
//@Table(name = "Customers")

public class SubmodelEntity {

  @Id
  private String id;

  private String submodelObj;


  @Lob
  public String getSubmodelObj() {
    return submodelObj;
  }

  @Lob
  public void setSubmodelObj(String submodelObj) {
    this.submodelObj = submodelObj;
  }


  public SubmodelEntity(String id, String sb) {
    this.id = id;
    this.submodelObj = sb;
  }

  public SubmodelEntity() {

  }

  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }
}
