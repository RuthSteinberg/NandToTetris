package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // נתיב הקובץ
        File file = new File("C:\\Users\\ruths\\Downloads\\nand2tetris.zip\\nand2tetris\\projects\\6\\add");


        Parser parser = new Parser(file);

        try {

            if (parser.hasMoreLines()) {
                System.out.println("There are more lines to read.");
            } else {
                System.out.println("No more lines to read.");
            }

            while (parser.hasMoreLines()) {
                parser.advance();  // Move to the next instruction
                System.out.println("Current instruction: " + parser.instruction);
            }
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
