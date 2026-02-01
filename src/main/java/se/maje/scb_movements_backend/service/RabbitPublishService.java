package se.maje.scb_movements_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import se.maje.scb_movements_backend.config.RabbitConfig;
import se.maje.scb_movements_backend.model.User;
import se.maje.scb_movements_backend.model.MovementRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class RabbitPublishService {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RabbitPublishService.class);

    // Publicerar movement record
    public void publishMovementRecord(MovementRecord record) {
        rabbitTemplate.convertAndSend(RabbitConfig.MOVEMENT_QUEUE, record);
        logger.info("Published movement record to queue '{}': {}", RabbitConfig.MOVEMENT_QUEUE, record);
    }

    // Publicerar när en user loggar in
    public void publishUserLoggedIn(User user) {
        rabbitTemplate.convertAndSend(RabbitConfig.USER_LOGIN_QUEUE, user);
        logger.info("Published user login event to queue '{}': {}", RabbitConfig.USER_LOGIN_QUEUE, user.getEmail());
    }

    // Publicerar när en user registreras
    public void publishUserRegistered(User user) {
        rabbitTemplate.convertAndSend(RabbitConfig.USER_REGISTER_QUEUE, user);
        logger.info("Published user registered event to queue '{}': {}", RabbitConfig.USER_REGISTER_QUEUE, user.getEmail());
    }
}
