package org.example.lab06.service;

import org.example.lab06.model.PaymentsMade;
import org.example.lab06.repository.PaymentsMadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsMadeService {

    @Autowired
    private PaymentsMadeRepository paymentsMadeRepository;

    public List<PaymentsMade> getAllPaymentsMade() {
        return paymentsMadeRepository.findAll();
    }

    public void savePayment(PaymentsMade paymentMade) {
        paymentsMadeRepository.save(paymentMade);
    }

    public PaymentsMade findPaymentByInstallationNumber(Long installationNumber) {
        return paymentsMadeRepository.findByInstallationRouterNumber(installationNumber);
    }
}
