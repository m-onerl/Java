package org.example.lab06.service;

import org.example.lab06.model.PriceList;
import org.example.lab06.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {

    private final PriceListRepository priceListRepository;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    public List<PriceList> getAllPriceLists() {
        return priceListRepository.findAll();
    }

    public void addPriceList(String serviceType, double price) {
        PriceList priceList = new PriceList();
        priceList.setServiceType(serviceType);
        priceList.setPrice(price);
        priceListRepository.save(priceList);
    }

    public PriceList save(PriceList request) {
        return priceListRepository.save(request);
    }

    public List<PriceList> findAll() {
        return getAllPriceLists();
    }
}
