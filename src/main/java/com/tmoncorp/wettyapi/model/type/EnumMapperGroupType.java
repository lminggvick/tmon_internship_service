package com.tmoncorp.wettyapi.model.type;

import java.util.List;

public interface EnumMapperGroupType<T> extends EnumMapperType {

    List<T> getCategories();

    default boolean hasTypeCode(List<T> typeList, String code) {
        return typeList.stream().anyMatch(type -> type.equals(code));
    }

}
