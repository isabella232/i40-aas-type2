package com.sap.i40aas.datamanager.kafkaConnector;

import identifiables.Submodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import utils.SampleSubmodelFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication
public class KafkaApplication {

  public static void main(String[] args) throws Exception {

    ConfigurableApplicationContext context = SpringApplication.run(com.sap.i40aas.datamanager.kafkaConnector.KafkaApplication.class, args);

    MessageProducer producer = context.getBean(MessageProducer.class);
    MessageListener listener = context.getBean(MessageListener.class);
    /*
     * Sending a Hello World message to topic 'baeldung'.
     * Must be recieved by both listeners with group foo
     * and bar with containerFactory fooKafkaListenerContainerFactory
     * and barKafkaListenerContainerFactory respectively.
     * It will also be recieved by the listener with
     * headersKafkaListenerContainerFactory as container factory
     */
    //  producer.sendMessage("Hello, World!");
    //  listener.latch.await(10, TimeUnit.SECONDS);

    /*
     * Sending message to a topic with 5 partition,
     * each message to a different partition. But as per
     * listener configuration, only the messages from
     * partition 0 and 3 will be consumed.
     */

        /*
        for (int i = 0; i < 5; i++) {
            producer.sendMessageToPartion("Hello To Partioned Topic!", i);
        }
        listener.partitionLatch.await(10, TimeUnit.SECONDS);
*/
    /*
     * Sending message to 'filtered' topic. As per listener
     * configuration,  all messages with char sequence
     * 'World' will be discarded.
     */
    // producer.sendMessageToFiltered("Hello Baeldung!");
    // producer.sendMessageToFiltered("Hello World!");
    // listener.filterLatch.await(10, TimeUnit.SECONDS);

    /*
     * Sending message to 'greeting' topic. This will send
     * and recieved a java object with the help of
     * greetingKafkaListenerContainerFactory.
     */
    producer.sendGreetingMessage(new com.sap.i40aas.datamanager.kafkaConnector.Greeting("Greetings", "World!"));
    listener.greetingLatch.await(10, TimeUnit.SECONDS);


    producer.sendSubmodelMessage(SampleSubmodelFactory.Companion.getSampleSubmodel("sample-SM"));
    listener.submodelLatch.await(10, TimeUnit.SECONDS);

    context.close();
  }

  @Bean
  public MessageProducer messageProducer() {
    return new MessageProducer();
  }

  @Bean
  public MessageListener messageListener() {
    return new MessageListener();
  }

  public static class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, com.sap.i40aas.datamanager.kafkaConnector.Greeting> greetingKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Submodel> submodelKafkaTemplate;

    @Value(value = "${aasenv.topic.name}")
    private String topicName;

    @Value(value = "${asset.topic.name}")
    private String partionedTopicName;

    @Value(value = "${assetadminshell.topic.name}")
    private String filteredTopicName;

    @Value(value = "${conceptdescription.topic.name}")
    private String greetingTopicName;

    @Value(value = "${submodel.topic.name}")
    private String submodelTopicName;

    public void sendMessage(String message) {

      ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

      future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

        @Override
        public void onSuccess(SendResult<String, String> result) {
          System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
        }

        @Override
        public void onFailure(Throwable ex) {
          System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
        }
      });
    }

    public void sendMessageToPartion(String message, int partition) {
      kafkaTemplate.send(partionedTopicName, partition, null, message);
    }

    public void sendMessageToFiltered(String message) {
      kafkaTemplate.send(filteredTopicName, message);
    }

    public void sendGreetingMessage(com.sap.i40aas.datamanager.kafkaConnector.Greeting greeting) {
      greetingKafkaTemplate.send(greetingTopicName, greeting);
    }

    public void sendSubmodelMessage(Submodel submodel) {
      submodelKafkaTemplate.send(submodelTopicName, submodel);
    }
  }

  public static class MessageListener {

    private final CountDownLatch latch = new CountDownLatch(3);

    private final CountDownLatch partitionLatch = new CountDownLatch(2);

    private final CountDownLatch filterLatch = new CountDownLatch(2);

    private final CountDownLatch greetingLatch = new CountDownLatch(1);

    private final CountDownLatch submodelLatch = new CountDownLatch(1);

    @KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void listenGroupFoo(String message) {
      System.out.println("Received Messasge in group 'foo': " + message);
      latch.countDown();
    }

    @KafkaListener(topics = "${message.topic.name}", groupId = "bar", containerFactory = "barKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void listenGroupBar(String message) {
      System.out.println("Received Messasge in group 'bar': " + message);
      latch.countDown();
    }

    @KafkaListener(topics = "${message.topic.name}", containerFactory = "headersKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
      System.out.println("Received Messasge: " + message + " from partition: " + partition);
      latch.countDown();
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${partitioned.topic.name}", partitions = {"0", "3"}), autoStartup = "${listen.auto.start:false}")
    public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
      System.out.println("Received Message: " + message + " from partition: " + partition);
      this.partitionLatch.countDown();
    }

    @KafkaListener(topics = "${filtered.topic.name}", containerFactory = "filterKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void listenWithFilter(String message) {
      System.out.println("Recieved Message in filtered listener: " + message);
      this.filterLatch.countDown();
    }

    @KafkaListener(topics = "${greeting.topic.name}", containerFactory = "greetingKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void greetingListener(com.sap.i40aas.datamanager.kafkaConnector.Greeting greeting) {
      System.out.println("Recieved greeting message: " + greeting);
      this.greetingLatch.countDown();
    }

    @KafkaListener(topics = "${submodel.topic.name}", containerFactory = "submodelKafkaListenerContainerFactory", autoStartup = "${listen.auto.start:false}")
    public void submodelListener(Submodel submodel) {
      System.out.println("Recieved submodel message: " + submodel.getIdShort());
      this.submodelLatch.countDown();
    }

  }

}
