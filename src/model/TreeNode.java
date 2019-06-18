package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreeNode extends Circle {

	private TreeNode parent;
	private TreeNode left;
	private TreeNode right;	
	private Line leftLine;
	private Line rightLine;
	private Text text;
	
	public TreeNode(char c, int x, int y, int radius) {
		super(x, y, radius);
		setFill(Color.web("0x5AC3E1"));
		setStroke(Color.BLACK);
		text = new Text(getX(), getY(), String.valueOf(c));
		left = null;
		right = null;
		parent = null;
	}

	public TreeNode(TreeNode left, TreeNode right) {
		super(15);
		this.left = left;
		this.right = right;
		this.parent = null;
		left.parent = this;
		right.parent = this;
		leftLine = new Line();
		rightLine = new Line();
		setFill(Color.web("0xAAB4B7"));
		setStroke(Color.BLACK);
		text = new Text(getX(), getY(), "");
	}

	public void setX(double value) {
		setCenterX(value);
	}

	public void setY(double value) {
		setCenterY(value);
	}

	public double getX() {
		return getCenterX();
	}

	public double getY() {
		return getCenterY();
	}

	public Text getText() {
		return text;
	}

	public TreeNode getParentNode() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}
	
	public Line getLeftLine() {
		return leftLine;
	}
	
	public Line getRightLine() {
		return rightLine;
	}

}
