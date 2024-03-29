package com.sipibibu.messenger.extern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class Receiver {
    Logger logger = LoggerFactory.getLogger(Receiver.class);
    @Value("${spring.rabbitmq.queues.messages.name}")
    String messagesQueueName;

    @RabbitListener(queues ="${spring.rabbitmq.queues.messages.name}")
    public void worker(String message) {
        logger.info("accepted on worker: " + message);
    }

}
