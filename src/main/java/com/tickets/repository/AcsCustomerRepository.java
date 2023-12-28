package com.tickets.repository;

import com.tickets.entity.acsCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcsCustomerRepository extends JpaRepository<acsCustomer,Integer> {





    @Query("SELECT t FROM acsCustomer t WHERE " +
            "t.acsPhoneNumber LIKE %?1% OR " +
            "t.acsCity LIKE %?1% OR " +
            "t.acsClientName LIKE %?1% OR " +
            "t.acsEmail LIKE %?1% OR " +
            "t.acsCustomerId LIKE %?1% OR " +
            "t.acsAFM LIKE %?1% OR " +
            "t.acsAddress LIKE %?1% ")
    List<acsCustomer> searchCustomers(String search);

    acsCustomer findByAcsPhoneNumber(String phoneNumber);

    List<acsCustomer> findAllByacsPhoneNumber(String phoneNumber);


    @Query("SELECT c FROM acsCustomer c WHERE c.acsClientName LIKE %?1%")
    List<acsCustomer> findAllByacsClientName(String name);



}

