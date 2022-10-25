package edu.miu.paymentservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class PaymentConfiguration {

    @Value("${kafka.topic.payment}")
    private String topicPayment;

    @Bean
    public NewTopic topicPayment() {
        return TopicBuilder.name(topicPayment)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicPaypal() {
        return TopicBuilder.name("PAYPAL_TOPIC")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicCredit() {
        return TopicBuilder.name("CREDIT_TOPIC")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicBank() {
        return TopicBuilder.name("BANK_TOPIC")
                .partitions(10)
                .replicas(1)
                .build();
    }
}
