package org.example.lab06.controller;

import org.example.lab06.model.PaymentsMade;
import org.example.lab06.service.PaymentsMadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsMadeController {

    @Autowired
    private PaymentsMadeService paymentsMadeService;

    @GetMapping
    public List<PaymentsMade> getAllPaymentsMade() {
        return paymentsMadeService.getAllPaymentsMade();
    }


}
