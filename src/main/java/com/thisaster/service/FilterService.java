package com.thisaster.service;

import com.thisaster.util.ArgumentsParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterService {
    private final ArgumentsParser arguments;
    private final List<String> integers = new ArrayList<>();
    private final List<String> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    public FilterService(ArgumentsParser arguments) {
        this.arguments = arguments;
    }

    public void processFiles(List<String> inputFiles) {
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyLine(line.trim());
                }
            } catch (IOException e) {
                System.err.println(("Error processing file: " + e.getMessage()));
            }
        }

        writeOutput();
        printStats();
    }

    private void classifyLine(String line) {
        if (line.matches("-?\\d+")) {
            integers.add(line);
        } else if (line.matches("-?\\d+\\.\\d+([eE][-+]?\\d+)?")) {
            floats.add(line);
        } else if (!line.isEmpty()) {
            strings.add(line);
        }
    }

    private void writeOutput() {
        FileWriterService writer = new FileWriterService();
        String prefix = arguments.getPrefix();
        String outputPath = arguments.getOutputPath();

        if (!integers.isEmpty()) {
            writer.writeData(outputPath + "/" + prefix + "integers.txt", integers, arguments.isAppendMode());
        }
        if (!floats.isEmpty()) {
            writer.writeData(outputPath + "/" + prefix + "floats.txt", floats, arguments.isAppendMode());
        }
        if (!strings.isEmpty()) {
            writer.writeData(outputPath + "/" + prefix + "strings.txt", strings, arguments.isAppendMode());
        }
    }

    private void printStats() {
        System.out.println("Целые числа: " + integers.size());
        System.out.println("Вещественные числа: " + floats.size());
        System.out.println("Строки: " + strings.size());
    }
}
