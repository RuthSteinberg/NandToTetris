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


    public Parser(File file) {
        try {
            if (file.exists()) {
                this.inputFile = file;
                this.reader = new BufferedReader(new FileReader(file));
                this.currentLine = reader.readLine();
                this.instruction = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("can't reading from the file");
        }
    }

    public boolean hasMoreLines() throws IOException {
        return currentLine != null; // if currentLine!=null return true - there is more line
    }
    public String lineCleaner(String line) {
        if (line.contains("//")) // if the string contain // shorter the string
        {
            line = line.substring(0,line.indexOf("/"));
        }
    return line;
    }
    public void advance() throws IOException {
        while (currentLine != null) {
            if(currentLine.contains("//")) {
                currentLine = lineCleaner(currentLine);
            }
            if (!currentLine.isEmpty())
            {
                instruction = currentLine;
            }
            currentLine = reader.readLine();
        }
    }
}

