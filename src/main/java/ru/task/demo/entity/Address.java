package ru.task.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS")
@Getter
@Setter
public class Address implements Serializable {

    private static final long serialVersionUID = 7770297955003302999L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "STREET", nullable = false, length = 100)
    private String street;

    @Column(name = "HOUSE", nullable = false, length = 50)
    private String house;

    @Column(name = "APARTMENT", length = 50)
    private String apartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(postcode, address.postcode) && city.equals(address.city) && street.equals(address.street) && house.equals(address.house) && Objects.equals(apartment, address.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postcode, city, street, house, apartment);
    }
}
