package edu.miu.paymentservice.service;

import edu.miu.paymentservice.entity.Payment;

public interface PaymentService {
    void publish(String topic, Payment message);

    void listen(Payment message);
}
