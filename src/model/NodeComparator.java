package model;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		int weightVal = o1.getWeight().compareTo(o2.getWeight());
		int charVal = 0;

		if (weightVal == 0) {
			if (isLeafNode(o1) && isLeafNode(o2)) { // if both are leaves, compare by ASCII value
				charVal = o1.getCharacter().compareTo(o2.getCharacter());
				return charVal;
			} else if (!(isLeafNode(o1) && isLeafNode(o2))) { // if o1 is leaf, o1 has precedence over o2
				return (isLeafNode(o1) ? -1 : 1);
			} else {
				return -1; // both are internal nodes, left (o1) has precedence over right (o2)
			}
		} else {
			return weightVal;
		}
	}

	private boolean isLeafNode(Node node) {
		return ((node.getLeftChild() == null) && (node.getRightChild() == null));
	}

}
