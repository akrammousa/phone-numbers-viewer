package com.example.phonenumbersviewer.services;

import com.example.phonenumbersviewer.data.entities.Customer;
import com.example.phonenumbersviewer.data.repositories.CustomerRepository;
import com.example.phonenumbersviewer.data.responses.CustomerResponse;
import com.example.phonenumbersviewer.enums.CountriesCodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerServiceTests {

    @Autowired
    CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testGetAll_returnCustomersResponse(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var firstCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(firstCustomer);

        Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

        final List<CustomerResponse> customersResponse = this.customerService.getAll();

        Assertions.assertEquals(1, customersResponse.size());
        Assertions.assertEquals(1 , customersResponse.get(0).getId());
    }
    @Test
    void testFindByCountryName_validCountryName_returnCustomers(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var firstCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);

        final var secondCustomerId = 2;
        final var secondCustomerPhone = "(251) 966002259";
        final var secondCustomer = this.prepareCustomer(secondCustomerId,secondCustomerPhone);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(firstCustomer);
        customerList.add(secondCustomer);
        Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

        String countryName = CountriesCodes.CAMEROON.name();
        final List<CustomerResponse> customersResponse = this.customerService.findByCountryName(countryName);

        Assertions.assertEquals(1, customersResponse.size());
        Assertions.assertEquals(1 , customersResponse.get(0).getId());
    }

    @Test
    void testFindByCountryName_validCountryName_noCustomerMatch(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var firstCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);

        final var secondCustomerId = 2;
        final var secondCustomerPhone = "(251) 966002259";
        final var secondCustomer = this.prepareCustomer(secondCustomerId,secondCustomerPhone);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(firstCustomer);
        customerList.add(secondCustomer);
        Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

        String countryName = CountriesCodes.MOZAMBIQUE.name();
        final List<CustomerResponse> customersResponse = this.customerService.findByCountryName(countryName);

        Assertions.assertEquals(0, customersResponse.size());
    }

    @Test
    void testFindByCountryCode_validCountryCode_returnCustomers(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var firstCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(firstCustomer);
        String countryCode = CountriesCodes.CAMEROON.getCode();

        Mockito.when(this.customerRepository.findAllByPhoneContains("(" +countryCode + ")")).thenReturn(customerList);

        final List<CustomerResponse> customersResponse = this.customerService.findByCountryCode(countryCode);

        Assertions.assertEquals(1, customersResponse.size());
        Assertions.assertEquals(1 , customersResponse.get(0).getId());

    }

    @Test
    void testGetByStatus_trueStatus_returnValidCustomers(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var validCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);

        final var secondCustomerId = 2;
        final var secondCustomerPhone = "(25551) 966002259545454";
        final var invalidCustomer = this.prepareCustomer(secondCustomerId,secondCustomerPhone);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(validCustomer);
        customerList.add(invalidCustomer);

        Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

        final List<CustomerResponse> customersResponse = this.customerService.getByStatus(true);

        Assertions.assertEquals(1, customersResponse.size());
        Assertions.assertEquals(1 , customersResponse.get(0).getId());

    }

    @Test
    void testGetByStatus_falseStatus_returnInvalidCustomers(){
        final var firstCustomerId = 1;
        final var firstCustomerPhone = "(237) 673122155";
        final var validCustomer = this.prepareCustomer(firstCustomerId,firstCustomerPhone);

        final var secondCustomerId = 2;
        final var secondCustomerPhone = "(25551) 966002259545454";
        final var invalidCustomer = this.prepareCustomer(secondCustomerId,secondCustomerPhone);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(validCustomer);
        customerList.add(invalidCustomer);

        Mockito.when(this.customerRepository.findAll()).thenReturn(customerList);

        final List<CustomerResponse> customersResponse = this.customerService.getByStatus(false);

        Assertions.assertEquals(1, customersResponse.size());
        Assertions.assertEquals(2 , customersResponse.get(0).getId());

    }

    private Customer prepareCustomer(int customerId, String phoneNumber){
        final var customerName = "test_name";
        final var customer = new Customer();
        customer.setId(customerId);
        customer.setName(customerName);
        customer.setPhone(phoneNumber);
        return customer;
    }
}
