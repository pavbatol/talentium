package com.pavbatol.talentium.mentor.mapper;

import com.pavbatol.talentium.hh.mapper.HhMapper;
import com.pavbatol.talentium.management.mapper.ManagementMapper;
import com.pavbatol.talentium.mentor.dto.MentorDtoRequest;
import com.pavbatol.talentium.mentor.dto.MentorDtoResponse;
import com.pavbatol.talentium.mentor.dto.MentorDtoUpdate;
import com.pavbatol.talentium.mentor.model.Mentor;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {HhMapper.class, ManagementMapper.class})
public interface MentorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId",  expression = "java(authUserId)")
    Mentor toEntity(MentorDtoRequest dto, Long authUserId);

    MentorDtoResponse toResponseDto(Mentor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Mentor updateEntity(MentorDtoUpdate dto, @MappingTarget Mentor entity);

    List<MentorDtoResponse> toDtos(List<Mentor> entities);
}
