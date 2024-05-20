package org.example.lab06.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Dodano identyfikator

    private Long routerNumber;
    private String address;
    private String serviceType;
    private Long installationRouterNumber;

    @OneToMany(mappedBy = "installation")
    private List<AccruedCharges> accruedCharges;

    @OneToMany(mappedBy = "installation")
    private List<PaymentsMade> paymentsMade;

    @ManyToOne
    private Customer customer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getRouterNumber() {
        return routerNumber;
    }

    public void setRouterNumber(Long routerNumber) {
        this.routerNumber = routerNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getInstallationRouterNumber() {
        return installationRouterNumber;
    }

    public void setInstallationRouterNumber(Long installationRouterNumber) {
        this.installationRouterNumber = installationRouterNumber;
    }

    public List<AccruedCharges> getAccruedCharges() {
        return accruedCharges;
    }

    public void setAccruedCharges(List<AccruedCharges> accruedCharges) {
        this.accruedCharges = accruedCharges;
    }

    public List<PaymentsMade> getPaymentsMade() {
        return paymentsMade;
    }

    public void setPaymentsMade(List<PaymentsMade> paymentsMade) {
        this.paymentsMade = paymentsMade;
    }

    @Override
    public String toString() {
        return "Installation{" +
                "id=" + id +
                ", routerNumber=" + routerNumber +
                ", address='" + address + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}
