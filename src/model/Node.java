package model;

public class Node {
	private Integer weight;
	private Character character;
	private Node leftChild;
	private Node rightChild;

	// For use in HuffmanEncoder class
	public Node(Integer weight, Node leftChild, Node rightChild) {
		super();
		this.weight = weight;
		this.character = null;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	// For use in HuffmanDecoder class
	public Node(Node leftChild, Node rightChild) {
		super();
		this.weight = null;
		this.character = null;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	// For use in HuffmanEncoder class
	public Node(Integer weight, Character character) {
		super();
		this.weight = weight;
		this.character = character;
	}

	// For use in HuffmanDecoder class
	public Node(Character character) {
		super();
		this.weight = null;
		this.character = character;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return "Node [weight=" + weight + ", character=" + character + ", leftChild=" + leftChild + ", rightChild="
				+ rightChild + "]";
	}

}
