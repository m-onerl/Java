package org.example.lab06.controller;

import org.example.lab06.model.PriceList;
import org.example.lab06.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceListController {
    private final PriceListService service;
    @Autowired
    public PriceListController(PriceListService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PriceList>> getAllItemsInPriceList() {
        List<PriceList> allItems = service.findAll();
        return ResponseEntity.ok(allItems);
    }

    @PostMapping("/list")
    public ResponseEntity<PriceList> createItemInPriceList(@RequestBody PriceList request) {
        PriceList savedItem = service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }
}
