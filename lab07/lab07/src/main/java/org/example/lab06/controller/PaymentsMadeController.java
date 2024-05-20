package org.example.lab06.controller;

import org.example.lab06.model.PaymentsMade;
import org.example.lab06.service.PaymentsMadeService;
import org.example.lab06.service.CustomerService;
import org.example.lab06.service.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.lab06.model.Customer;
import org.example.lab06.model.Installation;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentsMadeController {

    private final PaymentsMadeService paymentsMadeService;
    private final CustomerService customerService;
    private final InstallationService installationService;

    @Autowired
    public PaymentsMadeController(PaymentsMadeService paymentsMadeService, CustomerService customerService, InstallationService installationService) {
        this.paymentsMadeService = paymentsMadeService;
        this.customerService = customerService;
        this.installationService = installationService;
    }

    @GetMapping("/list")
    public String getAllPaymentsMade(Model model) {
        List<PaymentsMade> paymentsMade = paymentsMadeService.getAllPaymentsMade();
        model.addAttribute("paymentsMade", paymentsMade);
        return "paymentsmade-list";
    }

    @GetMapping("/add")
    public String showAddPaymentForm(Model model) {
        PaymentsMade payment = new PaymentsMade();
        model.addAttribute("payment", payment);
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        List<Installation> installations = installationService.getAllInstallations();
        model.addAttribute("installations", installations);
        return "add-paymentsmade";
    }

    @PostMapping("/add")
    public String addPayment(@ModelAttribute("payment") PaymentsMade payment,
                             @RequestParam("customerId") Long customerId,
                             @RequestParam(value = "installationId", required = false) Long installationId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (installationId != null) {
            Installation installation = installationService.getInstallationById(installationId);
            payment.setInstallation(installation);
        }
        payment.setCustomer(customer);
        paymentsMadeService.savePayment(payment);
        return "redirect:/payments/list";
    }

}
