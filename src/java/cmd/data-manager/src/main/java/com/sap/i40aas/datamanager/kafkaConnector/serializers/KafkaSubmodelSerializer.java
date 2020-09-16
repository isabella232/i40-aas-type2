package com.sap.i40aas.datamanager.kafkaConnector.serializers;

import identifiables.Submodel;
import org.apache.kafka.common.serialization.Serializer;
import utils.AASObjectsDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaSubmodelSerializer<T> implements Serializer<Submodel> {


  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serialize(String s, Submodel data) {
    String stingified = AASObjectsDeserializer.Companion.serializeSubmodel(data);
    return stingified.getBytes(StandardCharsets.UTF_8);
  }


  @Override
  public void close() {
  }


}
