package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TopView {

	private TextField userInput;
	private Button encodeButton;
	private Button decodeButton;
	private Button clearButton;
	private HBox hBox;

	public TopView() {
		buildNodes();
	}

	public TextField getUserInput() {
		return userInput;
	}

	public Button getEncodeButton() {
		return encodeButton;
	}

	public Button getDecodeButton() {
		return decodeButton;
	}
	
	public Button getClearButton() {
		return clearButton;
	}

	public HBox getHBox() {
		return hBox;
	}

	private void buildNodes() {
		buildTextField();
		buildButtons();
		buildHBox();
		populateHBox();
	}

	private void buildTextField() {
		userInput = new TextField();
		HBox.setHgrow(userInput, Priority.ALWAYS);
		userInput.setPromptText("Type your sentence or encoded message");
	}

	private void buildButtons() {
		encodeButton = new Button("Encode");
		decodeButton = new Button("Decode");
		clearButton = new Button("Clear");
	}

	private void buildHBox() {
		hBox = new HBox(10);
		hBox.setPadding(new Insets(5));
		hBox.setAlignment(Pos.CENTER);
	}
	
	private void populateHBox() {
		hBox.getChildren().addAll(userInput, encodeButton, decodeButton, clearButton);
	}

}
