package ru.task.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressModel {
    private Long id;
    private String postcode;
    private String city;
    private String street;
    private String house;
    private String apartment;

    public String getFullAddress() {
        String fullAddress = "г. " + city + ", ул. " + street + ", д. " + house;
        if (postcode != null && !postcode.isBlank()) {
            fullAddress = postcode + ", " + fullAddress;
        }
        if (apartment != null && !apartment.isBlank()) {
            fullAddress += "/" +apartment;
        }
        return fullAddress;
    }
}
