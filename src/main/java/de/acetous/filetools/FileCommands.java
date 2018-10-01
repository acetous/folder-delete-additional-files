package de.acetous.filetools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

@ShellComponent
public class FileCommands {

    private DirectoryService directoryService;

    @Autowired
    public FileCommands(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @ShellMethod("List files in work that are not present in base.")
    public String list(
            @ShellOption Path base,
            @ShellOption Path comapre
    ) throws IOException {
        return directoryService.findAdditionalFiles(base, comapre).stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining("\n"));
    }
}
