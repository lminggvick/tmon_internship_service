package com.tmoncorp.wettyapi.model.type;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTypeCategory extends BaseModelSupport {
    private int graphId;
    private String categoryKey;
    private String categoryValue;

    public BaseTypeCategory(String categoryKey, String categoryValue) {
        this.categoryKey = categoryKey;
        this.categoryValue = categoryValue;
    }
}
