package org.example.lab06.repository;

import org.example.lab06.model.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    Installation findByRouterNumber(Long routerNumber);

}
