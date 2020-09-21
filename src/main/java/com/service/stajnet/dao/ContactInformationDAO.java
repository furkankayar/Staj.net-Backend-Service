package com.service.stajnet.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationDAO {
    
    private String fax;

    private String phone;

    private String website;

    private AddressInformationDAO address; 


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AddressInformationDAO {

        private String country;
        
        private String city;

        private String district;

        private String address;

        private String postCode; 
    }
}