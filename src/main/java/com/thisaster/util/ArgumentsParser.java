package com.thisaster.util;

import lombok.Getter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.List;

@Getter
public class ArgumentsParser {
    private final List<String> inputFiles;
    private final String outputPath;
    private final String prefix;
    private final boolean appendMode;
    private final boolean shortStats;
    private final boolean fullStats;

    public ArgumentsParser(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("s", false, "Short statistics");
        options.addOption("f", false, "Full statistics");
        options.addOption("a", false, "Append mode");
        options.addOption("p", true, "Prefix for output files");
        options.addOption("o", true, "Output directory");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        this.appendMode = cmd.hasOption("a");
        this.shortStats = cmd.hasOption("s");
        this.fullStats = cmd.hasOption("f");
        this.prefix = cmd.getOptionValue("p", "");
        this.outputPath = cmd.getOptionValue("o", ".");

        this.inputFiles = cmd.getArgList();

        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("No input files specified.");
        }
    }

}

