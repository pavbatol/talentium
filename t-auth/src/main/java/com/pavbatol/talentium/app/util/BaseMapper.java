package com.pavbatol.talentium.app.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface BaseMapper<E, D> {
    D toDto(E entity);

    E toEntity(D dto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E updateEntity(D dto, @MappingTarget E targetEntity);

    List<D> toDtos(List<E> entities);
}
