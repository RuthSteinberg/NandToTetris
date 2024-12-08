package org.example;

import java.util.HashMap;
import java.util.Map;


public class SymbolTable{
    public Map<String,Integer> table;


    public SymbolTable() {
        this.table = new HashMap<>();
    }
    public void AddEntry(String str, int num) {
        table.put(str, num);
    }

    public boolean Contains(String str) {
        return table.containsKey(str);
    }

    public int getAdress(String str) {
        if(Contains(str)) {
            return table.get(str);
        }
        else return -1;
    }
}