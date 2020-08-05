package com.sap.i40aas.datamanager.kafkaConnector.serializers;

import com.sap.i40aas.datamanager.AASObjectsDeserializer;
import identifiables.Submodel;
import kotlinx.serialization.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

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

        try {
            //(deserialize value into Submodel object)

            return AASObjectsDeserializer.Companion.deserializeSubmodel(new String(value, StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new SerializationException("Error deserializing value", e);
        }
    }

    @Override
    public void close() {

    }


}


