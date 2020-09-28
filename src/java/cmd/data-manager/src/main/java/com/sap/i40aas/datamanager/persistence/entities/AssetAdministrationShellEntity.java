package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "ASSETADMINISTRATIONSHELL")
public class AssetAdministrationShellEntity {

  @Id
  @URL
  private String id;

  //@GeneratedValue(strategy = GenerationType.AUTO)
  //private long sessionId;

  @Lob
  @NotBlank(message = "AASObject is mandatory")
  String aasObj;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  })
  @JoinTable(
    name = "aasToSubmodels",
    joinColumns = @JoinColumn(name = "aas_id"),
    inverseJoinColumns = @JoinColumn(name = "submodel_id"))
  private List<SubmodelEntity> submodels = new ArrayList<>();


  @ManyToOne()
  @JoinColumn(name = "asset_id")
  private AssetEntity asset;


  public AssetEntity getAsset() {
    return asset;
  }

  public void setAsset(AssetEntity asset) {
    this.asset = asset;
  }

  public List<SubmodelEntity> getSubmodels() {
    return submodels;
  }

  public void setSubmodels(List<SubmodelEntity> submodels) {
    this.submodels = submodels;
  }

  public void addSubmodel(SubmodelEntity submodelEntity) {
    submodels.add(submodelEntity);
    submodelEntity.getAasList().add(this);
  }

  public void removeSubmodel(SubmodelEntity submodelEntity) {
    submodels.remove(submodelEntity);
    submodelEntity.getAasList().remove(this);
  }


  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }

  @Lob
  public String getAasObj() {
    return aasObj;
  }

  public void setAasObj(String submodelObj) {
    this.aasObj = submodelObj;
  }


  public AssetAdministrationShellEntity(String id, String aas) {
    this.id = id;
    this.aasObj = aas;
  }

  public AssetAdministrationShellEntity() {

  }
}

