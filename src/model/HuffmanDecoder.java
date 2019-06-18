package model;

/*
 * This class employs a single public static method decode() which 
 * takes in a single String of data which represents the header file
 * and binary string and returns the original message. If no delimiter
 * is present, this will return an error message "Incorrect header"
 */

import java.util.Stack;

public class HuffmanDecoder {
	private static final String DELIMETER = "#";

	public static String decode(String data) {
		String[] tokens = parseData(data);
		if (tokens.length != 2) {
			return "Incorrect header";
		}
		String header = tokens[0];
		String binaryString = tokens[1];
		HuffmanTree tree = parseHuffmanTree(header);
		Node localRoot = tree.getRoot();

		int index = 0;
		String message = "";
		char character;

		if (tree.getRoot().getLeftChild() == null && tree.getRoot().getRightChild() == null) { // a single character tree
			for (int i = 0; i < binaryString.length(); i++) {
				message += tree.getRoot().getCharacter();
			}
		} else { // multi-character tree
			while (index < binaryString.length()) {
				localRoot = tree.getRoot();
				while (localRoot.getCharacter() == null) {
					character = binaryString.charAt(index++);
					localRoot = (character == '1') ? localRoot.getRightChild() : localRoot.getLeftChild();
				}
				message += localRoot.getCharacter();
			}
		}

		return message;
	}

	private static HuffmanTree parseHuffmanTree(String header) {
		Stack<Node> stack = new Stack<>();

		int index = 0;
		boolean flag = true;
		Node newNode = null;
		Node leftChild = null;
		Node rightChild = null;

		while (flag) {
			if (header.charAt(index) == '1') {
				newNode = new Node(header.charAt(++index));
				stack.push(newNode);
			} else if (header.charAt(index) == '0' && stack.size() >= 2) {
				rightChild = stack.pop();
				leftChild = stack.pop();
				newNode = new Node(leftChild, rightChild);
				stack.push(newNode);
			} else {
				flag = false;
			}
			index++;
		}

		return new HuffmanTree(stack.pop());
	}

	private static String[] parseData(String data) {
		return data.split(DELIMETER);
	}

}
