package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File(C:\Users\ruths\OneDrive\02 second year\03 - Nand\NandToTetris\6\add");  // יש להחליף בנתיב לקובץ שלך
        Parser parser = new Parser(file);

        try {
            while (parser.hasMoreLines()) {
                parser.advance();
                System.out.println("Current instruction: " + parser.instruction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
