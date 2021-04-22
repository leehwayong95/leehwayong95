package com.company;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int heapSize = Integer.parseInt(br.readLine());

        Queue<Integer> heap = new PriorityQueue<>();

        while (heapSize --> 0) {
            int input = Integer.parseInt(br.readLine());
            if (input == 0) {
                if (heap.isEmpty()) {
                    bw.write(0 + "\n");
                } else {
                    bw.write(heap.poll() * -1 + "\n");
                }
            } else {
                heap.add(-1 * input);
            }
        }
        bw.flush();
        br.close();
        bw.close();
    }
}
