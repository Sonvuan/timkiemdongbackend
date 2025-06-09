package com.backend.timkiemdong.mapper;

import com.backend.timkiemdong.dto.AcountRequest;
import com.backend.timkiemdong.dto.AcountResponse;
import com.backend.timkiemdong.entity.Acount;
import com.backend.timkiemdong.entity.Permission;
import com.backend.timkiemdong.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface AcountMapper {

    Acount toAcount(AcountRequest acountRequest);

    @Mapping(target = "roles", expression = "java(mapRoles(acount))")
    @Mapping(target = "permissions", expression = "java(mapPermissions(acount))")
    AcountResponse toAcountResponse(Acount acount);

    default Set<String> mapRoles(Acount acount) {
        return acount.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<String> mapPermissions(Acount acount) {
        return acount.getRoles().stream()
                .flatMap(role -> role.getPermissions() != null ? role.getPermissions().stream() : Stream.empty())
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
}
