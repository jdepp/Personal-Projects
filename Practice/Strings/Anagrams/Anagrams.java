/*
 *  AUTHOR: Jeremy Deppen
 *  SUMMARY: Checks if two strings are anagrams of each other. For example,
             "hi!" and "!hi" are anagrams because they contain the same chars,
             no matter the order of them.
             Implemented using an int array of size 256 to store how many times
             chars in string1 are seen. This array is initialized automatically to
             0, so if we look up a char in string2 at its ASCII value index in the
             int array and we see 0 or less, that means it is not in string1.
    RUNTIME: Θ(n) - linear because we iterate through two arrays (not nested)
             and it takes constant-time lookup to the int array
             to see if the char in string2 is in string1.
 */


public class Anagrams
{
    public Anagrams()
    {}

    public boolean areAnagrams(String s1, String s2)
    {
        if(s1.length() != s2.length())  return false;
        if(s1 == null || s2 == null) return false;

        int[] asciiArray = new int[256];

        // store in the int array the number of times each char in s1 appears
        for(int i = 0; i < s1.length(); i++) {
            int val = asciiArray[s1.charAt(i)];
            asciiArray[s1.charAt(i)] = val + 1;
        }

        // iterate through s2 checking if the char appears in the int asciiArray
        // at its ASCII value index. If it appears, decrement the value at the
        // index by 1. If the value at the index is 0 or less, this says that
        // the current char is not in s1: return false
        for(int i = 0; i < s2.length(); i++) {
            int val = asciiArray[s2.charAt(i)];
            if(val <= 0) return false;
            else val--;
        }

        return true;
    }

    public static void main(String[] args)
    {
        String test1 = "hello!";
        String test2 = "ehol!l";    // IS an anagram of "hello"
        String test3 = "ehol!";     // NOT an anagram of "hello"
        String test4 = "hello!";     // IS an anagram of "hello";
        String test5 = "Hello!";     // NOT an anagram of "hello";

        Anagrams anagramObj = new Anagrams();

        System.out.print(test1 + " an anagram of " + test2 + " ?   ");
        System.out.println(anagramObj.areAnagrams(test1, test2));  // should return true

        System.out.print(test1 + " an anagram of " + test3 + " ?   ");
        System.out.println(anagramObj.areAnagrams(test1, test3));  // should return false

        System.out.print(test1 + " an anagram of " + test4 + " ?   ");
        System.out.println(anagramObj.areAnagrams(test1, test4));  // should return true

        System.out.print(test1 + " an anagram of " + test5 + " ?   ");
        System.out.println(anagramObj.areAnagrams(test1, test5));  // should return false
    }
}
