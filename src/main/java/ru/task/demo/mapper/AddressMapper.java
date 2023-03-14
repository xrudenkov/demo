package ru.task.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.task.demo.entity.Address;
import ru.task.demo.model.AddressModel;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressModel toModel(Address entity);

    Address toEntity(AddressModel model);
}
