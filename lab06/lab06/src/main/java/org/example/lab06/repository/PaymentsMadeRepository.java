package org.example.lab06.repository;

import org.example.lab06.model.PaymentsMade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsMadeRepository extends JpaRepository<PaymentsMade, Long> {
    PaymentsMade findByInstallationNumber(Long installationNumber);
}
