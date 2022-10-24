package edu.miu.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String paymentType;
    private String email;
    private double total;
    private int night;
    private UUID propertyId;
}
