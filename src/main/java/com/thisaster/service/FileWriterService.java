package com.thisaster.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterService {
    public void writeData(String filepath, List<String> data, boolean append) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, append))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(("Error write data to file: " + e.getMessage()));
        }
    }
}
