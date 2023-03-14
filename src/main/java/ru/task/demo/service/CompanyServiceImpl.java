package ru.task.demo.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import ru.task.demo.dao.AddressDao;
import ru.task.demo.dao.BranchDao;
import ru.task.demo.dao.CompanyDao;
import ru.task.demo.entity.Company;
import ru.task.demo.mapper.CompanyMapper;
import ru.task.demo.model.CompanyModel;

@Singleton
@Named("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Inject
    private CompanyDao companyDao;

    @Inject
    private BranchDao branchDao;

    @Inject
    private AddressDao addressDao;

    @Inject
    private CompanyMapper companyMapper;

    @Override
    @Transactional
    public void create(CompanyModel companyModel) {
        Company company = companyMapper.toEntity(companyModel);
        addressDao.insert(company.getAddress());
        companyDao.insert(company);
        companyModel.setId(company.getId());
        companyModel.getAddress().setId(company.getAddress().getId());
    }

    @Override
    @Transactional
    public void update(CompanyModel companyModel) {
        companyDao.update(companyModel);
        addressDao.update(companyModel.getAddress());
    }

    @Override
    @Transactional
    public void delete(CompanyModel companyModel) {
        companyDao.remove(companyModel.getId());
        addressDao.remove(companyModel.getAddress().getId());
        companyModel.getBranches().forEach(
                branch -> {
                    branchDao.remove(branch.getId());
                    addressDao.remove(branch.getAddress().getId());
                }
        );
    }

    @Override
    public List<CompanyModel> getAll() {
        return companyMapper.toList(companyDao.findAll());
    }
}
