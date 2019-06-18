package view;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class CenterView {

	private Pane pane;
	private ScrollPane sPane;

	public CenterView() {
		buildNodes();
	}

	public Pane getPane() {
		return pane;
	}
	
	public ScrollPane getSPane() {
		return sPane;
	}

	private void buildNodes() {
		buildPane();
		buildScrollPane();
	}

	private void buildPane() {
		pane = new Pane();
	}
	
	private void buildScrollPane() {
		sPane = new ScrollPane();
		sPane.setContent(pane);
		sPane.setPadding(new Insets(10));
	}

}
