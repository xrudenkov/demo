package ru.task.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {
    private Long id;
    private String postcode;
    private String city;
    private String street;
    private String house;
    private String apartment;

    public String getFullAddress() {
        String fullAddress = "г. " + city + ", ул. " + street + ", д. " + house;
        if (postcode != null) {
            fullAddress = postcode + ", " + fullAddress;
        }
        if (apartment != null) {
            fullAddress += "/" +apartment;
        }
        return fullAddress;
    }
}
