package com.company;
import java.io.*;
import java.util.LinkedList;


public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int s = Integer.parseInt(br.readLine());
        LinkedList<String> queue = new LinkedList<String>();
        while( s --> 0){
            String[] command = br.readLine().split(" ");
            if (command.length > 1) {
                queue.offer(command[1]);
            } else {
                boolean isEmpty = queue.isEmpty();
                switch (command[0]) {
                    case "pop":
                        bw.write((isEmpty ? -1 : queue.pop()) + "\n");
                        break;
                    case "size":
                        bw.write(queue.size() + "\n");
                        break;
                    case "empty":
                        bw.write((isEmpty ? 1 : 0)+"\n");
                        break;
                    case "front":
                        bw.write((isEmpty ? -1 : queue.peek())+"\n");
                        break;
                    case "back":
                        bw.write((isEmpty ? -1 : queue.peekLast()) + "\n");
                        break;
                }
            }
        }
        br.close();
        bw.flush();
        bw.close();
    }
}
