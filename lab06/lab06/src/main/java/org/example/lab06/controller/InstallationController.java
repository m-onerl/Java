package org.example.lab06.controller;

import org.example.lab06.model.Installation;
import org.example.lab06.service.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/installations")
public class InstallationController {

    @Autowired
    private InstallationService installationService;

    @GetMapping
    public List<Installation> getAllInstallations() {
        return installationService.getAllInstallations();
    }


}