package com.example.phonenumbersviewer.services;

import com.example.phonenumbersviewer.data.entities.Country;
import com.example.phonenumbersviewer.data.entities.Customer;
import com.example.phonenumbersviewer.data.repositories.CustomerRepository;
import com.example.phonenumbersviewer.data.responses.CustomerResponse;
import com.example.phonenumbersviewer.enums.CountriesCodes;
import com.example.phonenumbersviewer.enums.CountriesNumberRegexes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public List<CustomerResponse> getAll(){
        var customers = this.customerRepository.findAll();
        return mapAndCategorizeCustomers(customers);
    }

    public List<CustomerResponse> findByCountryName(String name){
        var allCustomers = this.customerRepository.findAll();
        allCustomers = allCustomers.stream().filter(customer -> Pattern.matches(CountriesNumberRegexes.valueOf(name).getRegex() , customer.getPhone())).collect(Collectors.toList());
        return mapAndCategorizeCustomers(allCustomers);
    }

    public List<CustomerResponse> findByCountryCode(String code) {
        code = "(" + code + ")";
        var customers = this.customerRepository.findAllByPhoneContains(code);
        return mapAndCategorizeCustomers(customers);
    }

    public List<CustomerResponse> getByStatus(boolean valid){
        var allCustomers = this.customerRepository.findAll();
        allCustomers =  valid ? allCustomers.stream().parallel().filter(customer -> {
            for (CountriesNumberRegexes countryRegex : CountriesNumberRegexes.values()) {
                if (Pattern.matches(countryRegex.getRegex() , customer.getPhone())){
                    return true;
                }
            }
            return false; }).collect(Collectors.toList())
                : allCustomers.stream().parallel().filter(customer -> {
            for (CountriesNumberRegexes countryRegex : CountriesNumberRegexes.values()) {
                if (Pattern.matches(countryRegex.getRegex() , customer.getPhone())){
                    return false;
                }
            }
            return true; }).collect(Collectors.toList());
        return mapAndCategorizeCustomers(allCustomers);
    }


    private List<CustomerResponse> mapAndCategorizeCustomers(List<Customer> customers) {
        List<CustomerResponse> customerResponses = new ArrayList<>();
        customers.forEach(customer ->  {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setName(customer.getName());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setCountry(this.getCountry(customer.getPhone()));
            customerResponse.setValid(customerResponse.getCountry() != null);
            customerResponses.add(customerResponse);
        });
        return customerResponses;
    }

    private Country getCountry(String phone) {
        for (CountriesNumberRegexes countryRegex : CountriesNumberRegexes.values()) {
            if (Pattern.matches(countryRegex.getRegex() , phone)){
                return new Country(countryRegex.name().toLowerCase() , CountriesCodes.valueOf(countryRegex.name()).getCode());
            }
        }
        return null;
    }
}
