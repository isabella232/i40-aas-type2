package com.sap.i40aas.datamanager.persistence.entities;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "ASSET")
public class AssetEntity {

  @Id
  @URL
  private String id;

  //@GeneratedValue(strategy = GenerationType.AUTO)
  //private long sessionId;

  @Lob
  @NotBlank(message = "Name is mandatory")
  String assetObj;

  @OneToMany(targetEntity = AssetAdministrationShellEntity.class, mappedBy = "asset", fetch = FetchType.EAGER)
  private List<AssetAdministrationShellEntity> aasList = new ArrayList<>();

  public void setId(String id) {
    this.id = id;
  }

  @Id
  public String getId() {
    return id;
  }

  @Lob
  public String getAssetObj() {
    return assetObj;
  }

  public void setAssetObj(String assetObj) {
    this.assetObj = assetObj;
  }

  public void setAasList(List<AssetAdministrationShellEntity> aas) {
    this.aasList = aasList;
  }

  public List<AssetAdministrationShellEntity> getAasList() {
    return aasList;
  }

  public void addAssetAdminShell(AssetAdministrationShellEntity aas) {
    aasList.add(aas);
    aas.setAsset(this);
  }

  public void removeAssetAdminShell(AssetAdministrationShellEntity aas) {
    aasList.remove(aas);
    aas.setAsset(this);
  }


  public AssetEntity(String id, String asset) {
    this.id = id;
    this.assetObj = asset;
  }

  public AssetEntity() {

  }
}

