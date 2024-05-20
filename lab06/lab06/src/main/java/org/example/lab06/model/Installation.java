package org.example.lab06.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Installation {

    @Id
    private Long routerNumber;
    private String address;
    private String serviceType;

    @OneToMany(mappedBy = "installation")
    private List<AccruedCharges> accruedCharges;

    @OneToMany(mappedBy = "installation")
    private List<PaymentsMade> paymentsMade;

    @ManyToOne
    private Customer customer;

    public void setRouterNumber(Long routerNumber) {
        this.routerNumber = routerNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Long getRouterNumber() {
        return routerNumber;
    }

    @Override
    public String toString() {
        return "Installation{" +
                "routerNumber=" + routerNumber +
                ", address='" + address + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}

