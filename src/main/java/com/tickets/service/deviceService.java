package com.tickets.service;

import com.tickets.entity.device;
import com.tickets.repository.deviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class deviceService {
    @Autowired
    private deviceRepository deviceRepository;

    @Autowired
    public deviceService(com.tickets.repository.deviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public String getDeviceModelBySerialNumber(String serialNumber) {
        Optional<device> deviceOptional = deviceRepository.findBySerialNumber(serialNumber);
        if (deviceOptional.isPresent()) {
            device device = deviceOptional.get();
            return device.getDeviceModel(); // Return the fetched device model
        } else {
            return null; // Or handle the case where the device is not found
        }
    }
}