package com.sap.i40aas.datamanager.kafkaConnector;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

  @Value(value = "${kafka.bootstrapAddress}")
  private String bootstrapAddress;

  @Value(value = "${aasenv.topic.name}")
  private String aasEnvTopicName;

  @Value(value = "${asset.topic.name}")
  private String assetTopicName;

  @Value(value = "${assetadminshell.topic.name}")
  private String assetAdminShellTopicName;

  @Value(value = "${conceptdescription.topic.name}")
  private String conceptDescriptionTopicName;

  @Value(value = "${submodel.topic.name}")
  private String submodelTopicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic createAASEnvTopic() {
    return new NewTopic(aasEnvTopicName, 1, (short) 1);
  }

  @Bean
  public NewTopic createAssetTopic() {
    return new NewTopic(assetTopicName, 6, (short) 1);
  }

  @Bean
  public NewTopic createAssetAdminShellTopic() {
    return new NewTopic(assetAdminShellTopicName, 1, (short) 1);
  }

  @Bean
  public NewTopic createConceptDescriptionTopic() {
    return new NewTopic(conceptDescriptionTopicName, 1, (short) 1);
  }

  @Bean
  public NewTopic createSubmodelTopic() {
    return new NewTopic(submodelTopicName, 1, (short) 1);
  }
}
