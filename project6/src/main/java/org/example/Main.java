package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String filePath = "C:\\Users\\ruths\\Downloads\\add.asm";

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Error: File not found at path: " + filePath);
            return;
        }

        Parser parser = new Parser(file);

        try {
            System.out.println("Starting to process the file: " + filePath);
            System.out.println("------------------------------------------------");

            while (parser.hasMoreLines()) {
                parser.advance();

                if (parser.instruction != null) {
                    System.out.println("Line " + parser.counter + ": " + parser.instruction);

                    INSTRUCTION_Type type = parser.instructionType();
                    System.out.println("Instruction Type: " + type);

                    if (type == INSTRUCTION_Type.A_INSTRUCTION || type == INSTRUCTION_Type.L_INSTRUCTION) {
                        System.out.println("Symbol: " + parser.symbol());
                    } else if (type == INSTRUCTION_Type.C_INSTRUCTION) {
                        System.out.println("Dest: " + parser.dest());
                        System.out.println("Comp: " + parser.comp());
                        System.out.println("Jump: " + parser.jump());
                    }
                }
                System.out.println("------------------------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Error while processing the file: " + e.getMessage());
            e.printStackTrace();
        } finally {

            try {
                if (parser.reader != null) {
                    parser.reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing the reader: " + e.getMessage());
            }
        }

        System.out.println("Finished processing the file.");
    }
}









