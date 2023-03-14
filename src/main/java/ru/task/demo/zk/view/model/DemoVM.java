package ru.task.demo.zk.view.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.cdi.DelegatingVariableResolver;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import ru.task.demo.exception.DuplicateNameException;
import ru.task.demo.model.BranchModel;
import ru.task.demo.model.CompanyModel;
import ru.task.demo.model.LegalFormEnum;
import ru.task.demo.service.BranchService;
import ru.task.demo.service.CompanyService;

@VariableResolver(DelegatingVariableResolver.class)
public class DemoVM {
    @WireVariable
    private CompanyService companyService;
    private CompanyModel newCompany;
    private CompanyModel selectedCompany;
    private boolean toggleEdit = false;
    private boolean toggleNew = false;
    private ListModelList<CompanyModel> companies;
    @WireVariable
    private BranchService branchService;
    private BranchModel newBranch;
    private BranchModel selectedBranch;
    private boolean toggleEditBranch = false;
    private boolean toggleNewBranch = false;
    private ListModelList<BranchModel> branches;

    @Init
    public void init() {
        companies = new ListModelList<>(companyService.getAll());
        newCompany = new CompanyModel();
        newBranch = new BranchModel();
    }

    @Command
    @NotifyChange({"selectedCompany", "branches", "toggleEdit", "toggleNew", "toggleEditBranch", "toggleNewBranch", "selectedBranch"})
    public void selectCompany(@BindingParam("id") Integer id, @BindingParam("evt") Event evt) {
        selectedCompany = companies.get(id);
        branches = new ListModelList<>(selectedCompany.getBranches());
        toggleEdit = false;
        toggleNew = false;
        toggleEditBranch = false;
        toggleNewBranch = false;
        selectedBranch = null;
    }

    @Command
    @NotifyChange({"toggleNew", "toggleEdit", "toggleCommon", "toggleNewBranch", "toggleEditBranch"})
    public void addCompany() {
        toggleEdit = false;
        toggleNew = true;
        toggleNewBranch = false;
        toggleEditBranch = false;
    }

    @Command
    @NotifyChange({"toggleEdit", "toggleNew", "toggleCommon", "toggleNewBranch", "toggleEditBranch"})
    public void editCompany() {
        toggleNew = false;
        toggleEdit = true;
        toggleNewBranch = false;
        toggleEditBranch = false;
    }

    @Command
    @NotifyChange({"toggleEdit", "toggleNew", "toggleCommon", "selectedCompany", "newCompany", "companies",
            "selectedBranch", "newBranch", "branches", "toggleEditBranch", "toggleNewBranch"})
    public void saveCompany() {
        if (toggleEdit) {
            checkDuplicateCompanyName(selectedCompany, false);
            companyService.update(selectedCompany);
            toggleEdit = false;
        }
        if (toggleNew) {
            checkDuplicateCompanyName(newCompany, true);
            companyService.create(newCompany);
            companies.add(newCompany);
            toggleNew = false;
            selectedCompany = newCompany;
            newCompany = new CompanyModel();
        }
    }

    @Command
    @NotifyChange({"selectedCompany", "companies", "selectedBranch", "branches"})
    public void deleteCompany() {
        companyService.delete(selectedCompany);
        companies.remove(selectedCompany);
        branches = null;
        selectedCompany = null;
        selectedBranch = null;
    }

    @Command
    @NotifyChange({"toggleEdit", "toggleNew", "toggleCommon"})
    public void cancelCompany() {
        toggleEdit = false;
        toggleNew = false;
    }

    @Command
    @NotifyChange({"selectedBranch", "branches", "toggleEdit", "toggleNew", "toggleEditBranch", "toggleNewBranch"})
    public void selectBranch(@BindingParam("id") Integer id, @BindingParam("evt") Event evt) {
        selectedBranch = branches.get(id);
        toggleEdit = false;
        toggleNew = false;
        toggleEditBranch = false;
        toggleNewBranch = false;
    }

    @Command
    @NotifyChange({"toggleNew", "toggleNew", "toggleCommon", "toggleEditBranch", "toggleNewBranch"})
    public void addBranch() {
        toggleNew = false;
        toggleEdit = false;
        toggleEditBranch = false;
        toggleNewBranch = true;
    }

    @Command
    @NotifyChange({"toggleEdit", "toggleNew", "toggleCommon", "toggleNewBranch", "toggleEditBranch"})
    public void editBranch() {
        toggleNew = false;
        toggleEdit = false;
        toggleNewBranch = false;
        toggleEditBranch = true;
    }

    @Command
    @NotifyChange({"toggleEdit", "toggleNew", "toggleCommon", "selectedBranch", "newCompany", "companies",
            "selectedBranch", "newBranch", "branches", "toggleEditBranch", "toggleNewBranch"
    })
    public void saveBranch() {
        if (toggleEditBranch) {
            checkDuplicateBranchName(selectedBranch, false);
            branchService.update(selectedBranch);
            toggleEditBranch = false;
        }
        if (toggleNewBranch) {
            checkDuplicateBranchName(newBranch, true);
            newBranch.setCompanyId(selectedCompany.getId());
            branchService.create(newBranch);
            branches.add(newBranch);
            toggleNewBranch = false;
            selectedBranch = newBranch;
            newBranch = new BranchModel();
        }
    }

    @Command
    @NotifyChange({"selectedCompany",  "companies", "selectedBranch", "branches"})
    public void deleteBranch() {
        branchService.delete(selectedBranch);
        branches.remove(selectedBranch);
        selectedCompany.getBranches().remove(selectedBranch);
        selectedBranch = null;
    }

    @Command
    @NotifyChange({"toggleEditBranch", "toggleNewBranch", "toggleCommon"})
    public void cancelBranch() {
        toggleEditBranch = false;
        toggleNewBranch = false;
    }

    private void checkDuplicateCompanyName(CompanyModel companyModel, boolean isNew) {
        companies.forEach(company -> {
            if(company.getName().equals(companyModel.getName())) {
                if (isNew) {
                    throw new DuplicateNameException("Компания с таким именем уже существует!");
                } else {
                    if (!company.getId().equals(companyModel.getId())) {
                        throw new DuplicateNameException("Компания с таким именем уже существует!");
                    }
                }
            }
        });
    }

    private void checkDuplicateBranchName(BranchModel branchModel, boolean isNew) {
        branches.forEach(branch -> {
            if(branch.getName().equals(branchModel.getName())) {
                if (isNew) {
                    throw new DuplicateNameException("Филиал с таким именем уже существует!");
                } else {
                    if (!branch.getId().equals(branchModel.getId())) {
                        throw new DuplicateNameException("Филиал с таким именем уже существует!");
                    }
                }
            }
        });
    }

    public List<CompanyModel> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyModel> companies) {
        this.companies = new ListModelList<>(companies);
    }

    public CompanyModel getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(CompanyModel selected) {
        this.selectedCompany = selected;
    }

    public boolean isToggleEdit() {
        return toggleEdit;
    }

    public void setToggleEdit(boolean toggleEdit) {
        this.toggleEdit = toggleEdit;
    }

    public boolean isToggleNew() {
        return toggleNew;
    }

    public void setToggleNew(boolean toggleNew) {
        this.toggleNew = toggleNew;
    }

    public CompanyModel getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(CompanyModel newCompany) {
        this.newCompany = newCompany;
    }

    public ListModel<String> getLegalForms() {
        return new ListModelList<>(Arrays.stream(LegalFormEnum.values()).map(LegalFormEnum::getTitle).collect(Collectors.toList()));
    }

    public boolean getToggleCommon() {
        return !toggleEdit && !toggleNew && !toggleEditBranch && !toggleNewBranch;
    }

    public BranchModel getNewBranch() {
        return newBranch;
    }

    public void setNewBranch(BranchModel newBranch) {
        this.newBranch = newBranch;
    }

    public BranchModel getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(BranchModel selectedBranch) {
        this.selectedBranch = selectedBranch;
    }

    public boolean isToggleEditBranch() {
        return toggleEditBranch;
    }

    public void setToggleEditBranch(boolean toggleEditBranch) {
        this.toggleEditBranch = toggleEditBranch;
    }

    public boolean isToggleNewBranch() {
        return toggleNewBranch;
    }

    public void setToggleNewBranch(boolean toggleNewBranch) {
        this.toggleNewBranch = toggleNewBranch;
    }

    public ListModelList<BranchModel> getBranches() {
        return branches;
    }

    public void setBranches(ListModelList<BranchModel> branches) {
        this.branches = branches;
    }
}
