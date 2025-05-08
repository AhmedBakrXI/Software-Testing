package org.software.testing;

import org.junit.Test;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class FileWriterTest
{

    private Path testFilePath;
    @Test
    public void writeToFile_createFileSuccessfully() {
        testFilePath = Path.of("test_files/output_file_test.txt");
        boolean result = FileWriter.writeToFile(testFilePath.toString(),"Ahmed Khaled\nBatMan :)");
        assertTrue(result);
        assertTrue(Files.exists(testFilePath));
    }

    @Test
    public void writeToFile_createFileWithCorrectContent() throws IOException {
        testFilePath = Path.of("test_files/output_file_test.txt");
        String content = "Ahmed Khaled\nBatMan :)";
        boolean result = FileWriter.writeToFile(testFilePath.toString(),content);
        assertTrue(result);
        assertTrue(Files.exists(testFilePath));
        String fileContent = Files.readString(testFilePath, StandardCharsets.UTF_8);
        assertEquals(fileContent,content);
    }
    @Test
    public void writeToFile_overwritesExistingFile() throws IOException {
        testFilePath = Path.of("test_files/overwrite_test.txt");
        Files.createDirectories(testFilePath.getParent());

        // Write initial content
        Files.writeString(testFilePath, "Old Content", StandardCharsets.UTF_8);

        // Write new content using the method
        String newContent = "New Content";
        boolean result = FileWriter.writeToFile(testFilePath.toString(), newContent);

        assertTrue(result);
        String fileContent = Files.readString(testFilePath, StandardCharsets.UTF_8);
        assertEquals(newContent, fileContent);
    }
    @Test
    public void writeToFile_invalidFilePath_returnsFalse() {
        String invalidPath = "test_files/invalid<>path.txt"; // Illegal on Windows
        boolean result = FileWriter.writeToFile(invalidPath, "Some Content");

        assertFalse(result);
    }
    @Test
    public void writeToFile_emptyFileName_returnsFalse() {
        boolean result = FileWriter.writeToFile("", "Hello");
        assertFalse(result);
    }
    @Test
    public void writeToFile_nullFilePath_returnsFalse() {
        boolean result = FileWriter.writeToFile(null, "Some Content");
        System.out.println(result);
        assertFalse(result);
    }



    @After
    public void cleanUp() {
        if(testFilePath != null)
        {
            File file = testFilePath.toFile();
            if(file.exists())
            {
                boolean fileStatus = file.delete();
                if(!fileStatus)
                {
                    System.err.println("Failed to delete: " + file.getAbsolutePath());
                }
            }
        }
    }
}