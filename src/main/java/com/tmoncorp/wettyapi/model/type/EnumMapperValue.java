package com.tmoncorp.wettyapi.model.type;

import com.tmoncorp.core.model.BaseModelSupport;

import java.util.ArrayList;
import java.util.List;

public class EnumMapperValue<T> extends BaseModelSupport {
    private String code;
    private String title;
    private List<T> categories = new ArrayList<>();

    public EnumMapperValue() {
    }

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.title = enumMapperType.getTitle();
    }

    public EnumMapperValue(EnumMapperGroupType<T> enumMapperGroupType) {
        this.code = enumMapperGroupType.getCode();
        this.title = enumMapperGroupType.getTitle();
        this.categories = enumMapperGroupType.getCategories();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        if (categories.isEmpty()) {
            return "{" +
                    "code='" + code + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        } else {
            return "{" +
                    "code='" + code + '\'' +
                    ", title='" + title + '\'' +
                    ", categories=" + categories +
                    '}';
        }
    }
}
