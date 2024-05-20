package org.example.lab06.controller;

import org.example.lab06.model.Customer;
import org.example.lab06.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        var customerToSave = customerService.save(customer);
        return ResponseEntity.ok(customerToSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer){
        var foundCustomer = customerService.getCustomerById(id);
        if(updatedCustomer.getFirstName() != null){
            foundCustomer.setFirstName(updatedCustomer.getFirstName());
        }
        if(updatedCustomer.getLastName() != null){
            foundCustomer.setLastName(updatedCustomer.getLastName());
        }
        return ResponseEntity.ok(customerService.save(foundCustomer));
    }


}
