package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int s = Integer.parseInt(br.readLine());
        for (; s > 0 ; s--){
            String[] command = br.readLine().split("");
            int count = 0;
            for (String i : command) {
                if (i.equals("("))
                    count ++;
                else {
                    if (count <= 0 ) {
                        count = -1;
                        break;
                    } else
                        count--;
                }
            }
            System.out.println(count == 0?"YES":"NO");
        }
    }
}
