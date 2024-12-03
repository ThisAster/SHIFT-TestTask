package com.thisaster;

import com.thisaster.service.FilterService;
import com.thisaster.util.ArgumentsParser;
import org.apache.commons.cli.ParseException;

public class DataFilterApp {
    public static void main(String[] args) {
        try {
            ArgumentsParser arguments = new ArgumentsParser(args);
            FilterService filterService = new FilterService(arguments);
            filterService.processFiles(arguments.getInputFiles());
        } catch (ParseException e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
