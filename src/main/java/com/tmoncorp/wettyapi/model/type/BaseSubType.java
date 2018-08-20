package com.tmoncorp.wettyapi.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseSubType implements EnumMapperType {

    /**
     * DATE의 sub types
     */
    START_DATE("시작", ""),
    END_DATE("끝", ""),
    SPLIT_TIME("분할 시간", ""),
    /**
     * REAL_TIME의 sub type
     */
//    DAYS("일수", ""),
    ONE_MONTH("30", ""),
    ONE_WEEK("7", ""),
    ONE_DAY("1", ""),
    /**
     * GENDER의 sub types
     */
    MALE("남", ""),
    FEMALE("녀", ""),
    /**
     * AGE의 sub types
     */
    AGE_10("10대", ""),
    AGE_20("20대", ""),
    AGE_30("30대", ""),
    AGE_40("40대", ""),
    AGE_50("50대", ""),
    /**
     * REGION의 sub types
     */
    REGION_1("서울", ""),
    REGION_2("인천", ""),
    REGION_3("대구", ""),
    REGION_4("대전", ""),
    REGION_5("부산", ""),
    // 추가됨
    /**
     * CATEGORY_NAME의 sub types
     */
    CULTURE("컬쳐", ""),
    LOCAL("지역", ""),
    TOUR("여행", ""),
    /**
     * INQUIRY_TYPE의 sub types
     */
    QUERY("질문", ""),
    COMPLAINT("항의", ""),
    /**
     * PLATFORM의 sub types
     */
    WEB("웹", ""),
    MOBILE("모바일", ""),

    UNKNOWN("Mismatch BaseSubType");

    private String title;
    private String value;

    BaseSubType(String title) {
        this.title = title;
    }

    BaseSubType(String title, String value) {
        this.title = title;
        this.value = value;
    }

    @JsonCreator
    public static BaseSubType create(BaseTypeCategory baseTypeCategory) {
        for (BaseSubType subType : values()) {
            if (subType.equals(BaseSubType.valueOf(baseTypeCategory.getCategoryKey()))) {
                subType.value = baseTypeCategory.getCategoryValue();
                return subType;
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

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + name() + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
