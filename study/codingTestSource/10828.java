import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        queue stack = new queue();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int s = Integer.parseInt(br.readLine());
        for (; s > 0 ; s--){
            String[] split_command = br.readLine().split(" ");
            if(split_command.length > 1)
                stack.push(split_command[1]);
            else {
                if(split_command[0].equals("top")) {
                    System.out.println(stack.top());
                } else if(split_command[0].equals("size")) {
                    System.out.println(stack.Size());
                } else if(split_command[0].equals("empty")) {
                    System.out.println(stack.empty());
                } else {
                    System.out.println(stack.pop());
                }
            }
        }
    }


    public static class queue {
        List<String> stack = new ArrayList<String>();
//      int pointer = 0;
        public int empty () {
            if(stack.size() == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        public void push (String s) {
            stack.add(s);
        }
        public String pop () {
            if (stack.size() <= 0) {
                return "-1";
            } else {
                String result = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                return result;
            }
        }
        public int Size () {
            return stack.size();
        }
        public String top () {
            if (stack.size() <= 0) {
                return "-1";
            } else {
                return stack.get(stack.size() - 1);
            }
        }
    }
}
