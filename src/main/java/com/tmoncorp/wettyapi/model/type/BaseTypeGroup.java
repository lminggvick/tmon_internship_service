package com.tmoncorp.wettyapi.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseTypeGroup implements EnumMapperGroupType<BaseSubType> {
    DATE("날짜", Collections.unmodifiableList(Arrays.asList(BaseSubType.START_DATE, BaseSubType.END_DATE, BaseSubType.SPLIT_TIME))),
    REAL_TIME("실시간", Collections.unmodifiableList(Arrays.asList(BaseSubType.ONE_DAY, BaseSubType.ONE_WEEK, BaseSubType.ONE_MONTH))),
    GENDER("성", Collections.unmodifiableList(Arrays.asList(BaseSubType.MALE, BaseSubType.FEMALE))),
    AGE("나이", Collections.unmodifiableList((Arrays.asList(BaseSubType.AGE_10, BaseSubType.AGE_20, BaseSubType.AGE_30, BaseSubType.AGE_40, BaseSubType.AGE_50)))),
    REGION("지역", Collections.unmodifiableList(Arrays.asList(BaseSubType.REGION_1, BaseSubType.REGION_2, BaseSubType.REGION_3, BaseSubType.REGION_4, BaseSubType.REGION_5))),

    //추가됨
    CATEGORY_NAME("딜 카테고리", Collections.unmodifiableList(Arrays.asList(BaseSubType.CULTURE, BaseSubType.LOCAL, BaseSubType.TOUR))),
    INQUIRY_TYPE("문의 유형", Collections.unmodifiableList(Arrays.asList(BaseSubType.QUERY, BaseSubType.COMPLAINT))),
    PLATFORM("플랫폼", Collections.unmodifiableList(Arrays.asList(BaseSubType.WEB, BaseSubType.MOBILE))),

    UNKNOWN("Mismatch BaseTypeGroup", Collections.unmodifiableList(Collections.emptyList()));

    private String title;
    @JsonIgnore
    private List<BaseSubType> categories;

    BaseTypeGroup(String title, List<BaseSubType> categories) {
        this.title = title;
        this.categories = categories;
    }

    @JsonCreator
    public static BaseTypeGroup create(BaseType inputType) {
        for (BaseTypeGroup baseType : values()) {
            if (baseType.name().equals(inputType.getCode())) {
                baseType.categories = inputType.getCategories();
                return baseType;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public List<BaseSubType> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + name() + '\'' +
                ", title='" + title + '\'' +
                ", categories=" + categories +
                '}';
    }
}
