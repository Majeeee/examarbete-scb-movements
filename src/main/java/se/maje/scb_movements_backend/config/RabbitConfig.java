package se.maje.scb_movements_backend.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /* ================= EXCHANGES ================= */
    public static final String USER_EVENTS_EXCHANGE = "user.events.exchange";
    public static final String MOVEMENT_EVENTS_EXCHANGE = "movement.events.exchange";

    /* ================= QUEUES ================= */
    public static final String USER_LOGIN_QUEUE = "user.login.queue";
    public static final String USER_REGISTER_QUEUE = "user.register.queue";
    public static final String MOVEMENT_QUEUE = "movement.queue";

    /* ================= ROUTING KEYS ================= */
    public static final String USER_LOGIN_ROUTING_KEY = "user.login";
    public static final String USER_REGISTER_ROUTING_KEY = "user.register";
    public static final String MOVEMENT_ROUTING_KEY = "movement.record";

    /* ================= EXCHANGE BEANS ================= */
    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(USER_EVENTS_EXCHANGE);
    }

    @Bean
    public TopicExchange movementEventsExchange() {
        return new TopicExchange(MOVEMENT_EVENTS_EXCHANGE);
    }

    /* ================= QUEUE BEANS ================= */
    @Bean
    public Queue userLoginQueue() {
        return QueueBuilder.durable(USER_LOGIN_QUEUE).build();
    }

    @Bean
    public Queue userRegisterQueue() {
        return QueueBuilder.durable(USER_REGISTER_QUEUE).build();
    }

    @Bean
    public Queue movementQueue() {
        return QueueBuilder.durable(MOVEMENT_QUEUE).build();
    }

    /* ================= BINDINGS ================= */
    @Bean
    public Binding loginBinding() {
        return BindingBuilder
                .bind(userLoginQueue())
                .to(userEventsExchange())
                .with(USER_LOGIN_ROUTING_KEY);
    }

    @Bean
    public Binding registerBinding() {
        return BindingBuilder
                .bind(userRegisterQueue())
                .to(userEventsExchange())
                .with(USER_REGISTER_ROUTING_KEY);
    }

    @Bean
    public Binding movementBinding() {
        return BindingBuilder
                .bind(movementQueue())
                .to(movementEventsExchange())
                .with(MOVEMENT_ROUTING_KEY);
    }
}
