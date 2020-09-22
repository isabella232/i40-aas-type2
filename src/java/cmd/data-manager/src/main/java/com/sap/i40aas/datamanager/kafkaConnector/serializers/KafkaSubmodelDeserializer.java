package com.sap.i40aas.datamanager.kafkaConnector.serializers;

import identifiables.Submodel;
import org.apache.kafka.common.serialization.Deserializer;
import utils.AASObjectsDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaSubmodelDeserializer implements Deserializer<Submodel> {

  private boolean isKey;

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    this.isKey = isKey;
  }

  @Override
  public Submodel deserialize(String s, byte[] value) {
    if (value == null) {
      return null;
    }

    return AASObjectsDeserializer.Companion.deserializeSubmodel(new String(value, StandardCharsets.UTF_8));
  }

  @Override
  public void close() {

  }


}


