package com.pavbatol.talentium.curator.mapper;

import com.pavbatol.talentium.curator.dto.CuratorDtoRequest;
import com.pavbatol.talentium.curator.dto.CuratorDtoResponse;
import com.pavbatol.talentium.curator.dto.CuratorDtoShort;
import com.pavbatol.talentium.curator.dto.CuratorDtoUpdate;
import com.pavbatol.talentium.curator.model.Curator;
import com.pavbatol.talentium.hh.mapper.HhMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = HhMapper.class)
public interface CuratorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(authUserId)")
    Curator toEntity(CuratorDtoRequest dto, Long authUserId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(authUserId)")
    Curator toEntity(CuratorDtoShort dto, Long authUserId);

    CuratorDtoShort toDtoShort(Curator entity);

    CuratorDtoResponse toResponseDto(Curator entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Curator updateEntity(CuratorDtoUpdate dto, @MappingTarget Curator entity);

    List<CuratorDtoResponse> toDtos(List<Curator> entities);
}
