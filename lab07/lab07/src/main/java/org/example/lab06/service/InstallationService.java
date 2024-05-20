package org.example.lab06.service;

import org.example.lab06.model.Installation;
import org.example.lab06.repository.InstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallationService {

    @Autowired
    private InstallationRepository installationRepository;

    public List<Installation> getAllInstallations() {
        return installationRepository.findAll();
    }

    public void save(Installation installation) {
        installationRepository.save(installation);
    }

    public Installation getInstallationByRouterNumber(Long routerNumber) {
        return installationRepository.findByRouterNumber(routerNumber);
    }

    public Installation getInstallationById(Long installationId) {
        return installationRepository.findById(installationId).orElse(null);
    }

    public void deleteByRouterNumber(String routerNumber) {
        Installation installation = installationRepository.findByRouterNumber(Long.valueOf(routerNumber));
        if (installation != null) {
            installationRepository.delete(installation);
        }
    }
}
