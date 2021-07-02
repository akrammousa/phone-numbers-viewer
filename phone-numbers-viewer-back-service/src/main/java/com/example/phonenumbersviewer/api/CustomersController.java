package com.example.phonenumbersviewer.api;

import com.example.phonenumbersviewer.data.entities.Customer;
import com.example.phonenumbersviewer.data.responses.CustomerResponse;
import com.example.phonenumbersviewer.enums.CountriesCodes;
import com.example.phonenumbersviewer.enums.CountriesNumberRegexes;
import com.example.phonenumbersviewer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    @Autowired
    CustomerService customerService;

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<List<CustomerResponse>> getAll(){
        var customers = this.customerService.getAll();
        return ResponseEntity.ok(customers);
    }

    @CrossOrigin
    @GetMapping("/country-name")
    public ResponseEntity<List<CustomerResponse>> findByCountryName(@RequestParam("name") String name){
        name = name.toUpperCase();
        if (!CountriesNumberRegexes.isExist(name)) return ResponseEntity.notFound().build();
        var customers = this.customerService.findByCountryName(name);
        return ResponseEntity.ok(customers);
    }

    @CrossOrigin
    @GetMapping("/country-code")
    public ResponseEntity<List<CustomerResponse>> findByCountryCode(@RequestParam("code") String code){
        if (!CountriesCodes.isValidCode(code)) return ResponseEntity.notFound().build();
        var customers = this.customerService.findByCountryCode(code);
        return ResponseEntity.ok(customers);
    }

    @CrossOrigin
    @GetMapping("/status")
    public ResponseEntity<List<CustomerResponse>> findByStatus(@RequestParam("status") boolean status){
        var customers = this.customerService.getByStatus(status);
        return ResponseEntity.ok(customers);
    }
}
