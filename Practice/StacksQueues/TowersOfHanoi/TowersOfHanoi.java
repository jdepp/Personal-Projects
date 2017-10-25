import java.util.*;

public class TowersOfHanoi
{
    public static void main(String[] args)
    {
        Scanner inScan = new Scanner(System.in);
        System.out.println("How many disks?");
        int numDisks = inScan.nextInt();

        MyStack stack1 = new MyStack();
        MyStack stack2 = new MyStack();
        MyStack stack3 = new MyStack();

        for(int i = 0; i < numDisks; i++)
            stack1.push(i);


        // Loop until numDisks-1 are in stack2
        while(stack1.getHeight() != numDisks-1)
        {
            stack3.push(stack1.pop());
        }

        // Move largest disk to stack3
        stack3.push(stack1.pop());

        while(stack3.getHeight() != numDisks)
        {

        }

    }
}
