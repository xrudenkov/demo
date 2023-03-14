package ru.task.demo.dao;

import jakarta.ejb.Local;
import ru.task.demo.entity.Address;
import ru.task.demo.model.AddressModel;

@Local
public interface AddressDao {
    Address getById(Long id);
    void insert(Address address);
    void update(AddressModel addressModel);
    void remove(Long id);
}
