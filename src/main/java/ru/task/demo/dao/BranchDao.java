package ru.task.demo.dao;

import jakarta.ejb.Local;
import ru.task.demo.entity.Branch;
import ru.task.demo.model.BranchModel;

@Local
public interface BranchDao {
    Branch getById(Long id);
    void insert(Branch branch);
    void update(BranchModel branchModel);
    void remove(Long id);
}
