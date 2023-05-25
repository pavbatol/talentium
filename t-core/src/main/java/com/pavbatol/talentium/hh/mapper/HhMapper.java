package com.pavbatol.talentium.hh.mapper;

import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.model.Hh;
import com.pavbatol.talentium.management.model.Management;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = Management.class)
public interface HhMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(authUserId)")
    Hh toEntity(HhDtoRequest dto, Long authUserId);

    HhDtoResponse toResponseDto(Hh saved);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Hh updateEntity(HhDtoUpdate dto, @MappingTarget Hh entity);

    List<HhDtoResponse> toDtos(List<Hh> content);
}
