package consoleDemo;

import model.Huffman;

/*
 * CSCI 651 - Programming Assignment 1
 * Purposes:
 * 
 * (1)	Use the static method encode() from the Huffman class and return
 *	a string of the encoded message (header + delimiter + bit string)
 * 
 * (2)	Use the static method decode() from the Huffman class which takes in 
 *	a string that is the encrypted message and returns the original message 
 *
 * @author Thomas Pang | 2019-06
 */

public class Demo {

	public static void main(String[] args) {
		test("go go gophers");
		test("streets are stone stars are not");
		test("aaaaabbbbcccdde");
		test("I would like green eggs and ham.");
		test("Hello World!");	
	}
	
	private static void test(String message) {
		String encodedMessage = Huffman.encode(message);
		System.out.println(encodedMessage);
		String decodedMessage = Huffman.decode(encodedMessage);
		System.out.println(decodedMessage);
	}
}
