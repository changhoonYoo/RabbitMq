package com.rabbitmq.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
public class SampleController {

    // RabbitMQ 에서 사용할 Exchange 의 이름을 정의합니다.
    private static final String EXCHANGE_NAME = "sample.exchange";

    // RabbitMQ와 통신하기 위해 사용되는 RabbitTemplate 빈을 주입받습니다. 이 템플릿은 RabbitMQ와 상호작용하는 데 사용됩니다.
    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping("/sample/queue")
    public String samplePublish(@RequestParam(name = "message", required = false) String message) { //  RabbitMQ에 메시지를 publish 하는 메서드
        if (message == null) {
            message = "RabbitMQ + SpringBoot = Success!";
        }

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "sample.routing.#", message);
        // RabbitTemplate 을 사용하여 RabbitMQ에 메시지를 publish 합니다.
        // 여기서는 EXCHANGE_NAME 에 정의된 Exchange 로 메시지를 보내고, 라우팅 키는 "sample.routing.#"로 지정되어 있습니다.
        // 메시지 내용은 "RabbitMQ + SpringBoot = Success!"로 지정되어 있습니다.
        return "message sending! / message: " + message;
    }
}
