package com.tmoncorp.wettyapi.model.type;

import com.tmoncorp.core.model.BaseModelSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseType extends BaseModelSupport {
    private String code;
    private List<BaseSubType> categories;   //BaseSubType -> BaseTypeCategory
}
