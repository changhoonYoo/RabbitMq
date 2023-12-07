package com.rabbitmq.publish;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;

public class SampleConfig { //RabbitMQ와 관련된 여러 빈(bean)들을 정의합니다.

    private static final String EXCHANGE_NAME = "sample.exchange";
    private static final String QUEUE_NAME = "sample.queue";
    private static final String ROUTING_KEY = "sample.routing.#";
    // RabbitMQ의 Exchange, Queue, Routing Key의 이름을 나타냅니다.

    // exchange() 메서드는 TopicExchange 빈을 생성합니다.
    // 이 Exchange 는 토픽 기반 라우팅을 지원하는 Exchange 로 메시지의 라우팅을 토픽 패턴으로 정의할 수 있습니다.
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // queue() 메서드는 Queue 빈을 생성합니다.
    // 이 Queue 는 RabbitMQ에 메시지를 보낼 때 메시지가 전달되는 대상이 됩니다.
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    // binding() 메서드는 Queue 와 TopicExchange 를 연결하는 Binding 빈을 생성합니다.
    // 이 Binding 은 어떤 라우팅 키로 메시지가 전송될 때 해당 메시지가 어떤 큐로 전송되어야 하는지를 정의합니다.
    // 여기서는 ROUTING_KEY 인 "sample.routing.#"으로 설정되어 있습니다.
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // rabbitTemplate() 메서드는 RabbitMQ와 통신하기 위한 RabbitTemplate 빈을 생성합니다.
    // 이 템플릿은 메시지를 publish 하고 RabbitMQ와 상호작용하는 데 사용됩니다.
    // MessageConverter 를 주입받아 메시지를 변환하는 데 사용하며, ConnectionFactory 는 RabbitMQ 와의 연결을 설정합니다.
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /* 이러한 빈들은 Spring 애플리케이션 컨텍스트에 등록되어 Spring Boot 애플리케이션이 실행될 때 자동으로 생성 및 구성되어 사용됩니다.
     * 이 코드를 통해 RabbitMQ로 메시지를 publish 하는 데 필요한 기본 구성이 설정되었습니다.
     */
}
