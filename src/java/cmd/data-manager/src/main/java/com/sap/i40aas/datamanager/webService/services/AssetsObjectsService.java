package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.persistence.entities.AssetEntity;
import com.sap.i40aas.datamanager.persistence.repositories.AssetRepository;
import identifiables.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;

import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class AssetsObjectsService {

  private AssetsObjectsService assetObjectService;

  @Autowired
  private AssetRepository assetRepo;

  public Asset getAsset(String id) {

    AssetEntity assetEntityFound = assetRepo.findById(id).get();
    Asset assetFound = AASObjectsDeserializer.Companion.deserializeAsset(assetEntityFound.getAssetObj());
    return assetFound;
  }


  public List<Asset> getAllAssets() {
    List<Asset> assetsList = new ArrayList<>();
    assetRepo.findAll().forEach(assetEntity -> {
      assetsList.add(AASObjectsDeserializer.Companion.deserializeAsset(assetEntity.getAssetObj()));
    });
    return assetsList;
  }

  public Asset updateAsset(String id, Asset asset) {

    if (assetRepo.findById(id).isPresent()) {
      AssetEntity ast = new AssetEntity(id, AASObjectsDeserializer.Companion.serializeAsset(asset));
      assetRepo.save(ast);
      return asset;
    } else
      throw new java.util.NoSuchElementException();
  }

  public Asset addAsset(Asset asset) {
    log.debug("Upserting asset with id: " + asset.getIdentification().getId());

    AssetEntity asE = new AssetEntity(asset.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeAsset(asset));
    assetRepo.save(asE);
    return asset;
  }

  public Asset createAsset(String id, Asset asset) throws DuplicateResourceException {
// if Id not present create else if already there throw error
    if (assetRepo.findById(id).isPresent() == false) {
      AssetEntity asE = new AssetEntity(id, AASObjectsDeserializer.Companion.serializeAsset(asset));
      assetRepo.save(asE);
      return asset;
    } else
      throw new DuplicateResourceException();

  }


  public Asset deleteAsset(String id) {
    if (assetRepo.findById(id).isPresent()) {
      //find the Submodel so that it gets returned
      AssetEntity assetEntityFound = assetRepo.findById(id).get();
      Asset assetFounnd = AASObjectsDeserializer.Companion.deserializeAsset(assetEntityFound.getAssetObj());

      assetRepo.deleteById(id);
      return assetFounnd;
    } else
      throw new java.util.NoSuchElementException();
  }
}
