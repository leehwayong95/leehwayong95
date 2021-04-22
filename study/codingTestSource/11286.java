package com.company;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int heapSize = Integer.parseInt(br.readLine());

        Queue<Integer> minheap = new PriorityQueue<>();
        Queue<Integer> normalHeap = new PriorityQueue<>();

        while (heapSize --> 0) {
            int input = Integer.parseInt(br.readLine());
            if (input == 0) {
                if (minheap.isEmpty() && normalHeap.isEmpty()) {
                    bw.write(0 + "\n");
                } else if (minheap.isEmpty()) {
                    bw.write(normalHeap.poll() + "\n");
                } else if (normalHeap.isEmpty()) {
                    bw.write(-1 * minheap.poll() + "\n");
                } else {
                    if (minheap.peek() > normalHeap.peek()) {
                        bw.write(normalHeap.poll()+"\n");
                    } else {
                        bw.write(-1 * minheap.poll()+"\n");
                    }
                }
            } else {
                if (input < 0)
                    minheap.add(-1 * input);
                else
                    normalHeap.add(input);
            }
        }
        bw.flush();
        br.close();
        bw.close();
    }
}
