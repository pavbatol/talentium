package com.pavbatol.talentium.hh.mapper;

import com.pavbatol.talentium.hh.dto.HhDtoRequest;
import com.pavbatol.talentium.hh.dto.HhDtoResponse;
import com.pavbatol.talentium.hh.dto.HhDtoUpdate;
import com.pavbatol.talentium.hh.model.Hh;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface HhMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    Hh toEntity(HhDtoRequest dto, Long userId);

    HhDtoResponse toResponseDto(Hh saved);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Hh updateEntity(HhDtoUpdate dto, @MappingTarget Hh entity);
}
