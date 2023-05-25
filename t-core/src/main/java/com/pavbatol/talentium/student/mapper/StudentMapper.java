package com.pavbatol.talentium.student.mapper;

import com.pavbatol.talentium.management.mapper.ManagementMapper;
import com.pavbatol.talentium.mentor.mapper.MentorMapper;
import com.pavbatol.talentium.student.dto.StudentDtoRequest;
import com.pavbatol.talentium.student.dto.StudentDtoResponse;
import com.pavbatol.talentium.student.dto.StudentDtoResponseShort;
import com.pavbatol.talentium.student.dto.StudentDtoUpdate;
import com.pavbatol.talentium.student.model.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {MentorMapper.class, ManagementMapper.class})
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId",  expression = "java(authUserId)")
    Student toEntity(StudentDtoRequest dto, Long authUserId);

    StudentDtoResponse toResponseDto(Student entity);

    StudentDtoResponseShort toResponseDtoShort(Student entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student updateEntity(StudentDtoUpdate dto, @MappingTarget Student entity);

    List<StudentDtoResponse> toDtos(List<Student> entities);
}
