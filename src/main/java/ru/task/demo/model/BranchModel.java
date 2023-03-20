package ru.task.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BranchModel {
    private Long id;
    private String name;
    private String registrationDate;
    private AddressModel address = new AddressModel();
    private Long companyId;
}
