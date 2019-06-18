package model;

import java.util.Stack;

public class TreeBuilder {
	
	private int hOffset = 0;
	private Stack<TreeNode> stack;

	private static final int RADIUS = 15;
	private static final int H_OFFSET = 60;
	private static final int V_OFFSET = 60;
	
	public TreeBuilder() {
		stack = new Stack<>();
	}

	public TreeNode buildLeafNode(char c) {
		hOffset += H_OFFSET;
		TreeNode newNode = new TreeNode(c, hOffset, V_OFFSET, RADIUS);
		stack.add(newNode);
		return newNode;
	}
	
	public TreeNode buildInternalNode() {
		TreeNode parent = null;
		if (stack.size() >= 2) {
			TreeNode right = stack.pop();
			TreeNode left = stack.pop();
			parent = new TreeNode(left, right);
			stack.add(parent);
			bindLines(parent);
			adjustNodes(parent);
			double xAvg = (left.getX() + right.getX()) / 2;
			parent.setCenterX(xAvg);
			parent.setCenterY(V_OFFSET);
		} 
		return parent;
	}
	
	private void bindLines(TreeNode parent) {
		parent.getLeftLine().startXProperty().bind(parent.centerXProperty());
		parent.getLeftLine().startYProperty().bind(parent.centerYProperty());
		parent.getRightLine().startXProperty().bind(parent.centerXProperty());
		parent.getRightLine().startYProperty().bind(parent.centerYProperty());
		parent.getLeftLine().endXProperty().bind(parent.getLeft().centerXProperty());
		parent.getRightLine().endXProperty().bind(parent.getRight().centerXProperty());
		parent.getLeftLine().endYProperty().bind(parent.getLeft().centerYProperty());
		parent.getRightLine().endYProperty().bind(parent.getRight().centerYProperty());
	}

	private void adjustNodes(TreeNode node) {
		if (node.getLeft() != null) {
			double newLeftY = node.getLeft().getY() + V_OFFSET;
			node.getLeft().setY(newLeftY);
			node.getLeft().getText().setY(newLeftY);
			adjustNodes(node.getLeft());
		}
		
		if (node.getRight() != null) {
			double newRightY = node.getRight().getY() + V_OFFSET;
			node.getRight().setY(newRightY);
			node.getRight().getText().setY(newRightY);
			adjustNodes(node.getRight());
		}

	}

	
}
