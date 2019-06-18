package model;

/*
 * This class employs two public static methods.
 * 
 * encode(String data) serves to return the encoded message which
 * 		contains a header file followed by the bit string
 * 
 * getTree(String data) returns a Huffman Tree needed for the GUI 
 * 		component of the project
 */

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanEncoder {

	public static String encode(String data) {
		return headerAndString(data);
	}

	public static HuffmanTree getTree(String data) {
		return new HuffmanTree(mergeItems(data));
	}

	private static String headerAndString(String data) {
		HuffmanTree tree = new HuffmanTree(mergeItems(data));
		return tree.generateHeaderFile() + binaryString(data);
	}

	public static String binaryString(String data) {
		HuffmanTree tree = getTree(data);
		String bitPath = "";
		String binaryString = "";
		for (int i = 0; i < data.length(); i++) {
			binaryString = postOrderTraversal(tree.getRoot(), data.charAt(i), binaryString, bitPath);

		}
		
		if (binaryString.length() == 0) { // if tree is a single node representing 1 character
			for (int i = 0; i < tree.getRoot().getWeight(); i++) {
				binaryString += "0";
			}
		}
		return binaryString;
	}

	private static String postOrderTraversal(Node localRoot, char character, String binaryString, String bitPath) {
		if (localRoot.getCharacter() != null) {
			if (localRoot.getCharacter() == character) {
				binaryString += bitPath;
				// System.out.println(character + " " + bitPath);
			}
		} else {
			bitPath += "0";
			binaryString = postOrderTraversal(localRoot.getLeftChild(), character, binaryString, bitPath);
			bitPath = (bitPath.length() >= 2) ? bitPath.substring(0, bitPath.length() - 1) : "";
			bitPath += "1";
			binaryString = postOrderTraversal(localRoot.getRightChild(), character, binaryString, bitPath);

		}
		return binaryString;
	}

	private static Node mergeItems(String data) {
		Queue<Node> queue = new PriorityQueue<Node>(new NodeComparator());
		populateQueue(queue, data);
		while (queue.size() != 1) {
			Node first = queue.remove();
			Node second = queue.remove();
			Node newNode = new Node(first.getWeight() + second.getWeight(), first, second);
			queue.add(newNode);
		}

		return queue.remove();
	}

	private static Queue<Node> populateQueue(Queue<Node> queue, String data) {
		CharacterMap characterMap = new CharacterMap(data);
		Iterator<Entry<Character, Integer>> iterator = characterMap.getMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Character, Integer> next = iterator.next();
			Node node = new Node(next.getValue(), next.getKey());
			queue.add(node);
		}
		return queue;
	}

}
