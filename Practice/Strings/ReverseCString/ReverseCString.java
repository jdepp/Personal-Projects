import java.util.*;

public class ReverseCString
{
    public ReverseCString()
    { }

    public String reverse(String string)
    {
        char[] array = string.toCharArray();
        int endIndex = array.length - 2;
        for(int i = 0; i < array.length / 2; i++)
        {
            array = swap(array, i, endIndex);
            endIndex--;
        }
        string = String.valueOf(array);
        return string;
    }

    public char[] swap(char[] arr, int x, int y)
    {
        char temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
        return arr;
    }

    public static void main(String[] args)
    {
        Scanner inScan = new Scanner(System.in);

        System.out.println("Enter a string you want reversed:");
        String userString = inScan.nextLine();
        System.out.println("Appending null char to the end\n");
        userString += '\0';

        System.out.println("Reversing " + userString);
        ReverseCString reverseObj = new ReverseCString();
        userString = reverseObj.reverse(userString);
        System.out.println("Reversed string: " + userString + "\n");
    }
}
