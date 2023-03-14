package ru.task.demo.service;

import jakarta.ejb.Local;
import ru.task.demo.model.BranchModel;

@Local
public interface BranchService {
    void create(BranchModel branchModel);

    void update(BranchModel branchModel);

    void delete(BranchModel branchModel);
}
