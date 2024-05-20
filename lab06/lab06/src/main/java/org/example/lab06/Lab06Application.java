package org.example.lab06;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab06.model.AccruedCharges;
import org.example.lab06.model.Customer;
import org.example.lab06.model.Installation;
import org.example.lab06.model.PaymentsMade;
import org.example.lab06.service.PaymentsMadeService;
import org.example.lab06.model.PriceList;
import org.example.lab06.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Lab06Application implements CommandLineRunner {
    private final CustomerService customerService;
    private final InstallationService installationService;
    private final PriceListService priceListService;
    private final AccruedChargesService accruedChargesService;
    private PaymentsMadeService paymentsMadeService;
    private static final Logger logger = LogManager.getLogger(Lab06Application.class);

    private final Scanner scanner;
    @Autowired
    public Lab06Application(CustomerService customerService, InstallationService installationService,
                            PriceListService priceListService, AccruedChargesService accruedChargesService,
                            PaymentsMadeService paymentsMadeService) {
        this.customerService = customerService;
        this.installationService = installationService;
        this.priceListService = priceListService;
        this.accruedChargesService = accruedChargesService;
        this.paymentsMadeService = paymentsMadeService;
        this.scanner = new Scanner(System.in);
    }


    public void run(String... args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Dodaj klienta");
            System.out.println("2. Usuń klienta");
            System.out.println("3. Pokaż listę klientów");
            System.out.println("4. Dodaj instalację");
            System.out.println("5. Pokaż listę instalacji");
            System.out.println("6. Dodaj cennik");
            System.out.println("7. Pokaż listę cenników");
            System.out.println("8. Zarejestruj wpłate");
            System.out.println("9. Korekta wpłaty");
            System.out.println("10. Wyjdź");

            System.out.print("Wybierz co chcesz zrobić: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    deleteCustomer();
                    break;
                case 3:
                    showAllCustomers();
                    break;
                case 4:
                    addInstallation();
                    break;
                case 5:
                    showAllInstallations();
                    break;
                case 6:
                    addPriceList();
                    break;
                case 7:
                    showAllPriceLists();
                    break;
                case 8:
                    registerPayment();
                    break;
                case 9:
                    applyCorrection();
                    break;
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("Błąd, spróbuj ponownie");
            }
        }

        scanner.close();
    }

    private void addCustomer() {
        System.out.println("Wprowadź dane klienta:");
        System.out.print("Imię: ");
        String firstName = scanner.nextLine();

        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine();

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customerService.save(customer);

        System.out.println("Klient został dodany.");
    }

    private void deleteCustomer() {
        System.out.print("Id klienta do usunięcia: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        customerService.deleteById(id);

        System.out.println("Klient usunięty.");
    }

    private void showAllCustomers() {
        System.out.println("Lista klientów:");
        List<Customer> customers = customerService.getAllCustomers();

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void addInstallation() {
        System.out.println("Wprowadź dane instalacji:");

        System.out.print("Numer routera: ");
        Long routerNumber = Long.parseLong(scanner.nextLine());

        System.out.print("Adres: ");
        String address = scanner.nextLine();

        System.out.print("Typ usługi: ");
        String serviceType = scanner.nextLine();

        System.out.print("ID klienta: ");
        Long customerId = Long.parseLong(scanner.nextLine());

        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Nie znaleziono klienta o podanym ID.");
            return;
        }

        Installation installation = new Installation();
        installation.setRouterNumber(routerNumber);
        installation.setAddress(address);
        installation.setServiceType(serviceType);
        installation.setCustomer(customer);
        installationService.save(installation);


        Installation savedInstallation = installationService.getInstallationByRouterNumber(routerNumber);


        if (savedInstallation == null) {
            System.out.println("Błąd podczas zapisywania instalacji.");
            return;
        }
        System.out.print("Data płatności (RRRR-MM-DD): ");
        String paymentDueDate = scanner.nextLine();

        System.out.print("Kwota do zapłaty: ");
        double amountDue = Double.parseDouble(scanner.nextLine());


        Long savedRouterNumber = savedInstallation.getRouterNumber();


        AccruedCharges accruedCharges = new AccruedCharges();
        accruedCharges.setPaymentDueDate(LocalDate.parse(paymentDueDate));
        accruedCharges.setAmountDue(amountDue);
        accruedCharges.setInstallationRouterNumber(savedRouterNumber);
        accruedChargesService.save(accruedCharges);

        System.out.println("Instalacja została dodana.");
        if (LocalDate.parse(paymentDueDate).isBefore(LocalDate.now())) {
            logger.warn("Data płatności minęła. Data płatności: " + paymentDueDate + ", Kwota do zapłaty: " + amountDue);
        } else {
            logger.info("Data płatności jeszcze nie minęła. Data płatności: " + paymentDueDate + ", Kwota do zapłaty: " + amountDue);
        }
    }



    private void showAllInstallations() {
        System.out.println("Lista instalacji:");
        List<Installation> installations = installationService.getAllInstallations();

        for (Installation installation : installations) {
            System.out.println(installation);
        }
    }

    private void addPriceList() {
        System.out.println("Wprowadź dane cennika:");

        System.out.print("Typ usługi: ");
        String serviceType = scanner.nextLine();

        System.out.print("Cena: ");
        double price = Double.parseDouble(scanner.nextLine());

        priceListService.addPriceList(serviceType, price);

        System.out.println("Dodano nowy cennik: " + serviceType + " - " + price);
    }

    private void showAllPriceLists() {
        System.out.println("Lista cenników:");
        List<PriceList> priceLists = priceListService.getAllPriceLists();

        for (PriceList priceList : priceLists) {

            System.out.println("Typ usługi: " + priceList.getServiceType() + ", Cena: " + priceList.getPrice());
        }
    }

    private void registerPayment() {
        System.out.println("Wprowadź dane wpłaty:");
        System.out.print("Numer instalacji: ");
        Long installationNumber = Long.parseLong(scanner.nextLine());

        Installation installation = installationService.getInstallationByRouterNumber(installationNumber);
        if (installation == null) {
            System.out.println("Instalacja o podanym numerze nie istnieje.");
            return;
        }

        System.out.print("Data płatności (RRRR-MM-DD): ");
        String paymentDate = scanner.nextLine();

        System.out.print("Kwota: ");
        double amount = Double.parseDouble(scanner.nextLine());

        PaymentsMade paymentMade = new PaymentsMade();
        paymentMade.setInstallation(installation);
        paymentMade.setPaymentDate(LocalDate.parse(paymentDate));
        paymentMade.setAmount(amount);

        paymentsMadeService.savePayment(paymentMade);

        System.out.println("Wpłata została zarejestrowana.");
    }




    private void applyCorrection() {
        System.out.println("Wprowadź dane korekty:");
        System.out.print("Numer instalacji: ");
        Long installationNumber = Long.parseLong(scanner.nextLine());

        System.out.print("Data korekty (RRRR-MM-DD): ");
        String correctionDate = scanner.nextLine();

        System.out.print("Kwota korekty: ");
        double correctionAmount = Double.parseDouble(scanner.nextLine());


        PaymentsMade paymentMade = paymentsMadeService.findPaymentByInstallationNumber(installationNumber);

        if (paymentMade != null) {

            double updatedAmount = paymentMade.getAmount() + correctionAmount;
            paymentMade.setAmount(updatedAmount);

            paymentsMadeService.savePayment(paymentMade);
            System.out.println("Korekta została zastosowana.");
        } else {
            System.out.println("Nie znaleziono wpłaty dla podanej instalacji.");
        }
    }





    public static void main(String[] args) {
        SpringApplication.run(Lab06Application.class, args);
    }
}
