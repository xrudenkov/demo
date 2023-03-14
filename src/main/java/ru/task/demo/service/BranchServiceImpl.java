package ru.task.demo.service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import ru.task.demo.dao.AddressDao;
import ru.task.demo.dao.BranchDao;
import ru.task.demo.dao.CompanyDao;
import ru.task.demo.entity.Branch;
import ru.task.demo.entity.Company;
import ru.task.demo.mapper.BranchMapper;
import ru.task.demo.model.BranchModel;

@Singleton
@Named("branchService")
public class BranchServiceImpl implements BranchService {

    @Inject
    private BranchDao branchDao;

    @Inject
    private AddressDao addressDao;

    @Inject
    private CompanyDao companyDao;

    @Inject
    private BranchMapper branchMapper;

    @Override
    @Transactional
    public void create(BranchModel branchModel) {
        Branch branch = branchMapper.toEntity(branchModel);
        addressDao.insert(branch.getAddress());
        Company company = companyDao.getById(branchModel.getCompanyId());
        branch.setCompany(company);
        branchDao.insert(branch);
        branchModel.setId(branch.getId());
        branchModel.getAddress().setId(branch.getAddress().getId());
    }

    @Override
    @Transactional
    public void update(BranchModel branchModel) {
        branchDao.update(branchModel);
        addressDao.update(branchModel.getAddress());
    }

    @Override
    @Transactional
    public void delete(BranchModel branchModel) {
        branchDao.remove(branchModel.getId());
        addressDao.remove(branchModel.getAddress().getId());
    }
}
