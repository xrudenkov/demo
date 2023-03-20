package ru.task.demo.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
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

    @Inject
    private Logger logger;

    @Override
    @Transactional
    public void create(CompanyModel companyModel) {
        Company company = companyMapper.toEntity(companyModel);
        addressDao.insert(company.getAddress());
        companyDao.insert(company);
        companyModel.setId(company.getId());
        companyModel.getAddress().setId(company.getAddress().getId());
        logger.info("Created company: " + companyModel);
    }

    @Override
    @Transactional
    public void update(CompanyModel companyModel) {
        companyDao.update(companyModel);
        addressDao.update(companyModel.getAddress());
        logger.info("Updated company: " + companyModel);
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
        logger.info("Removed company: " + companyModel);
    }

    @Override
    public List<CompanyModel> getAll() {
        List<CompanyModel> companies = companyMapper.toList(companyDao.findAll());
        logger.info("Received companies in the amount " + companies.size());
        return companies;
    }
}
