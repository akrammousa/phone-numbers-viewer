package com.example.phonenumbersviewer.api;

import com.example.phonenumbersviewer.data.entities.Country;
import com.example.phonenumbersviewer.data.entities.Customer;
import com.example.phonenumbersviewer.data.responses.CustomerResponse;
import com.example.phonenumbersviewer.enums.CountriesCodes;
import com.example.phonenumbersviewer.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomersController.class)
public class CustomerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testGetAll_returnsCustomer() throws Exception {
        int customerId = 1;
        List<CustomerResponse> customerList = new ArrayList<>();
        customerList.add(this.prepareCustomer(customerId , "testPhone"));
        Mockito.when(customerService.getAll()).thenReturn(customerList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertFalse(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testFindByCountryName_validName_returnsCustomer() throws Exception {
        String countryName = CountriesCodes.MOZAMBIQUE.name();
        int customerId = 1;
        List<CustomerResponse> customerList = new ArrayList<>();
        customerList.add(this.prepareCustomer(customerId , "testPhone"));
        Mockito.when(customerService.findByCountryName(countryName)).thenReturn(customerList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/country-name")
                .queryParam("name" , countryName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertFalse(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testFindByCountryName_InvalidName_returnsEmptyResponse() throws Exception {
        String countryName = "invalid country";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/country-name")
                .queryParam("name" , countryName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testFindByCountryCode_validCode_returnsCustomer() throws Exception {
        String countryCode = CountriesCodes.MOZAMBIQUE.getCode();
        int customerId = 1;
        List<CustomerResponse> customerList = new ArrayList<>();
        customerList.add(this.prepareCustomer(customerId , "testPhone"));
        Mockito.when(customerService.findByCountryCode(countryCode)).thenReturn(customerList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/country-code")
                .queryParam("code" , countryCode)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertFalse(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testFindByCountryCode_InvalidCode_returnsEmptyResponse() throws Exception {
        String countryCode = "invalid code";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/country-code")
                .queryParam("code" , countryCode)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testGetByStatus_validStatus_returnsCustomer() throws Exception {
        int customerId = 1;
        List<CustomerResponse> customerList = new ArrayList<>();
        customerList.add(this.prepareCustomer(customerId , "validPhone"));
        Mockito.when(customerService.getByStatus(true)).thenReturn(customerList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/status")
                .queryParam("status" , String.valueOf(true))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertFalse(mvcResult.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void testGetByStatus_InvalidStatus_returnsCustomer() throws Exception {
        int customerId = 1;
        List<CustomerResponse> customerList = new ArrayList<>();
        customerList.add(this.prepareCustomer(customerId , "invalidPhone"));
        Mockito.when(customerService.getByStatus(false)).thenReturn(customerList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/status")
                .queryParam("status" , String.valueOf(false))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertFalse(mvcResult.getResponse().getContentAsString().isEmpty());
    }


    private CustomerResponse prepareCustomer(int customerId, String phoneNumber){
        final var customerName = "test_name";
        final var customer = new CustomerResponse();
        Country country = new Country(CountriesCodes.MOZAMBIQUE.name() , CountriesCodes.MOZAMBIQUE.getCode());
        customer.setId(customerId);
        customer.setName(customerName);
        customer.setPhone(phoneNumber);
        customer.setCountry(country);
        customer.setValid(true);
        return customer;
    }
}
