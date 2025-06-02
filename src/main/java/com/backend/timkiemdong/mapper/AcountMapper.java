package com.backend.timkiemdong.mapper;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.entity.Acount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcountMapper {
    Acount toAcount(AcountRequest acountRequest);
    AcountResponse toAcountResponse(Acount acount);
}
