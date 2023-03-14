package ru.task.demo.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import ru.task.demo.entity.Branch;
import ru.task.demo.model.BranchModel;
import ru.task.demo.util.DateFormatter;

@Singleton
public class BranchDaoImpl implements BranchDao {

    @Inject
    private EntityManager em;

    @Override
    public Branch getById(Long id) {
        return em.find(Branch.class, id);
    }

    @Override
    public Optional<Branch> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void insert(Branch branch) {
        em.persist(branch);
    }

    @Override
    public void update(BranchModel branchModel) {
        Branch branch = getById(branchModel.getId());
        if (branch == null) {
            throw new EntityNotFoundException("Not found branch for company");
        }
        branch.setName(branchModel.getName());
        branch.setRegistrationDate(DateFormatter.toDate(branchModel.getRegistrationDate()));
        em.flush();
    }

    @Override
    public void remove(Long id) {
        em.remove(getById(id));
        em.flush();
    }
}
