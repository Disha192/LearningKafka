package com.gl.service.impl;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;

public class AuditLogging {
    private static final Properties producerProps = new Properties();

    static {
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    private static final KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

    public static void logEvent(int transactionId, int accountId, String message, Date transactionDate, String transactionType) {
        String logMessage = String.format("TransactionID: %d, AccountID: %d, Recipient Name: %s, Date: %s, Type: %s",
                transactionId, accountId, message, transactionDate.toString(), transactionType);
        ProducerRecord<String, String> record = new ProducerRecord<>("paymentaudit", logMessage);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                System.err.printf("Failed to send record to Kafka: %s%n", exception.getMessage());
            } else {
                System.out.printf("Record sent to Kafka: topic=%s partition=%d offset=%d%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }
    public static void close() {
        producer.close();
    }
}
