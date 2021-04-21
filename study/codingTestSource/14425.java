package com.company;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] command = br.readLine().split(" ");
        int inputGroupCount = Integer.parseInt(command[0]);
        int inputStringCount = Integer.parseInt(command[1]);
        LinkedList<String> Group = new LinkedList<>();
        while ( inputGroupCount --> 0) {
            Group.offer(br.readLine());
        }
        int foundCount = 0;
        while(inputStringCount --> 0) {
            if (Group.indexOf(br.readLine()) != -1)
                foundCount ++;
        }
        bw.write(foundCount + "\n");
        bw.flush();
        br.close();
        bw.close();
    }
}
