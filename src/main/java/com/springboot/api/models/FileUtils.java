package com.springboot.api.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author agamull
 */
@Component
@Slf4j
public class FileUtils {


    public FileUtils() {
    }

    /**
     * Check if the given file name is a valid file name. Return false if any of
     * the characters in the file name has !@#$%^&*()
     *
     * @param fileName
     * @return
     */
    public boolean validFileName(String fileName) {
        log.debug("Inside validFileName method of EcasFileUtils");
        return !StringUtils.containsAny(fileName, "!@#$%^&*()");
    }

    /**
     * Check if the given file type is a valid file type which falls under the
     * acceptable file types.
     *
     * @param fileName
     * @return
     */
    public boolean validFileType(String fileName) {
        log.debug("Inside validateFileType method of EcasFileUtils");
        Pattern pattern;
        Matcher matcher;
        final String FILETYPE_PATTERN = "([^\\s]+(\\.(?i)(txt|jpg|jpeg|png|pdf|doc|docx|xls|xlsx|msg))$)";
        pattern = Pattern.compile(FILETYPE_PATTERN);
        matcher = pattern.matcher(fileName);

        return matcher.matches();
    }

}