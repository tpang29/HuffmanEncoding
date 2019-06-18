package model;

public class Huffman {
	
	public static String decode(String data) {
		return HuffmanDecoder.decode(data);
	}
	
	public static String encode(String data) {
		return HuffmanEncoder.encode(data);
	}
}
