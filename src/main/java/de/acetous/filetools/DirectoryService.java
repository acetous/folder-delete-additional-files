package de.acetous.filetools;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
public class DirectoryService {
    public List<Path> findAdditionalFiles(Path base, Path compare) throws IOException {
        List<String> baseFiles;
        try (DirectoryStream<Path> baseDirectoryStream = Files.newDirectoryStream(base)) {
            baseFiles = stream(baseDirectoryStream.spliterator(), false)
                    .map(path -> FilenameUtils.getBaseName(path.getFileName().toString()))
                    .collect(Collectors.toList());
        }

        List<Path> additionalFiles;
        try (DirectoryStream<Path> compareDirectoryStream = Files.newDirectoryStream(compare)) {
            additionalFiles = stream(compareDirectoryStream.spliterator(), false)
                    .filter(path -> !baseFiles.contains(FilenameUtils.getBaseName(path.getFileName().toString())))
                    .collect(Collectors.toList());
        }

        return additionalFiles;
    }

}
