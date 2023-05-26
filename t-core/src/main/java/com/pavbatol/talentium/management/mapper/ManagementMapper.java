package com.pavbatol.talentium.management.mapper;

import com.pavbatol.talentium.management.dto.ManagementDtoRequest;
import com.pavbatol.talentium.management.dto.ManagementDtoResponse;
import com.pavbatol.talentium.management.dto.ManagementDtoShort;
import com.pavbatol.talentium.management.dto.ManagementDtoUpdate;
import com.pavbatol.talentium.management.model.Management;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ManagementMapper {

    @Mapping(target = "id", ignore = true)
    Management toEntity(ManagementDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    Management toEntity(ManagementDtoShort dto);

    ManagementDtoResponse toResponseDto(Management entity);

    ManagementDtoShort toShortDto(Management entity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Management updateEntity(ManagementDtoUpdate dto, @MappingTarget Management entity);

    List<ManagementDtoResponse> toDtos(List<Management> entities);
}
