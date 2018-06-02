package com.springboot.api.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ApplicationConstants {

    // All anticipated document types.
    public static final Map<String, String> TIMESHEET_FILE_EXTENSION_MAP = ImmutableMap.<String, String>builder()
            //Images
            .put("bmp", "image/bmp")
            .put("jpg", "image/jpeg")
            .put("jpeg", "image/jpeg")
            .put("png", "image/png")
            .put("pdf", "application/pdf")
            .put("txt", "text/plain")
            .build();
}
