package de.acetous.filetools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class DirectoryServiceTest {

    private DirectoryService testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new DirectoryService();
    }

    @Test
    void shouldCompareDirectories() throws IOException {
        Path baseDirectory = Files.createTempDirectory("base").toAbsolutePath();
        Path compareDirectory = Files.createTempDirectory("compare").toAbsolutePath();

        Files.createFile(baseDirectory.resolve("one.bar"));
        Files.createFile(compareDirectory.resolve("one.foo"));
        Files.createFile(compareDirectory.resolve("two.foo"));

        List<Path> additionalFiles = testSubject.findAdditionalFiles(baseDirectory, compareDirectory);

        assertThat(additionalFiles).extracting(path -> path.getFileName().toString()).containsOnly("two.foo");
    }
}