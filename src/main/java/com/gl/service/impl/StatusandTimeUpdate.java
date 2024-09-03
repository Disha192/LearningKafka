package com.gl.service.impl;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class StatusandTimeUpdate {
    private static final Logger logger = LoggerFactory.getLogger(StatusandTimeUpdate.class);

    private String status;
    private final String[] statuses = {"initiated", "in progress", "in transit", "completed"};
    private int currentIndex = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public StatusandTimeUpdate() {
        this("localhost:9092", "NewPaymentStatus"); // Default values
    }

    public StatusandTimeUpdate(String bootstrapServers, String topic) {
        this.status = statuses[currentIndex];
        this.topic = topic;

        Properties settings = new Properties();
        settings.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        settings.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        settings.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(settings);
    }

    public void startStatusChange() {
        scheduler.scheduleAtFixedRate(() -> {
            currentIndex = (currentIndex + 1) % statuses.length;
            status = statuses[currentIndex];
            String message = "Status: " + status + ", Time: " + new Date();
            logger.info(message);

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, status, message);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Produced record with key {} to topic {}, partition {}, offset {}",
                            status, metadata.topic(), metadata.partition(), metadata.offset());
                } else {
                    logger.error("Exception occurred while sending record: {}", exception.getMessage());
                }
            });
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void stopStatusChange() {
        scheduler.shutdown();
        producer.close();
    }

    public String getStatus() {
        return status;
    }

    public Date getDate() {
        return new Date();
    }
}