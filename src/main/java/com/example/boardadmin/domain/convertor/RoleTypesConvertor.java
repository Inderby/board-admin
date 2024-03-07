package com.example.boardadmin.domain.convertor;

import com.example.boardadmin.domain.constant.RoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter //원래는 autoApply = true 로 자동 적용이 가능하지만 Collection 타입은 인식하지 못하는 오류가 존재한다.
public class RoleTypesConvertor implements AttributeConverter<Set<RoleType>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<RoleType> attribute) {
        return attribute.stream().map(RoleType::name).sorted().collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<RoleType> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).map(RoleType::valueOf).collect(Collectors.toSet());
    }
}
