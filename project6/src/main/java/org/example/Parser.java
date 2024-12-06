package org.example;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;


public class Parser {
    public File inputFile;
    public BufferedReader reader;
    public String currentLine;
    public String instruction;
    public int counter;


    public Parser(File file) {
        try {
            if (file.exists()) {
                this.inputFile = file;
                this.reader = new BufferedReader(new FileReader(file));
                this.currentLine = reader.readLine();
                this.instruction = null;
                this.counter = 0;
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while opening the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // count the total number of lines in the file
    public int countTotalLines() {
        int lines = 0;
        try (BufferedReader tempReader = new BufferedReader(new FileReader(inputFile))) {
            while (tempReader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while counting lines: " + e.getMessage());
        }
        return lines;
    }


    public boolean hasMoreLines() throws IOException {
        return currentLine != null;
    }

    public String lineCleaner(String line) {
        if (line != null && line.contains("//")) // if the string contain // shorter the string
        {
            line = line.substring(0,line.indexOf("/"));
        }
        return line.trim();
    }

    public void advance() throws IOException {
        while (hasMoreLines()) {
            currentLine = lineCleaner(currentLine);
            if (!currentLine.isEmpty()) {
                instruction = currentLine;
                System.out.println("Processed instruction: " + instruction);
            }
            currentLine = reader.readLine();
            counter++;
        }
    }
}