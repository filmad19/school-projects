package at.kaindorf.Theorie_Thread.Consumer_Producer;

import java.util.Arrays;

public class Stack {
    private int[] values;
    private int tos = 0;

    public Stack(int size){
        values = new int[size];
    }

    public boolean isEmpty(){
        return tos == 0;
    }

    public boolean isFull(){
        return tos == values.length;
    }

    public void push(int value){
        if(isFull()){
            throw new RuntimeException("stack is full");
        }

        values[tos++] = value;
    }

    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("stack is empty");
        }

        return values[--tos];
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[");

        for(int i = 0;i < tos;i++){
            stringBuffer.append(values[i] + (i == tos-1 ? "" : ", "));
        }

        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        stack.push(3);
        stack.push(11);
        stack.push(12);
        stack.push(13);
        stack.push(14);
        stack.pop();

        System.out.println(stack);
    }
}
