package org.software.testing;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileWriter {
    public static boolean writeToFile(String fileName, String content) {
        if(fileName == null || content == null) {
            return false;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean isFileCreated = file.createNewFile();
                if (!isFileCreated) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        if (file.canWrite()) {
            try {
                Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
