package com.miriam.str_consumer.listener;

import com.miriam.str_consumer.service.MensajeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class StrConsumerListener {

    private static final Logger log = LogManager.getLogger(StrConsumerListener.class);

    @Autowired
    private MensajeService mensajeService;

    @KafkaListener(groupId = "group-1",
            topicPartitions = @TopicPartition(topic = "str-topic", partitions = {"0"}),
            containerFactory = "validMessageContainerFactory")
    public void listener1(String message) {
        log.info("LISTENER1 ::: Recibiendo un mensaje {}", message);
        mensajeService.guardarMensaje(message);
    }

    @KafkaListener(groupId = "group-1",
            topicPartitions = @TopicPartition(topic = "str-topic", partitions = {"1"}),
            containerFactory = "validMessageContainerFactory")
    public void listener2(String message) {
        log.info("LISTENER2 ::: Recibiendo un mensaje {}", message);
        mensajeService.guardarMensaje(message);
    }

    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "validMessageContainerFactory")
    public void listener3(String message) {
        log.info("LISTENER3 ::: Recibiendo un mensaje {}", message);
        mensajeService.guardarMensaje(message);
    }
}