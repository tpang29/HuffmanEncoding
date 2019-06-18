package model;

public class HuffmanTree {
	private Node root;
	private String headerFile;
	
	private static final String DELIMITER = "#";
	
	public HuffmanTree(Node root, String headerFile) {
		super();
		this.root = root;
		this.headerFile = headerFile;
	}

	public HuffmanTree(Node root) {
		super();
		this.root = root;
		this.headerFile = "";
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	public String generateHeaderFile() {
		postOrderTraversal(root);
		headerFile += "0" + root.getWeight() + DELIMITER;
		return headerFile;
	}
	
	public int indexOfSeparator() {
		this.generateHeaderFile();
		return headerFile.indexOf(String.valueOf(root.getWeight()) + DELIMITER) - 1;
	}
	
	private void postOrderTraversal(Node localRoot) {
		if (localRoot.getCharacter() != null) { // add '1' before the character at each leaf node
			headerFile += "1" + localRoot.getCharacter();
//			System.out.println(localRoot.getCharacter());
		} else {
			// continue to traverse down subtree
			postOrderTraversal(localRoot.getLeftChild());
			postOrderTraversal(localRoot.getRightChild());
			headerFile += "0";	
		}
	}

	@Override
	public String toString() {
		return "HuffmanTree [root=" + root + "]";
	}

}
