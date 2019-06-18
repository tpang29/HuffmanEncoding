package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import model.Huffman;
import model.HuffmanEncoder;
import model.TreeBuilder;
import model.TreeNode;
import view.CenterView;
import view.TopView;

public class Controller {

	private BorderPane root;
	private TopView topView;
	private CenterView centerView;

	public Controller(BorderPane root) {
		this.root = root;
		topView = new TopView();
		centerView = new CenterView();
		this.root.setTop(topView.getHBox());
		this.root.setCenter(centerView.getSPane());
		setCallbacks();
	}

	public BorderPane getRoot() {
		return root;
	}

	private void setCallbacks() {
		topView.getEncodeButton().setOnAction(buildTree());
		topView.getDecodeButton().setOnAction(displayMessage());
		topView.getClearButton().setOnAction(clear());
	}
	
	private EventHandler<ActionEvent> clear() {
		return event -> {
			topView.getUserInput().clear();
			centerView.getPane().getChildren().clear();
			centerView.getSPane().setContent(null);	
		};
	}
	
	private EventHandler<ActionEvent> displayMessage() {
		return event -> {
			String decodedMessage = Huffman.decode(topView.getUserInput().getText());
			topView.getUserInput().setText(decodedMessage);
			centerView.getSPane().setContent(null);
		};
	}

	private EventHandler<ActionEvent> buildTree() {
		return event -> {
			centerView.getPane().getChildren().clear();		
			String encodedString = Huffman.encode(topView.getUserInput().getText());
			int lastZero = HuffmanEncoder.getTree((topView.getUserInput().getText())).indexOfSeparator();
			int index = 0;
			TreeBuilder builder = new TreeBuilder();

			while (index < lastZero) {
				if (encodedString.charAt(index) == '1') {
					TreeNode node = builder.buildLeafNode(encodedString.charAt(++index));
					centerView.getPane().getChildren().addAll(node, node.getText());
				} else if (encodedString.charAt(index) == '0') {
					TreeNode parent = builder.buildInternalNode();
					if (!(parent.getLeft() == null || parent.getRight() == null)) {
						centerView.getPane().getChildren().add(0, parent.getLeftLine());
						centerView.getPane().getChildren().add(0, parent.getRightLine());
						centerView.getPane().getChildren().addAll(parent, parent.getText());
					}
				}
				index++;
				centerView.getSPane().setContent(centerView.getPane());	
				topView.getUserInput().setText(encodedString);
			}
		};
	}

}
