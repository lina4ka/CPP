package com.company;

public class Palindrome {
    public static String reverseString(String s)
    {
        StringBuilder rString = new StringBuilder(s);
        return rString.reverse().toString();
    }
    public static boolean isPalindrome(String s)
    {
        return s.equals(reverseString(s));
    }
    public static void main(String[] args)
    {
        for (int i = 0; i < args.length; i++ )
        {
            if (isPalindrome(args[i]))
                System.out.println(args[i] + "\tis Palindrome");
            else
                System.out.println(args[i] + "\tnot Palindrome");
        }
    }
}
