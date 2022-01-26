package com.jobhouse.finder.Helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

public class HashMapByUserHelperConverter implements Converter<HashMap<String, String>, UserHelper> {
    public UserHelper userHelper;

    @Override
    public UserHelper convert(HashMap<String, String> map) {
        for (Map.Entry<String, String> set : map.entrySet()) {
            this.userHelper.setUsername(set.getKey());
            this.userHelper.setFull_name(set.getValue());
        }
        return this.userHelper;
    }

}
