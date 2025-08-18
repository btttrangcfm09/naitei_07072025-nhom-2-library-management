package com.group2.library_management.dto.converter;

import com.group2.library_management.dto.enums.MatchMode;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMatchModeConverter implements Converter<String, MatchMode> {

    @Override
    public MatchMode convert(String source) {
        if (source == null || source.isBlank()) {
            return null; 
        }
        try {
            return MatchMode.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MatchMode.ANY;
        }
    }
}
