package ru.task.demo.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import ru.task.demo.entity.Address;
import ru.task.demo.model.AddressModel;

@Singleton
public class AddressDaoImpl implements AddressDao {

    @Inject
    private EntityManager entityManager;

    @Override
    public Address getById(Long id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public void insert(Address address) {
        entityManager.persist(address);
    }

    @Override
    public void update(AddressModel addressModel) {
        Address address = getById(addressModel.getId());
        if (address == null) {
            throw new EntityNotFoundException("Not found address for company");
        }
        address.setPostcode(addressModel.getPostcode());
        address.setCity(addressModel.getCity());
        address.setStreet(addressModel.getStreet());
        address.setHouse(addressModel.getHouse());
        address.setApartment(addressModel.getApartment());
        entityManager.flush();
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(getById(id));
        entityManager.flush();
    }
}