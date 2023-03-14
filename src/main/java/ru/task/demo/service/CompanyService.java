package ru.task.demo.service;

import jakarta.ejb.Local;
import java.util.List;
import ru.task.demo.model.CompanyModel;

@Local
public interface CompanyService {
    void create(CompanyModel companyModel);

    void update(CompanyModel companyModel);

    void delete(CompanyModel companyModel);

    List<CompanyModel> getAll();
}
