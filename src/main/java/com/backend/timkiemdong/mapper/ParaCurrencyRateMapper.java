package com.backend.timkiemdong.mapper;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ParaCurrencyRateMapper {


    ParaCurrencyRateDto toDTO(ParaCurrencyRate entity);

    ParaCurrencyRate toEntity(ParaCurrencyRateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ParaCurrencyRateDto dto, @MappingTarget ParaCurrencyRate entity);

}
