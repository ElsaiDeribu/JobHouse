package com.jobhouse.finder.Helpers;

import java.util.HashMap;
import java.util.Map;

import com.jobhouse.finder.Tables.CompanyProfile.Type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringByTypeConverter implements Converter<String, Type> {

    private Map<String, Type> typeMap = new HashMap<>();

    public StringByTypeConverter() {
        typeMap.put("IT", Type.IT);
        typeMap.put("CONSTRUCTION", Type.CONSTRUCTION);
        typeMap.put("INDUSTRY", Type.INDUSTRY);
        typeMap.put("BUSINESS", Type.BUSINESS);
        typeMap.put("OTHER", Type.OTHER);
    }

    @Override
    public Type convert(String id) {
        return typeMap.get(id);
    }
}
