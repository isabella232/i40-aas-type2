package com.sap.i40aas.datamanager.persistence.repositories;

import com.sap.i40aas.datamanager.persistence.entities.AssetEntity;
import org.springframework.data.repository.CrudRepository;

public interface AssetRepository extends CrudRepository<AssetEntity, String> {
}
