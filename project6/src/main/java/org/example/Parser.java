package org.example;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;

enum INSTRUCTION_Type {
    A_INSTRUCTION,
    C_INSTRUCTION,
    L_INSTRUCTION
}

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
                this.instruction = currentLine;
                this.counter = 0;
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while opening the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetToFirstInstruction() throws IOException {
        reader.close(); // Close the current reader
        this.reader = new BufferedReader(new FileReader(inputFile));
        this.currentLine = reader.readLine(); // Set the current line to the first instruction
        this.instruction = currentLine;
    }

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
        return counter < countTotalLines() -1;
    }

    public String lineCleaner(String line) {
        if (line != null && line.contains("//")) // if the string contain // shorter the string
        {
            line = line.substring(0, line.indexOf("/"));
        }
        return line.trim();
    }

    public void advance() throws IOException {
        if (hasMoreLines()) {
            counter++;
            currentLine = reader.readLine();
            if (currentLine != null) {
                currentLine = lineCleaner(currentLine);
                if(!currentLine.isEmpty()) {
                    instruction = currentLine;
                } else {
                    advance();
                }
            }
            //currentLine = reader.readLine();
        }
    }

    public INSTRUCTION_Type instructionType() {
        if(instruction.charAt(0) == '@') {
            return INSTRUCTION_Type.A_INSTRUCTION;
        }
        else if(instruction.charAt(0) == '(') {
            return INSTRUCTION_Type.L_INSTRUCTION;
        }
        else {
            return INSTRUCTION_Type.C_INSTRUCTION;
        }
    }

    public String symbol() {
        String str = "";
        if(instructionType() != INSTRUCTION_Type.C_INSTRUCTION) {
            if(instructionType() == INSTRUCTION_Type.A_INSTRUCTION) {
                str = instruction.substring(1);
            } else {
                str = instruction.substring(1, instruction.length()-1);
            }
        }
        return str;
    }

    public String dest() {
        String str="";
        if(instructionType() == INSTRUCTION_Type.C_INSTRUCTION) {
            if(instruction.contains("=")) {
                int end = instruction.indexOf("=");
                return str = instruction.substring(0, end);
            }
        }
        return str;
    }

    public String jump() {
        String str="";
        if(instructionType() == INSTRUCTION_Type.C_INSTRUCTION) {
            if(instruction.contains(";")) {
                int start = instruction.indexOf(";");
                return str = instruction.substring(start+1); //instruction.substring(end+1);
            }
        }
        return str;
    }

    public String comp() {
        String str="";
        if(instructionType() == INSTRUCTION_Type.C_INSTRUCTION) {
            if(!instruction.contains(";")) {
                int start = instruction.indexOf("=");
                str = instruction.substring(start+1);
            } else {
                int start = instruction.indexOf("=");
                int end = instruction.indexOf(";");
                str = instruction.substring(start+1, end);
            }
        }
        return str;
    }
}