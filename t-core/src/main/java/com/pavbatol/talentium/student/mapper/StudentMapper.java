package com.pavbatol.talentium.student.mapper;

import com.pavbatol.talentium.student.dto.StudentDtoRequest;
import com.pavbatol.talentium.student.dto.StudentDtoResponse;
import com.pavbatol.talentium.student.dto.StudentDtoResponseShort;
import com.pavbatol.talentium.student.dto.StudentDtoUpdate;
import com.pavbatol.talentium.student.model.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(authUserId)")
    @Mapping(target = "management.id", source = "dto.management.id")
    Student toEntity(StudentDtoRequest dto, Long authUserId);

    StudentDtoResponse toResponseDto(Student entity);

    StudentDtoResponseShort toResponseDtoShort(Student entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "registeredOn", ignore = true)
    @Mapping(target = "management", expression = "java(new Management().setId(dto.getManagement().getId()))")
    @Mapping(target = "mentor", expression = "java(new Mentor().setId(dto.getMentor().getId()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student updateEntity(StudentDtoUpdate dto, @MappingTarget Student entity);

    List<StudentDtoResponse> toDtos(List<Student> entities);
}
