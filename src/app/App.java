package app;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

//	public static void main(String[] args) {
//		launch(args);
//	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Controller controller = new Controller(root);
		Scene scene = new Scene(controller.getRoot(), 1200, 500);
		primaryStage.setTitle("Huffman Encoder/Decoder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
