package org.example;


import java.io.*;

//import static com.sun.tools.javac.tree.TreeInfo.symbol;
import static org.example.Code.*;
import static org.example.INSTRUCTION_Type.L_INSTRUCTION;
import static org.example.INSTRUCTION_Type.A_INSTRUCTION;
import static org.example.INSTRUCTION_Type.C_INSTRUCTION;

public class HackAssembler {

    public static String intToBinary(int number, int padding) {
        return String.format("%" + padding +"s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) if (!Character.isDigit(c)) return false;
        return true;
    }

    public static void main(String[] args) throws IOException {

        SymbolTable table = new SymbolTable();

        for (int i = 0; i < 16; i++) { //enter to table the regular symbols
            table.AddEntry("R" + i, i);
        }
        table.AddEntry("SCREEN", 16384);
        table.AddEntry("KBD", 24576);
        table.AddEntry("SP", 0);
        table.AddEntry("LCL", 1);
        table.AddEntry("ARG", 2);
        table.AddEntry("THIS", 3);
        table.AddEntry("THAT", 4);

        int ACounter = 16;
        String FinalOutput = "";

        String filePath = "C:\\Users\\ruths\\Downloads\\Add.hack";
        File file = new File(filePath);
        //File file = new File(args[0]);
        if (!file.exists())
            System.out.println("file doesn't exist");
        Parser parser = new Parser(file);
        Code code = new Code();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String path = file.getAbsolutePath();
        String filename = file.getName();
        int dot = filename.indexOf(".");
        String fileNameNoType = filename.substring(0, dot);
        int fullPathIndex = file.getAbsolutePath().indexOf(filename);
        String LongPath = path.substring(0, fullPathIndex);
        String finalPath = LongPath + fileNameNoType + ".hack"; //changed to .hack finish
        File outputfile = new File(finalPath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile));

        int counterNotL=0;
        int countLinstruction = 0;

        while (parser.hasMoreLines()) { //first run
            parser.advance();
            String line = parser.instruction;
            if (parser.instructionType() == L_INSTRUCTION) {
                String symbol = parser.symbol();
                if(!table.Contains(symbol)) { //if already exist does nothing
                    table.AddEntry(symbol, counterNotL - countLinstruction);
                    countLinstruction++;
                }
            }
            counterNotL++;
        }
        parser.resetToFirstInstruction();

        parser.counter = 0;
        int countLinesAgain = 0;
        String val = "";
        while (parser.hasMoreLines()) {
            parser.advance();
            if (parser.instructionType() == L_INSTRUCTION) {
                String symbol = parser.symbol();
                if(!table.Contains(symbol)) { //if already exist does nothing
                    table.AddEntry(symbol, parser.counter+1);
                }
            } else if(parser.instructionType() == A_INSTRUCTION) {
                String symbol = parser.symbol();
                if(!isNumeric(symbol)) { //enter in binary num/string to the table
                    if(!table.Contains(symbol)) {
                        table.AddEntry(symbol, ACounter);
                        ACounter++;
                    }
                    val = intToBinary(table.getAdress(symbol), 15);
                } else {
                    val = intToBinary(Integer.parseInt(symbol), 15);
                }
                writer.write("0" + val);
                writer.newLine();
            } else if(parser.instructionType() == C_INSTRUCTION) {
                String dest = Code.getDest(parser.dest());
                String comp = Code.getComp(parser.comp());
                String jump = Code.getJump(parser.jump());
                String fullBinaryC = "111" +comp + dest + jump;
                writer.write(fullBinaryC);
                writer.newLine();
            }
            countLinesAgain++;
        }
        writer.close();
    }
}