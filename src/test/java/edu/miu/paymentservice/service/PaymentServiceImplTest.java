package edu.miu.paymentservice.service;

import edu.miu.paymentservice.entity.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;


@Testcontainers
@SpringBootTest
@ActiveProfiles("dev")
class PaymentServiceImplTest {
    @Autowired
    private KafkaTemplate<String, Payment> kafkaTemplate;

    @Autowired
    private PaymentService paymentService;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.1"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    public void should_publish_method_bank() throws InterruptedException {
        UUID propertyId = UUID.randomUUID();
        Payment payment = new Payment("BANK", "anh@gmail.com", 500.0, 2, propertyId);
        paymentService.publish("BANK_TOPIC", payment);
    }

    @Test
    public void should_publish_method_credit() throws InterruptedException {
        UUID propertyId = UUID.randomUUID();
        Payment payment = new Payment("CREDIT", "anh@gmail.com", 500.0, 2, propertyId);
        paymentService.publish("CREDIT_TOPIC", payment);
    }

    @Test
    public void should_publish_method_paypal() throws InterruptedException {
        UUID propertyId = UUID.randomUUID();
        Payment payment = new Payment("PAYPAL", "anh@gmail.com", 500.0, 2, propertyId);
        paymentService.publish("PAYPAL_TOPIC", payment);
    }
}
