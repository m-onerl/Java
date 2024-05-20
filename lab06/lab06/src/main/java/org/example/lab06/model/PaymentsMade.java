package org.example.lab06.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class PaymentsMade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long installationNumber;
    private LocalDate paymentDate;
    private double amount;

    @ManyToOne
    private Installation installation;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstallationNumber() {
        return installationNumber;
    }

    public void setInstallationNumber(Long installationNumber) {
        this.installationNumber = installationNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }

    public void setInstallationRouterNumber(Long routerNumber) {
        if (installation == null) {
            installation = new Installation();
        }
        installation.setRouterNumber(routerNumber);
    }

}
