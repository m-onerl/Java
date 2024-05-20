package org.example.lab06.controller;

import org.example.lab06.model.AccruedCharges;
import org.example.lab06.model.Installation;
import org.example.lab06.service.AccruedChargesService;
import org.example.lab06.service.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccruedChargesController {

    @Autowired
    private AccruedChargesService accruedChargesService;

    @Autowired
    private InstallationService installationService;

    @GetMapping("/charges")
    public String getAllAccruedCharges(Model model) {
        List<AccruedCharges> accruedCharges = accruedChargesService.getAllAccruedCharges();
        model.addAttribute("accruedCharges", accruedCharges);
        return "accruedcharges-list";
    }

    @GetMapping("/charges/add")
    public String showAddAccruedChargeForm(Model model) {
        model.addAttribute("accruedCharge", new AccruedCharges());
        return "add-accruedcharge";
    }

    @PostMapping("/charges/add")
    public String addAccruedCharge(@ModelAttribute("accruedCharge") AccruedCharges accruedCharge) {

        Long routerNumberLong = accruedCharge.getInstallationRouterNumber();
        Installation installation = installationService.getInstallationByRouterNumber(routerNumberLong);
        accruedCharge.setInstallation(installation);
        accruedChargesService.save(accruedCharge);
        return "redirect:/charges";
    }
}
