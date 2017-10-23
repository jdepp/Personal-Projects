/*
 *  AUTHOR:  Jeremy Deppen
 *
 *  SUMMARY: A class that converts text to a large number or vice-versa
 *           by using the BigInteger class. When casting from text to number or
 *           vice-versa, bytes are used to make the conversion.
 *
 *           We need this class because in RSA encryption, we will be reading
 *           in a file and storing its contents in a string and then transforming that
 *           string into a number and then RSA performs a calculation on that number
 *           which makes that number encrypted. When decrypting, we take this encrypted
 *           number, decrypt it, and then cast it back to the string (the original
 *           file contents if done correctly).
 *
 *           The reason we are using BigInteger is because the number
 *           could be larger than the largest data type can hold (double 64bits),
 *           so we need another way to store this large number. BigInteger can
 *           store insanely large numbers.
 */


import java.math.BigInteger;


public class TextNumberConvert
{
    public TextNumberConvert()
    {}

    public BigInteger textToNumber(String text)
    {
        BigInteger number = new BigInteger(text.getBytes());

        return number;
    }

    public String numberToText(BigInteger number)
    {
        String text = new String(number.toByteArray());

        return text;
    }
}
