package org.example.lab06.controller;

import org.example.lab06.model.Customer;
import org.example.lab06.model.Installation;
import org.example.lab06.service.CustomerService;
import org.example.lab06.service.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
@RequestMapping("/installations")
public class InstallationController {

    private final InstallationService installationService;
    private final CustomerService customerService;

    @Autowired
    public InstallationController(InstallationService installationService, CustomerService customerService) {
        this.installationService = installationService;
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String showAllInstallations(Model model) {
        List<Installation> installations = installationService.getAllInstallations();
        model.addAttribute("installations", installations);
        return "installation-list";
    }

    @GetMapping("/add")
    public String showAddInstallationForm(Model model) {
        model.addAttribute("installation", new Installation());
        return "add-installation";
    }

    @PostMapping("/add")
    public String addInstallation(@ModelAttribute("installation") Installation installation,
                                  @RequestParam("customerId") Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            installation.setCustomer(customer);
            installationService.save(installation);
        } else {
            return "redirect:/installations/add";
        }
        return "redirect:/installations/";
    }

    @RequestMapping(value = "/delete/{routerNumber}", method = RequestMethod.GET)
    public String deleteInstallation(@PathVariable("routerNumber") String routerNumber) {
        Installation installation = installationService.getInstallationByRouterNumber(Long.valueOf(routerNumber));
        if (installation != null) {
            installationService.deleteByRouterNumber(routerNumber);
        }
        return "redirect:/installations/";
    }
}
