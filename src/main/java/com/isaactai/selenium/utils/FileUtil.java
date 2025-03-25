package com.isaactai.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author tisaac
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * Creates parent directories for a file path if they do not exist.
     *
     * @param file the target file (not directory)
     */
    public static void ensureParentDirExists(File file) {
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                logger.warn("Failed to create directory: " + parentDir.getAbsolutePath());
            } else {
                logger.info("Created directory: " + parentDir.getAbsolutePath());
            }
        }
    }
}
