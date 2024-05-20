package org.example.lab06.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class AccruedCharges {

    @Id
    private LocalDate paymentDueDate;

    private Double amountDue;

    @Column(name = "INSTALLATION_ROUTER_NUMBER", insertable = false, updatable = false)
    private Long installationRouterNumber;

    @ManyToOne
    private Installation installation;

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public Long getInstallationRouterNumber() {
        return installationRouterNumber;
    }

    public void setInstallationRouterNumber(Long installationRouterNumber) {
        this.installationRouterNumber = installationRouterNumber;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }
}
