package ru.task.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.task.demo.exception.ValidationModelException;
import ru.task.demo.model.AddressModel;
import ru.task.demo.model.BranchModel;
import ru.task.demo.model.CompanyModel;

public class ValidatorModel {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    public static void validate(CompanyModel companyModel) throws ValidationModelException {
        String errorMsg = "";
        if (companyModel.getName() == null || companyModel.getName().isBlank()) {
            errorMsg += "Поле Название не заполнено\n";
        }
        if (companyModel.getLegalForm() == null) {
            errorMsg += "Поле Форма не заполнено\n";
        }
        if (companyModel.getRegistrationDate() == null) {
            errorMsg += "Поле Дата регистрации не заполнено\n";
        }
        errorMsg = validateRegistrationDate(companyModel.getRegistrationDate(), errorMsg);
        errorMsg = validateAddress(companyModel.getAddress(), errorMsg);
        if (!errorMsg.isBlank()) {
            throw new ValidationModelException(errorMsg);
        }
    }

    public static void validate(BranchModel branchModel) throws ValidationModelException {
        String errorMsg = "";
        if (branchModel.getName() == null || branchModel.getName().isBlank()) {
            errorMsg += "Поле Название не заполнено\n";
        }
        errorMsg = validateRegistrationDate(branchModel.getRegistrationDate(), errorMsg);
        errorMsg = validateAddress(branchModel.getAddress(), errorMsg);
        if (!errorMsg.isBlank()) {
            throw new ValidationModelException(errorMsg);
        }
    }

    private static String validateAddress(AddressModel addressModel, String errorMsg) {
        if (addressModel.getPostcode() != null && !addressModel.getPostcode().isBlank() && !addressModel.getPostcode().matches("\\d{6}$")) {
            errorMsg += "Поле Индекс должно состоять из 6 цифр\n";
        }
        if (addressModel.getCity() == null || addressModel.getCity().isBlank()) {
            errorMsg += "Поле Город не заполнено\n";
        }
        if (addressModel.getStreet() == null || addressModel.getStreet().isBlank()) {
            errorMsg += "Поле Улица не заполнено\n";
        }
        if (addressModel.getHouse() == null || addressModel.getHouse().isBlank()) {
            errorMsg += "Поле Дом не заполнено\n";
        }
        return errorMsg;
    }

    private static String validateRegistrationDate(String registrationDate, String errorMsg) {
        if (registrationDate == null || registrationDate.isBlank()) {
            errorMsg += "Поле Дата регистрации не заполнено\n";
        } else {
            try {
                Date parse = df.parse(registrationDate);
                if(parse.after(new Date())) {
                    errorMsg += "Указана дата больше текущей";
                }
            } catch (ParseException e) {
                errorMsg += "Формат даты не верный";
            }
        }
        return errorMsg;
    }
}
