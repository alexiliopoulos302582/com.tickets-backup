package com.tickets.service;

import com.tickets.entity.acsCustomer;
import com.tickets.repository.AcsCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcsCustomerService {

    private final AcsCustomerRepository acsCustomerRepository;

    public AcsCustomerService(AcsCustomerRepository acsCustomerRepository) {
        this.acsCustomerRepository = acsCustomerRepository;
    }

    public void registerAcsCustomer(acsCustomer acsCustomer) {
        acsCustomerRepository.save(acsCustomer);
    }

    public List<acsCustomer> getAllCustomers() {
        List<acsCustomer> acsCustomers = acsCustomerRepository.findAll();
        return acsCustomers;
    }

    public List<acsCustomer> findCustomers(String search) {
        if (search != null) {

        return acsCustomerRepository.searchCustomers(search);
        }
        else return acsCustomerRepository.findAll();
    }

    public acsCustomer findByPhoneNumber(String phoneNumber) {
        return acsCustomerRepository.findByAcsPhoneNumber(phoneNumber);
    }

    List<acsCustomer> findAllByPhoneNumber(String phoneNumber) {
        List<acsCustomer> acsCustomers = acsCustomerRepository.findAllByacsPhoneNumber(phoneNumber);
        return acsCustomers;
    }

    public List<acsCustomer> findallcustomers() {
        return acsCustomerRepository.findAll();
    }

}
