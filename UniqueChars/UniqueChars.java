import java.util.*;

public class UniqueChars
{

    public UniqueChars()
    { }

    public boolean allUniqueChars(String userString)
    {
        char[] charArray = userString.toCharArray();
        int[] asciiArray = new int[256];

        // if larger than 256, there has to be atleast 1 duplicate
        if(charArray.length > 256)
            return false;

        for(int i = 0; i < charArray.length; i++)
        {
            int asciiIndex = (int)charArray[i];

            // Element is already at index - duplicate
            if(asciiArray[asciiIndex] == 1)
                return false;
            asciiArray[asciiIndex] = 1;
        }
        return true;
    }

    public static void main(String[] args)
    {
        UniqueChars UC = new UniqueChars();

        String test1 = "abcd";          // returns true
        String test2 = "aabcdd";        // returns false
        String test3 = ".,/13kl;jaeyp"; // returns true

        String test4 = "";              // returns true
        for(int i = 0; i < 256; i++)    // all unique ascii chars
            test4 += (char)i;

        String test5 = "";              // returns false
        for(int i = 0; i < 256; i++)    // all unique ascii chars + 'd'
            test5 += (char)i;           // d is repeated twice - not all unique
        test5 += 'd';

        System.out.println("TESTING PREMADE STRINGS");
        System.out.println("Testing string: " + test1 + "..." + UC.allUniqueChars(test1) + "\n");
        System.out.println("Testing string: " + test2 + "..." + UC.allUniqueChars(test2) + "\n");
        System.out.println("Testing string: " + test3 + "..." + UC.allUniqueChars(test3) + "\n");
        System.out.println("Testing string: " + test4 + "..." + UC.allUniqueChars(test4) + "\n");
        System.out.println("Testing string: " + test5 + "..." + UC.allUniqueChars(test5) + "\n");

        System.out.println("\nEnter a string to see if it's unique!");
        Scanner inScan = new Scanner(System.in);
        String customString = inScan.nextLine();
        System.out.println("Testing string: " + customString + "..." + UC.allUniqueChars(customString) + "\n");

    }

}
