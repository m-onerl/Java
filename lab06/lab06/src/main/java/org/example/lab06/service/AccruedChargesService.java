package org.example.lab06.service;

import org.example.lab06.model.AccruedCharges;
import org.example.lab06.repository.AccruedChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccruedChargesService {

    @Autowired
    private AccruedChargesRepository accruedChargesRepository;

    public List<AccruedCharges> getAllAccruedCharges() {
        return accruedChargesRepository.findAll();
    }

    public void save(AccruedCharges accruedCharges) {
        accruedChargesRepository.save(accruedCharges);


}

}
