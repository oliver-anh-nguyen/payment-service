package edu.miu.paymentservice.controller;

import edu.miu.paymentservice.entity.Payment;
import edu.miu.paymentservice.service.PaymentService;
import edu.miu.paymentservice.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${kafka.topicPayment}")
    private String topicPayment;

    @MockBean
    private PaymentService paymentService;

    @Test
    void should_receive_info_payment() throws Exception {
        UUID propertyId = UUID.randomUUID();
        System.out.println(propertyId);
        Payment mockData = new Payment("BANK", "anh@gmail.com", 500, 2, propertyId);
        paymentService.publish(topicPayment, mockData);

        mockMvc.perform(post("/topic/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.stringify(mockData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Received payment topic")));
    }
}
