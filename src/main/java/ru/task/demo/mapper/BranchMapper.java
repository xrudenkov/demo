package ru.task.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.task.demo.entity.Branch;
import ru.task.demo.model.BranchModel;
import ru.task.demo.util.DateFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
        uses = { AddressMapper.class, DateFormatter.class },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchMapper {

    @Mapping(expression = "java(entity.getCompany().getId())", target = "companyId")
    BranchModel toModel(Branch entity);

    Branch toEntity(BranchModel model);
}
