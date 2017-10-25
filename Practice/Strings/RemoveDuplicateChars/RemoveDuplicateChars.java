/*  AUTHOR: Jeremy Deppen
 *  SUMMARY: Removes duplicate characters from a string by casting to a
 *           character array and utitilizing an int array of 256 (ASCII vals)
 *           to represent which characters have already been seen.
 *           An uppercase version of a character is treated as not being the
 *           same as its lowercase equivilant. Spaces are also removed if
 *           there are more than one.
 */


import java.util.*;

public class RemoveDuplicateChars
{
    public RemoveDuplicateChars()
    { }

    public String remove(String string)
    {
        char[] charArr = string.toCharArray();

        if(charArr == null) return string;
        if(charArr.length <= 1) return string;

        int[] asciiArray = new int[256];

        for(int i = 0; i < charArr.length; i++)
        {
            // Character seen - set index to null
            if(asciiArray[charArr[i]] == 1)
                charArr[i] = '\u0000';
            // Not seen - set its ascii value index to 1 to show this
            else
                asciiArray[charArr[i]] = 1;
        }

        String newString = "";
        for(int i = 0; i < charArr.length; i++)
        {
            // Append char to string if not null
            if(charArr[i] != '\u0000')
                newString += charArr[i];
        }

        return newString;
    }

    /* MAIN method for test cases*/
    public static void main(String[] args)
    {
        RemoveDuplicateChars rem = new RemoveDuplicateChars();
        String test1 = "Hello World!";
        String test2 = "abcdeA";
        String test3 = "AAAAAAABBBBBBBCCCCCCCDDDDDD";
        String test4 = "My name is Jeremy Deppen and I study computer science!";

        System.out.println("\nOriginal string: " + test1);
        System.out.println("Remove duplicate characters: " + rem.remove(test1) +"\n");

        System.out.println("Original string: " + test2);
        System.out.println("Remove duplicate characters: " + rem.remove(test2) + "\n");

        System.out.println("Original string: " + test3);
        System.out.println("Remove duplicate characters: " + rem.remove(test3) + "\n");

        System.out.println("Original string: " + test4);
        System.out.println("Remove duplicate characters: " + rem.remove(test4) + "\n");
    }
}
