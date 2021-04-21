package com.company;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] inputcommand = br.readLine().split(" ");
        int totalPokimon = Integer.parseInt(inputcommand[0]);
        int MyPokimon = Integer.parseInt(inputcommand[1]);
        Map<Integer, String> indexKeyList = new HashMap<>();
        Map<String, Integer> stringKeyList = new HashMap<>();
        int i = 0;
        while (totalPokimon > i) {
            String pokimon = br.readLine();
            indexKeyList.put(i, pokimon);
            stringKeyList.put(pokimon, i++);
        }
        i = 0;
        while (MyPokimon > i) {
            String Search = br.readLine();
            try {
                int index = Integer.parseInt(Search);
                bw.write(indexKeyList.get(index - 1)+ "\n");
                bw.flush();
            } catch (Exception e) {
                bw.write((stringKeyList.get(Search)+1) + "\n");
                bw.flush();
            }
            i++;
        }
        br.close();
        bw.close();
    }
}
