package ru.task.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.task.demo.model.LegalFormEnum;

@Entity
@Table(name = "COMPANY")
@Getter
@Setter
public class Company implements Serializable {

    private static final long serialVersionUID = 7440297955003302414L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "LEGAL_FORM")
    @Enumerated(EnumType.ORDINAL)
    private LegalFormEnum legalForm;

    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Branch> branches;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
