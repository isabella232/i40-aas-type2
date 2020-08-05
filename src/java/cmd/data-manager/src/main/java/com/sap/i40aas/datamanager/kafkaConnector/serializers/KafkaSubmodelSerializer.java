package com.sap.i40aas.datamanager.kafkaConnector.serializers;

import com.sap.i40aas.datamanager.AASObjectsDeserializer;
import identifiables.Submodel;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaSubmodelSerializer<T> implements Serializer<Submodel> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, Submodel data) {
        String stingified = AASObjectsDeserializer.Companion.getJson().stringify(Submodel.Companion.serializer(), data);
        return stingified.getBytes(StandardCharsets.UTF_8);
    }


    @Override
    public void close() {
    }


}
