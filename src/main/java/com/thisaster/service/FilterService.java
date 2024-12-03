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

    private long sumIntegers = 0;
    private float sumFloats = 0F;
    private float minFloat = Float.MAX_VALUE;
    private float maxFloat = Float.MIN_VALUE;
    private long minInteger = Integer.MAX_VALUE;
    private long maxInteger = Integer.MIN_VALUE;
    private long minStringLength = Long.MAX_VALUE;
    private long maxStringLength = Long.MIN_VALUE;

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
            long value = Long.parseLong(line);
            integers.add(line);
            sumIntegers += value;
            minInteger = Math.min(minInteger, value);
            maxInteger = Math.max(maxInteger, value);
        } else if (line.matches("-?\\d+\\.\\d+([eE][-+]?\\d+)?")) {
            floats.add(line);
            float value = Float.parseFloat(line);
            sumFloats += value;
            minFloat = Math.min(minFloat, value);
            maxFloat = Math.max(maxFloat, value);
        } else if (!line.isEmpty()) {
            strings.add(line);
            minStringLength = Math.min(minStringLength, line.length());
            maxStringLength = Math.max(maxStringLength, line.length());
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
        boolean isShortStat = arguments.isShortStats();
        boolean isFullStat = arguments.isFullStats();

        if (isShortStat) {
            System.out.println("Целые числа: " + integers.size());
            System.out.println("Вещественные числа: " + floats.size());
            System.out.println("Строки: " + strings.size());
        }

        if (isFullStat) {
            if (!integers.isEmpty()) {
                System.out.println("Целые числа:");
                System.out.println("  Количество: " + integers.size());
                System.out.println("  Минимум: " + minInteger);
                System.out.println("  Максимум: " + maxInteger);
                System.out.println("  Сумма: " + sumIntegers);
                System.out.println("  Среднее: " + (sumIntegers / (double) integers.size()));
            }

            if (!floats.isEmpty()) {
                System.out.println("Вещественные числа:");
                System.out.println("  Количество: " + floats.size());
                System.out.println("  Минимум: " + minFloat);
                System.out.println("  Максимум: " + maxFloat);
                System.out.println("  Сумма: " + sumFloats);
                System.out.println("  Среднее: " + (sumFloats / floats.size()));
            }

            if (!strings.isEmpty()) {
                System.out.println("Строки:");
                System.out.println("  Количество: " + strings.size());
                System.out.println("  Минимальная длина: " + minStringLength);
                System.out.println("  Максимальная длина: " + maxStringLength);
            }
        }
    }
}
