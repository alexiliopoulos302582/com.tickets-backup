package com.tickets.service;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class acsCustomerDTO {

    private int id;
    private String acsCustomerId;
    private String acsClientName;
    private String acsAFM;
    private String acsCity;
    private String acsAddress;
    private String acsEmail;
    private String acsPhoneNumber;

    public acsCustomerDTO(String acsCustomerId,
                          String acsClientName,
                          String acsAFM, String acsCity,
                          String acsAddress, String acsEmail,
                          String acsPhoneNumber) {
        this.acsCustomerId = acsCustomerId;
        this.acsClientName = acsClientName;
        this.acsAFM = acsAFM;
        this.acsCity = acsCity;
        this.acsAddress = acsAddress;
        this.acsEmail = acsEmail;
        this.acsPhoneNumber = acsPhoneNumber;
    }
}
