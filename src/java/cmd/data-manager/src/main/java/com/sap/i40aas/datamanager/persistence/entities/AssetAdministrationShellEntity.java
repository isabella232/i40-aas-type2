package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ASSETADMINISTRATIONSHELL")
public class AssetAdministrationShellEntity {

  @Id
  @URL
  private String id;

  @GeneratedValue(strategy = GenerationType.AUTO)
  private long sessionId;

  @Lob
  @NotBlank(message = "AASObject is mandatory")
  String aasObj;

  private List<SubmodelEntity> submodels;

  private List<AssetEntity> assets;


  @OneToMany(targetEntity = AssetEntity.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "aas_id")
  public List<AssetEntity> getAssets() {
    return assets;
  }

  public void setAssets(List<AssetEntity> assets) {
    this.assets = assets;
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

