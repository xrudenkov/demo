package ru.task.demo.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.task.demo.entity.Company;
import ru.task.demo.model.CompanyModel;
import ru.task.demo.model.LegalFormEnum;
import ru.task.demo.util.DateFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
        uses = { AddressMapper.class, BranchMapper.class, DateFormatter.class },
        imports = LegalFormEnum.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {

    @Mapping(expression = "java(entity.getLegalForm().getTitle())", target = "legalForm")
    CompanyModel toModel(Company entity);

    @Mapping(expression = "java(LegalFormEnum.fromString(model.getLegalForm()))", target = "legalForm")
    Company toEntity(CompanyModel model);

    List<CompanyModel> toList(List<Company> companies);
}
