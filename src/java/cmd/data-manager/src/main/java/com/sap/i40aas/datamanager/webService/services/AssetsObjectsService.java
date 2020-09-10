package com.sap.i40aas.datamanager.webService.services;

import identifiables.Asset;
import org.springframework.stereotype.Service;
import utils.SampleAssetFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AssetsObjectsService {

  private AssetsObjectsService assetObjectService;


  private final List<Asset> assetsList = new ArrayList<>(Arrays.asList(
    SampleAssetFactory.Companion.getSampleAsset("sampleAsset_1"),
    SampleAssetFactory.Companion.getSampleAsset("sampleAsset_2")
  ));


  public Asset getAsset(String assetId) {
//TODO: replace idshort with the following

    Asset assetFound = assetsList.stream().filter(asset -> asset.getIdentification().getId().equals(assetId)).findFirst().get();
    return assetFound;
  }


  public List<Asset> getAllAssets() {
    return assetsList;
  }

  public void updateAsset(String assetId, Asset asset) {
    if (assetsList.stream().anyMatch(asset1 -> asset.getIdentification().getId().equals(assetId))) {
      assetsList.set(assetsList.indexOf(assetsList.stream().filter(asset1 -> asset.getIdentification().getId().equals(assetId)).findFirst().get()), asset);
    } else
      assetsList.add(asset);
  }

  public void addAsset(Asset asset) {
    assetsList.add(asset);
  }

  public void deleteAsset(String assetId) {
    assetsList.removeIf(t -> t.getIdentification().getId().equals(assetId));
  }
}
