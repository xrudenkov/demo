package ru.task.demo.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import ru.task.demo.entity.Company;
import ru.task.demo.model.CompanyModel;
import ru.task.demo.model.LegalFormEnum;
import ru.task.demo.util.DateFormatter;

@Singleton
public class CompanyDaoImpl implements CompanyDao{

    @Inject
    private EntityManager em;

    @Override
    public List<Company> findAll() {
        TypedQuery<Company> query = em.createQuery("SELECT c FROM Company c", Company.class);
        return query.getResultList();
    }

    @Override
    public Company getById(Long id) {
        return em.find(Company.class, id);
    }

    @Override
    public void insert(Company company) {
        em.persist(company);
    }

    @Override
    public void update(CompanyModel companyModel) {
        Company company = getById(companyModel.getId());
        if (company == null) {
            throw new EntityNotFoundException("Not found company by name: " + companyModel.getName());
        }
        company.setName(companyModel.getName());
        company.setLegalForm(LegalFormEnum.fromString(companyModel.getLegalForm()));
        company.setRegistrationDate(DateFormatter.toDate(companyModel.getRegistrationDate()));
    }

    @Override
    public void remove(Long id) {
        em.remove(getById(id));
    }
}
