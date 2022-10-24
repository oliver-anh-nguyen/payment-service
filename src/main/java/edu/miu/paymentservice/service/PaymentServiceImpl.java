package edu.miu.paymentservice.service;

import edu.miu.paymentservice.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final KafkaTemplate<String, Payment> kafkaTemplate;

    @Override
    public void publish(String topic, Payment payment) {
        ListenableFuture<SendResult<String, Payment>> future = kafkaTemplate.send(topic, payment);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(final SendResult<String, Payment> message) {
                log.info("sent message= " + message + " with offset= " + message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("unable to send message= " + payment, throwable);
            }
        });
    }

    @Override
    @KafkaListener(id = "reserveId", topics = "${kafka.topicPayment}")
    public void listen(Payment payment) {
        System.out.println("Received info from topicPayment: " + payment);
        publish(payment.getPaymentType().toUpperCase() + "_TOPIC", payment);
    }
}
