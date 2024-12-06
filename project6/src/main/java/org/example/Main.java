package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // הגדרת הנתיב לקובץ הקלט
        String filePath = "C:\\Users\\ruths\\Downloads\\add.asm"; // החלף לנתיב המתאים לקובץ שלך

        // יצירת אובייקט File
        File file = new File(filePath);

        // בדיקה אם הקובץ קיים
        if (!file.exists()) {
            System.out.println("Error: File not found at path: " + filePath);
            return;
        }

        // יצירת אובייקט Parser
        Parser parser = new Parser(file);

        try {
            System.out.println("Starting to process the file: " + filePath);
            System.out.println("------------------------------------------------");

            // לולאה שמבצעת קריאות לכל הפונקציות על כל שורה בקובץ
            while (parser.hasMoreLines()) {
                parser.advance(); // מעבד את השורה הנוכחית

                if (parser.instruction != null) {
                    System.out.println("Line " + parser.counter + ": " + parser.instruction);

                    // בדיקת סוג ההוראה
                    INSTRUCTION_Type type = parser.instructionType();
                    System.out.println("Instruction Type: " + type);

                    // הדפסת symbol, dest, comp, jump לפי סוג ההוראה
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
            // סגירת BufferedReader
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









