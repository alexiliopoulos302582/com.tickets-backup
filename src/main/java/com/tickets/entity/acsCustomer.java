package com.tickets.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "acsCustomer")
public class acsCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
        private String acsCustomerId;
    @Column(name = "acs_client_name")
    private String acsClientName;
        private String  acsAFM;
        private String acsCity;
         private String acsAddress;
         private String acsEmail;
         private String acsPhoneNumber;
    public acsCustomer(String acsCustomerId,
                       String acsClientName, String acsAFM, String acsCity, String acsAddress,
                       String acsEmail,String acsPhoneNumber) {
        this.acsCustomerId = acsCustomerId;
        this.acsClientName = acsClientName;
        this.acsAFM = acsAFM;
        this.acsCity = acsCity;
        this.acsAddress = acsAddress;
        this.acsEmail = acsEmail;
        this.acsPhoneNumber = acsPhoneNumber;
    }

    public String getAcsPhoneNumber() {
        return acsPhoneNumber;
    }

    public void setAcsPhoneNumber(String acsPhoneNumber) {
        this.acsPhoneNumber = acsPhoneNumber;
    }

    public acsCustomer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcsCustomerId() {
        return acsCustomerId;
    }

    public void setAcsCustomerId(String acsCustomerId) {
        this.acsCustomerId = acsCustomerId;
    }

    public String getAcsClientName() {
        return acsClientName;
    }

    public void setAcsClientName(String acsClientName) {
        this.acsClientName = acsClientName;
    }

    public String getAcsAFM() {
        return acsAFM;
    }

    public void setAcsAFM(String acsAFM) {
        this.acsAFM = acsAFM;
    }

    public String getAcsCity() {
        return acsCity;
    }

    public void setAcsCity(String acsCity) {
        this.acsCity = acsCity;
    }

    public String getAcsAddress() {
        return acsAddress;
    }

    public void setAcsAddress(String acsAddress) {
        this.acsAddress = acsAddress;
    }

    public String getAcsEmail() {
        return acsEmail;
    }

    public void setAcsEmail(String acsEmail) {
        this.acsEmail = acsEmail;
    }
}
