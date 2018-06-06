/**
  * Created by Sasddam on 05/31/2018.
  */
 
package com.example.unit_testing;


public class StringHelper {
		
	// Remove character 'A' from given string in case if 'A' occours in first two position
	// ABCD : BCD, AACD : CD, BCAD : BCAD
	public String TruncateAInFirst2Positions(String str) {
		
		if (str.length() <= 2) {
			return str.replaceAll("A","");
		}
		
		String first2Character = str.substring(0, 2);
		String stringMinusFirst2Character = str.substring(2);
		
		return first2Character.replaceAll("A","") + stringMinusFirst2Character;
	}
	
	// Check first two and last two characters are the same of given string
	// A : False, AB : True, AAA : True, ABAB : True, ABCAB : True
	public boolean FirstAndLastTwoCharactersAreSame(String str) {
		
		if (str.length() <= 1) return false;
		if (str.length() <= 1) return true;
		
		String first2char = str.substring(0, 2);
		String last2char = str.substring(str.length()-2);
		
		return first2char.equals(last2char);
		
	}
	
	// Swap last two character of input string
	// AB : BA, ABC : ACB, ASDFGHJK : ASDFGHKJ
	public String swapLast2Characters(String input) {
		
		int length = input.length();
		String inputMinusLast2Chars = input.substring(0, (length-2));
		
		char secondLastChar = input.charAt(length-2);
		char lastChar = input.charAt(length-1);
		
		return inputMinusLast2Chars+lastChar+secondLastChar;
	}

}
