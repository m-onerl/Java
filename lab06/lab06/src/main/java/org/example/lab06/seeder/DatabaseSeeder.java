package org.example.lab06.seeder;

import org.example.lab06.model.*;
import org.example.lab06.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseSeeder {

    private final CustomerRepository customerRepository;
    private final InstallationRepository installationRepository;
    private final AccruedChargesRepository accruedChargesRepository;
    private final PriceListRepository priceListRepository;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository, InstallationRepository installationRepository, AccruedChargesRepository accruedChargesRepository, PriceListRepository priceListRepository) {
        this.customerRepository = customerRepository;
        this.installationRepository = installationRepository;
        this.accruedChargesRepository = accruedChargesRepository;
        this.priceListRepository = priceListRepository;

    }

    @PostConstruct
    public void seedDatabase() {

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Sebastian");
        customer1.setLastName("Woloszyn");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Anna");
        customer2.setLastName("Kowalczyk");


        customerRepository.save(customer1);
        customerRepository.save(customer2);


        Installation installation1 = new Installation();
        installation1.setRouterNumber(1001L);
        installation1.setAddress("ul. Łukasza Górnickiego 22, Wrocław");
        installation1.setServiceType("Internet");
        installation1.setCustomer(customer1);

        Installation installation2 = new Installation();
        installation2.setRouterNumber(1002L);
        installation2.setAddress("ul. Słoneczna 20, Kraków");
        installation2.setServiceType("TV");
        installation2.setCustomer(customer2);

        installationRepository.save(installation1);
        installationRepository.save(installation2);


        AccruedCharges accruedCharges1 = new AccruedCharges();
        accruedCharges1.setPaymentDueDate(LocalDate.of(2024, 5, 1));
        accruedCharges1.setAmountDue(80.0);
        accruedCharges1.setInstallation(installation1);

        AccruedCharges accruedCharges2 = new AccruedCharges();
        accruedCharges2.setPaymentDueDate(LocalDate.of(2024, 5, 1));
        accruedCharges2.setAmountDue(50.0);
        accruedCharges2.setInstallation(installation2);


        accruedChargesRepository.save(accruedCharges1);
        accruedChargesRepository.save(accruedCharges2);
        PriceList priceList1 = new PriceList();
        priceList1.setServiceType("Basic Internet");
        priceList1.setPrice(50.0);

        PriceList priceList2 = new PriceList();
        priceList2.setServiceType("Basic TV");
        priceList2.setPrice(30.0);

        priceListRepository.save(priceList1);
        priceListRepository.save(priceList2);

    }
}