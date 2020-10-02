package com.sap.i40aas.datamanager.webService.services;

import com.sap.i40aas.datamanager.errorHandling.DuplicateResourceException;
import com.sap.i40aas.datamanager.persistence.entities.AssetAdministrationShellEntity;
import com.sap.i40aas.datamanager.persistence.repositories.AssetAdministrationShellRepository;
import identifiables.AssetAdministrationShell;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.AASObjectsDeserializer;

import java.util.ArrayList;
import java.util.List;

@Slf4j

@Service
public class AssetAdminShellObjectsService {

  private AssetAdminShellObjectsService assetAdminShellObjectsService;

  @Autowired
  private AssetAdministrationShellRepository assetAdministrationShellRepository;

  public AssetAdministrationShell getAssetAdministrationShell(String id) {

    AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(id).get();
    AssetAdministrationShell aasFound = AASObjectsDeserializer.Companion.deserializeAAS(aasEntityFound.getAasObj());
    return aasFound;
  }


  public List<AssetAdministrationShell> getAllAssetAdministrationShells() {
    List<AssetAdministrationShell> aasList = new ArrayList<>();
    assetAdministrationShellRepository.findAll().forEach(assetAdministrationShellEntity -> {
      aasList.add(AASObjectsDeserializer.Companion.deserializeAAS(assetAdministrationShellEntity.getAasObj()));
    });
    return aasList;
  }

  public AssetAdministrationShell updateAssetAdministrationShell(String id, AssetAdministrationShell aas) {

    if (assetAdministrationShellRepository.findById(id).isPresent()) {
      AssetAdministrationShellEntity sbE = new AssetAdministrationShellEntity(id, AASObjectsDeserializer.Companion.serializeAAS(aas));
      assetAdministrationShellRepository.save(sbE);
      return aas;
    } else
      throw new java.util.NoSuchElementException();
  }

  public AssetAdministrationShell addAssetAdministrationShell(AssetAdministrationShell aas) {
    log.debug("Upserting aas with id: " + aas.getIdentification().getId());

    AssetAdministrationShellEntity sbE = new AssetAdministrationShellEntity(aas.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeAAS(aas));
    assetAdministrationShellRepository.save(sbE);
    return aas;

  }

  public AssetAdministrationShell addAssetAdministrationShellWithRelation(AssetAdministrationShell aas) {
    log.debug("Upserting aas with id: " + aas.getIdentification().getId());

    AssetAdministrationShellEntity sbE = new AssetAdministrationShellEntity(aas.getIdentification().getId(), AASObjectsDeserializer.Companion.serializeAAS(aas));
    assetAdministrationShellRepository.save(sbE);
    return aas;

  }

  public AssetAdministrationShell createAssetAdministrationShell(String id, AssetAdministrationShell aas) throws DuplicateResourceException {
// if Id not present create else if already there throw error
    if (assetAdministrationShellRepository.findById(id).isPresent() == false) {

      String aasSer = AASObjectsDeserializer.Companion.serializeAAS(aas);

      AssetAdministrationShellEntity sbE = new AssetAdministrationShellEntity(id, aasSer);
      assetAdministrationShellRepository.save(sbE);
      return aas;
    } else
      throw new DuplicateResourceException();

  }

  public AssetAdministrationShell deleteAssetAdministrationShell(String id) {
    if (assetAdministrationShellRepository.findById(id).isPresent()) {
      //find the Submodel so that it gets returned
      AssetAdministrationShellEntity aasEntityFound = assetAdministrationShellRepository.findById(id).get();
      AssetAdministrationShell aasFound = AASObjectsDeserializer.Companion.deserializeAAS(aasEntityFound.getAasObj());

      assetAdministrationShellRepository.deleteById(id);
      return aasFound;
    } else
      throw new java.util.NoSuchElementException();
  }

}
