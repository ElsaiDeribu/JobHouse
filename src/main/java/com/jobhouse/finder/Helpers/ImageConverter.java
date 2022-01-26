package com.jobhouse.finder.Helpers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageConverter implements Converter<MultipartFile, String> {

    @Override
    public String convert(MultipartFile image) {
        try {
            return Base64.getEncoder().encodeToString(image.getBytes());
        } catch (IOException e) {
            return null;
        }
    }
}
