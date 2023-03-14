package ru.task.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BRANCH")
@Getter
@Setter
public class Branch implements Serializable {

    private static final long serialVersionUID = 8110297955003302415L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name="COMPANY_ID", nullable = false)
    private Company company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return name.equals(branch.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
