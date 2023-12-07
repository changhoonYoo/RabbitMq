package com.rabbitmq.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private static final Logger log = LoggerFactory.getLogger(Listener.class);

    @RabbitListener(queues = "sample.queue")
    public void receiveMessage(final Message message) {
        log.info(message.toString());
    }
    //RabbitMQ 영역에 있는 sample.queue 라는 Queue 에 있는 메세지를 가지고 와서 logging 하는 코드


}
