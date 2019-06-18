package consoleDemo;

import model.Huffman;

/*
 * CSCI 651 - Programming Assignment 1
 * HuffmanEncoding.java
 * Purpose(s):
 * 
 * (1)	Use the static method encode() from the HuffmanEncoder class to return a HuffmanTree object
 * 			and pass this reference to another tree reference variable
 * 
 * (2)	Using the new HuffmanTree reference variable, call the generateHeaderFile() method to verify
 * 			that the header file is properly generated by travsersing the tree
 * 
 * (3)	Use the static method decode() from the HuffmanDecoder class to return a message from a string
 * 			which contains the header file concatenated with the binary string 
 *
 * @author Thomas Pang | 2019-06-07
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
