package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "SUBMODEL")
public class SubmodelEntity {

  @Id
  @URL
  private String id;

  @GeneratedValue(strategy = GenerationType.AUTO)
  private long sessionId;

  @Lob
  @NotBlank(message = "SubmodelObj is mandatory")
  String submodelObj;


  @ManyToMany(mappedBy = "submodels")
  private List<AssetAdministrationShellEntity> aasList = new ArrayList<>();

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

  public long getSessionId() {
    return sessionId;
  }

  public void setSessionId(long sessionId) {
    this.sessionId = sessionId;
  }

  public void setAasList(List<AssetAdministrationShellEntity> aasList) {
    this.aasList = aasList;
  }

  public List<AssetAdministrationShellEntity> getAasList() {
    return aasList;
  }

  public SubmodelEntity(String id, String sb) {
    this.id = id;
    this.submodelObj = sb;
  }

  public SubmodelEntity() {

  }
}

