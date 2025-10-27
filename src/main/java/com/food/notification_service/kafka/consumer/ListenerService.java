package com.food.notification_service.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.notification_service.kafka.events.OrderReadyForDeliveryEvent;
import com.food.notification_service.kafka.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import static com.food.notification_service.kafka.topics.KafkaTopics.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListenerService {

    private final ObjectMapper objectMapper;

    /**
     * Handle PAYMENT_COMPLETED events from Kafka.
     * <p>
     * Deserializes the message into a PaymentEvent, acknowledges the Kafka
     * message and logs a notification message. Intended to simulate notifying
     * a user that payment completed.
     *
     * @param message the raw JSON message from the topic
     * @param ack     the acknowledgment provided by the Kafka listener
     * @throws JsonProcessingException if the message cannot be deserialized
     */
    @KafkaListener(topics = PAYMENT_COMPLETED, groupId = "${spring.kafka.consumer.group-id}")
    public void handlePaymentCompleted(String message, Acknowledgment ack) throws JsonProcessingException {
        PaymentEvent event = objectMapper.readValue(message, PaymentEvent.class);
        ack.acknowledge();
        log.info("Notification: Payment COMPLETED for user {} on order {}. Amount: {}",
                event.getUserId(), event.getOrderId(), event.getAmount());
    }

    /**
     * Handle ORDER_READY_FOR_DELIVER events from Kafka.
     * <p>
     * Deserializes the message into an OrderReadyForDeliveryEvent, acknowledges
     * the Kafka message and logs details that would be used to notify the user.
     *
     * @param message the raw JSON message from the topic
     * @param ack     the acknowledgment provided by the Kafka listener
     * @throws JsonProcessingException if the message cannot be deserialized
     */
    @KafkaListener(topics = ORDER_READY_FOR_DELIVER, groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderReadyForDelivery(String message, Acknowledgment ack) throws JsonProcessingException {
        OrderReadyForDeliveryEvent event = objectMapper.readValue(message, OrderReadyForDeliveryEvent.class);
        ack.acknowledge();
        log.info("Notification: Order {} is READY FOR DELIVERY for user {}. Address: {}. Item: {}. Message: {}",
                event.getOrderId(), event.getUserId(), event.getAddress(), event.getItem(), event.getMessage());
    }

    /**
     * Handle ORDER_DELIVERED events from Kafka.
     * <p>
     * Deserializes the message into an OrderReadyForDeliveryEvent, acknowledges
     * the Kafka message and logs that the order has been delivered.
     *
     * @param message the raw JSON message from the topic
     * @param ack     the acknowledgment provided by the Kafka listener
     * @throws JsonProcessingException if the message cannot be deserialized
     */
    @KafkaListener(topics = ORDER_DELIVERED, groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderDelivered(String message, Acknowledgment ack) throws JsonProcessingException {
        OrderReadyForDeliveryEvent event = objectMapper.readValue(message, OrderReadyForDeliveryEvent.class);
        ack.acknowledge();
        log.info("Notification: Order {} is DELIVERED for user {}. Address: {}. Item: {}. Message: {}",
                event.getOrderId(), event.getUserId(), event.getAddress(), event.getItem(), event.getMessage());
    }
}
