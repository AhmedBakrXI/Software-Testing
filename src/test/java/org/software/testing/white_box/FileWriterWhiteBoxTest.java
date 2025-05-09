package org.software.testing;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class FileWriterWhiteBoxTest {

    private Path testFilePath;

    @Test
    public void testWriteToFile_NullFileName() {
        // Act
        boolean result = FileWriter.writeToFile(null, "Content");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testWriteToFile_NullContent() {
        // Act
        boolean result = FileWriter.writeToFile("test_files/nullContent.txt", null);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testWriteToFile_FileDoesNotExist_CreatesFileAndWritesContent() throws IOException {
        // Arrange
        testFilePath = Path.of("test_files/newFile.txt");
        String content = "New file content";

        // Act
        boolean result = FileWriter.writeToFile(testFilePath.toString(), content);

        // Assert
        assertTrue(result);
        assertTrue(Files.exists(testFilePath));
        assertEquals(content, Files.readString(testFilePath, StandardCharsets.UTF_8));
    }

    @Test
    public void testWriteToFile_FileExists_WritesContent() throws IOException {
        // Arrange
        testFilePath = Path.of("test_files/existingFile.txt");
        Files.createDirectories(testFilePath.getParent());
        Files.writeString(testFilePath, "Old content", StandardCharsets.UTF_8);
        String newContent = "Updated content";

        // Act
        boolean result = FileWriter.writeToFile(testFilePath.toString(), newContent);

        // Assert
        assertTrue(result);
        assertEquals(newContent, Files.readString(testFilePath, StandardCharsets.UTF_8));
    }

    @Test
    public void testWriteToFile_FileCannotBeCreated() {
        // Act
        boolean result = FileWriter.writeToFile("/invalid/path/file.txt", "Content");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testWriteToFile_ExceptionDuringFileCreation() {
        // Act
        boolean result = FileWriter.writeToFile("/invalid/path/file.txt", "Content");

        // Assert
        assertFalse(result); // Should return false due to exception during file creation
    }

    @After
    public void cleanUp() {
        if (testFilePath != null) {
            File file = testFilePath.toFile();
            if (file.exists() && !file.setWritable(true)) {
                System.err.println("Failed to make writable: " + file.getAbsolutePath());
            }
            if (file.exists() && !file.delete()) {
                System.err.println("Failed to delete: " + file.getAbsolutePath());
            }
        }
    }
}