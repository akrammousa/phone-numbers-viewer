package com.example.phonenumbersviewer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.phonenumbersviewer.data.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findAllByPhoneContains(String match);

    List<Customer> findFirstByPhoneContains(String phone);
}
