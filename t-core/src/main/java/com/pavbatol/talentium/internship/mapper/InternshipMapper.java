package com.pavbatol.talentium.internship.mapper;

import com.pavbatol.talentium.hh.mapper.HhMapper;
import com.pavbatol.talentium.internship.dto.*;
import com.pavbatol.talentium.internship.model.Internship;
import com.pavbatol.talentium.management.mapper.ManagementMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {HhMapper.class, ManagementMapper.class})
public interface InternshipMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    Internship toEntity(InternshipDtoRequest dto);

    @Mapping(target = "id", ignore = true)
    Internship toEntity(InternshipDtoShort dto);

    InternshipDtoResponse toResponseDto(Internship entity);

    InternshipDtoResponseShort toResponseDtoShort(Internship entity);

    InternshipDtoShort toDtoShort(Internship entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Internship updateEntity(InternshipDtoUpdate dto, @MappingTarget Internship entity);

    List<InternshipDtoResponse> toDtos(List<Internship> entities);
}
