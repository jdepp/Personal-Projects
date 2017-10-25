/*
 *  AUTHOR:
 *        Jeremy Deppen
 *
 *  SUMMARY:
 *        Class that represents a stack using a linked list.
 */

import java.util.*;

public class MyStack
{
    private StackNode topItem;
    private int stackHeight;

    public MyStack()
    { }

    public void push(int val)
    {
        if(topItem == null)
        {
            topItem = new StackNode(val);
            stackHeight = 1;
            return;
        }
        StackNode newItem = new StackNode(val);
        newItem.next = topItem;
        topItem = newItem;
        stackHeight++;
        return;
    }

    public Integer pop()
    {
        if(topItem != null)
        {
            Integer itemToPop = topItem.number;
            topItem = topItem.next;
            stackHeight--;
            return itemToPop;
        }
        return null;
    }

    public Integer peek()
    {
        if(topItem != null) return topItem.number;
        else  return null;
    }

    public boolean isEmpty()
    {
        if(stackHeight == 0) return true;
        else return false;
    }

    public int getHeight()
    {
        return stackHeight;
    }

    /* Node class to represent the linked list stack */
    private class StackNode
    {
        public int number;
        StackNode next;
        public StackNode(int n)
        {
            number = n;
        }
    }

    public static void main(String[] args) {
        Scanner inScan = new Scanner(System.in);
        MyStack myStack = new MyStack();
        while(true)
        {
            System.out.println("Enter an int or '-1' to stop");
            int userInt = inScan.nextInt();
            if(userInt == -1) break;
            myStack.push(userInt);
        }
        System.out.println("\nYou entered:");
        while(!myStack.isEmpty())
        {
            System.out.println(myStack.pop());
        }
    }
}
