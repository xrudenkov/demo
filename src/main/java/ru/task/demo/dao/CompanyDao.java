package ru.task.demo.dao;

import jakarta.ejb.Local;
import java.util.List;
import ru.task.demo.entity.Company;
import ru.task.demo.model.CompanyModel;

@Local
public interface CompanyDao {

    List<Company> findAll();

    Company getById(Long id);

    void insert(Company company);

    void update(CompanyModel model);

    void remove(Long id);
}
