package org.example.lab06.repository;

import org.example.lab06.model.AccruedCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccruedChargesRepository extends JpaRepository<AccruedCharges, Long> {
}
