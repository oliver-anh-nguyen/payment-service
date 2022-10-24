package edu.miu.paymentservice.controller;

import edu.miu.paymentservice.entity.Payment;
import edu.miu.paymentservice.entity.Response;
import edu.miu.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topic/payment")
public class PaymentController {

    @Value("${kafka.topic.payment}")
    private String topicPayment;

    private final PaymentService paymentService;

    @PostMapping
    public Response send(@RequestBody Payment payment) {
        paymentService.publish(topicPayment, payment);
        return new Response("Received payment topic");
    }
}
