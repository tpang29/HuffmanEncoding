package model;

/*
 * This class serves to create a list of unique key:value pairs that
 * store a single character of data and the frequency of that character.
 * 
 * The value of each pair will represent the weight of each leaf node
 * in a Huffman Tree.
 */

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class CharacterMap {
	private Map<Character, Integer> map;
	private String data;

	public CharacterMap(String data) {
		map = new TreeMap<Character, Integer>();
		this.data = data;
		generateCharacterMap();
	}

	public Map<Character, Integer> getMap() {
		return map;
	}

	public String getData() {
		return data;
	}

	public void printCharacterMapData() {
		Iterator<Entry<Character, Integer>> iterator = map.entrySet().iterator();

		int totalCharacters = 0;

		while (iterator.hasNext()) {
			Entry<Character, Integer> next = iterator.next();
			totalCharacters += next.getValue();
			System.out.print(next.getValue() + " ");
			System.out.println(next.getKey());
		}

		System.out.println("Number of unique characters:\t" + map.size());
		System.out.println("Total characters in string:\t" + totalCharacters);
	}

	private void generateCharacterMap() {
		map = new TreeMap<>();
		for (int i = 0; i < data.length(); i++) {
			Character key = data.charAt(i);
			if (!map.containsKey(key)) {
				map.put(key, 1);
			} else {
				map.replace(key, map.get(key).intValue() + 1);
			}
		}
	}
}
