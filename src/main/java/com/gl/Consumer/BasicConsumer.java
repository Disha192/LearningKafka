package com.gl.Consumer;//package com.gl.Consumer;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Properties;
//
//public class KafkaMessageConsumer {
//
//    public static void main(String[] args) {
//        // Kafka properties
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka server address
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group"); // Consumer group ID
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Start from the earliest message
//
//        // Create a Kafka consumer
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
//
//        // Subscribe to topics
//        consumer.subscribe(Arrays.asList("NewPaymentStatus", "auditlogs"));
//
//        // Poll for messages
//        try {
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); // Polling duration
//                if (records.isEmpty()) {
//                    System.out.println("No messages received");
//                }
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s%n",
//                            record.topic(),
//                            record.partition(),
//                            record.offset(),
//                            record.key(),
//                            record.value());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            consumer.close();
//        }
//    }
//}


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class BasicConsumer {
    public static void main(String[] args) {
        System.out.println("*** Starting Basic Consumer ***");
        Properties settings = new Properties();
        settings.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(ConsumerConfig.GROUP_ID_CONFIG, "hello_world_group");
        settings.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        settings.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        settings.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(settings);
        consumer.subscribe(Collections.singletonList("paymentaudit"));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("### Stopping Basic Consumer ###");
            consumer.close();
        }));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: value = %s%n",  record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
