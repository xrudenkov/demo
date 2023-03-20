package ru.task.demo.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyModel {
    private Long id;
    private String name;
    private String legalForm;
    private String registrationDate;
    private AddressModel address = new AddressModel();
    private Set<BranchModel> branches = new HashSet<>();

}
