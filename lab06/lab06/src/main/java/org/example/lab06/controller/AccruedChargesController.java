package org.example.lab06.controller;

import org.example.lab06.model.AccruedCharges;
import org.example.lab06.service.AccruedChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/charges")
public class AccruedChargesController {

    @Autowired
    private AccruedChargesService accruedChargesService;

    @GetMapping
    public List<AccruedCharges> getAllAccruedCharges() {
        return accruedChargesService.getAllAccruedCharges();
    }


}
