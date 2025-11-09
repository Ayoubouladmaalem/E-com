package com.ouaailelaouad.payment.infrastructure.messaging;

import com.ouaailelaouad.payment.domain.event.PaymentConfirmationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmationProducer {

    private final KafkaTemplate<String, PaymentConfirmationEvent> kafkaTemplate;

    public void sendConfirmation(PaymentConfirmationEvent event) {
        log.info("Sending payment confirmation for order: {}", event.getOrderId());

        Message<PaymentConfirmationEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "payment-confirmation-topic")
                .setHeader(KafkaHeaders.KEY, event.getPaymentReference())
                .build();

        kafkaTemplate.send(message);
        log.info("Payment confirmation sent successfully for order: {}", event.getOrderId());
    }
}