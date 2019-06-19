# CSCI-651 Algorithm Concepts: Programming Assignment 1

## Members: Tom Pang, Jay Shah, Efosa Uwa-Omede

## Programming Assignment:

In this programming assignment, the goal was to write a program that takes in a sentence and generates the Huffman tree specified by the header. A program was designed and implemented that widely uses Huffman coding algorithm, which is used in JPEG compression as well as in MP3 audio compression. Concepts that were learned from the class, namely priority queues, stacks, and trees, were utilized to design the file compression program and the file decompression program (similar to *zip* and *unzip*).

### Languages & Technologies used:

- JavaFX
- IntelliJ IDEA

## Coding - "App.java":

The code demonstrated below is a runnable application for which the header, inputted by user, can be encoded or decoded. When the users inputs a header such as "go go gopher" and that header is encoded, a Huffman tree of their encoded input is displayed on the application. 

When the users inputs a such as "1g1o01s1 01e1h01p1r0000013#0001101000110100011110110111001111100" and the header is decoded,  a text box will appear and display the corresponding text to their decoded input to the user , such as "go go gopher".

```java
package app;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

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
```

##### Figures of the Application:

![1560817780105](C:\Users\eomed\AppData\Roaming\Typora\typora-user-images\1560817780105.png)

**Figure 1:** The application that allows the user to encode or decode their inputted message.

![1560818564546](C:\Users\eomed\AppData\Roaming\Typora\typora-user-images\1560818564546.png)

**Figure 2:** Example of encoded info and displayed corresponding Huffman tree

![1560818789423](C:\Users\eomed\AppData\Roaming\Typora\typora-user-images\1560818789423.png)

**Figure 3:** Example of decoded info and displayed text box containing corresponding text

## Coding - "Demo.java":

The code below is another runnable class which serves as a test file to determine and display that the String inputs are able to be encoded and decoded.  The user could then use any of the results into the "App.java" to familiarize themselves with how the application works.

```java
package consoleDemo;

import model.Huffman;

public class Demo {

	public static void main(String[] args) {
		test("go go gophers");
		test("streets are stone stars are not");
		test("aaaaabbbbcccdde");
		test("I would like green eggs and ham.");
		test("Hello World!");	
	}
	
	private static void test(String message) {
		String encodedMessage = Huffman.encode(message);
		System.out.println(encodedMessage);
		String decodedMessage = Huffman.decode(encodedMessage);
		System.out.println(decodedMessage);
	}
}
```

![1560907988881](C:\Users\eomed\AppData\Roaming\Typora\typora-user-images\1560907988881.png)

**Figure 4:** A screenshot of the result from running "Demo.java"

## Code - "Controller.java":

The user interface of an FXML application is defined inside an FXML document and all the logic to handle input events are written inside a controller class.

```java
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

```

## Coding - "model" Package:

The following pieces of code that will be shown will be a part of the "model" package. The purpose of this package were to:

1. Use the `static method encode()` from the `HuffmanEncoder` class to return a `HuffmanTree` object and pass this reference to another tree reference variable
2. Using the new `HuffmanTree` reference variable, call the `generateHeaderFile()` method to verify that the header file is properly generated by traversing the tree
3. Use the static method decode() from the `HuffmanDecoder` class to return a message from a string which contains the header file concatenated with the binary string 

## Coding - "CharacterMap.java":

This class serves to create a list of unique key:value pairs that store a single character of data and the frequency of that character. The value of each pair will represent the weight of each leaf node in a Huffman Tree.

```java
package model;

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
```

## Coding - "Huffman.java":

A test method to verify if the `HuffmanDecoder` and `HuffmanEncoder` work properly.

```java
package model;

public class Huffman {
   
   public static String decode(String data) {
      return HuffmanDecoder.decode(data);
   }
   
   public static String encode(String data) {
      return HuffmanEncoder.encode(data);
   }
}
```

## Coding - "HuffmanDecoder.java":

This class employs a single `public static method decode()` which  takes in a single String of data which represents the header file and binary string and returns the original message. If no delimiter is present, this will return an error message `"Incorrect header"` .
```java
package model;

import java.util.Stack;

public class HuffmanDecoder {
   private static final String DELIMETER = "#";

   public static String decode(String data) {
      String[] tokens = parseData(data);
      if (tokens.length != 2) {
         return "Incorrect header";
      }
      String header = tokens[0];
      String binaryString = tokens[1];
      HuffmanTree tree = parseHuffmanTree(header);
      Node localRoot = tree.getRoot();

      int index = 0;
      String message = "";
      char character;

      if (tree.getRoot().getLeftChild() == null && tree.getRoot().getRightChild() == null) { // a single character tree
         for (int i = 0; i < binaryString.length(); i++) {
            message += tree.getRoot().getCharacter();
         }
      } else { // multi-character tree
         while (index < binaryString.length()) {
            localRoot = tree.getRoot();
            while (localRoot.getCharacter() == null) {
               character = binaryString.charAt(index++);
               localRoot = (character == '1') ? localRoot.getRightChild() : localRoot.getLeftChild();
            }
            message += localRoot.getCharacter();
         }
      }

      return message;
   }

   private static HuffmanTree parseHuffmanTree(String header) {
      Stack<Node> stack = new Stack<>();

      int index = 0;
      boolean flag = true;
      Node newNode = null;
      Node leftChild = null;
      Node rightChild = null;

      while (flag) {
         if (header.charAt(index) == '1') {
            newNode = new Node(header.charAt(++index));
            stack.push(newNode);
         } else if (header.charAt(index) == '0' && stack.size() >= 2) {
            rightChild = stack.pop();
            leftChild = stack.pop();
            newNode = new Node(leftChild, rightChild);
            stack.push(newNode);
         } else {
            flag = false;
         }
         index++;
      }

      return new HuffmanTree(stack.pop());
   }

   private static String[] parseData(String data) {
      return data.split(DELIMETER);
   }

}
```

## Coding - "HuffmanEncoder.java":

This class employs two public static methods:
- `encode(String data)` serves to return the encoded message which contains a header file followed by the bit string
- `getTree(String data)` returns a Huffman Tree needed for the GUI  component of the project 

```java
package model;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanEncoder {

   public static String encode(String data) {
      return headerAndString(data);
   }

   public static HuffmanTree getTree(String data) {
      return new HuffmanTree(mergeItems(data));
   }

   private static String headerAndString(String data) {
      HuffmanTree tree = new HuffmanTree(mergeItems(data));
      return tree.generateHeaderFile() + binaryString(data);
   }

   public static String binaryString(String data) {
      HuffmanTree tree = getTree(data);
      String bitPath = "";
      String binaryString = "";
      for (int i = 0; i < data.length(); i++) {
         binaryString = postOrderTraversal(tree.getRoot(), data.charAt(i), binaryString, bitPath);

      }
      
      if (binaryString.length() == 0) { // if tree is a single node representing 1 character
         for (int i = 0; i < tree.getRoot().getWeight(); i++) {
            binaryString += "0";
         }
      }
      return binaryString;
   }

   private static String postOrderTraversal(Node localRoot, char character, String binaryString, String bitPath) {
      if (localRoot.getCharacter() != null) {
         if (localRoot.getCharacter() == character) {
            binaryString += bitPath;
            // System.out.println(character + " " + bitPath);
         }
      } else {
         bitPath += "0";
         binaryString = postOrderTraversal(localRoot.getLeftChild(), character, binaryString, bitPath);
         bitPath = (bitPath.length() >= 2) ? bitPath.substring(0, bitPath.length() - 1) : "";
         bitPath += "1";
         binaryString = postOrderTraversal(localRoot.getRightChild(), character, binaryString, bitPath);

      }
      return binaryString;
   }

   private static Node mergeItems(String data) {
      Queue<Node> queue = new PriorityQueue<Node>(new NodeComparator());
      populateQueue(queue, data);
      while (queue.size() != 1) {
         Node first = queue.remove();
         Node second = queue.remove();
         Node newNode = new Node(first.getWeight() + second.getWeight(), first, second);
         queue.add(newNode);
      }

      return queue.remove();
   }

   private static Queue<Node> populateQueue(Queue<Node> queue, String data) {
      CharacterMap characterMap = new CharacterMap(data);
      Iterator<Entry<Character, Integer>> iterator = characterMap.getMap().entrySet().iterator();
      while (iterator.hasNext()) {
         Entry<Character, Integer> next = iterator.next();
         Node node = new Node(next.getValue(), next.getKey());
         queue.add(node);
      }
      return queue;
   }

}
```

## Coding - "HuffmanTree.java":

This class creates and returns the Huffman Tree using the node and the inputted header.

```java
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
//       System.out.println(localRoot.getCharacter());
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
```

## Coding - "Node.java":

This class creates a node that will be used in both the `HuffmanEncoder` class and the `HuffmanDecoder` class. Each node has weight, a character, a left child, and a right child.

```java
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
```

## Coding - "NodeComparator.java":

This class compares to nodes to each other by using the methodology of priority queues. 

```java
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
```

## Coding - "TreeBuilder.java":

This class utilizes the "stack" methodology that is required for the creation of the Huffman Trees.

```java
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
```

## Coding - "TreeNode.java":

This class creates the visual aesthetics of the nodes that are visible in the application after the user encodes their inputted information. Also, the parent-child relationship between the nodes. 

```java
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
```

## Coding - "view" Package:

The following pieces of code that will be shown will be a part of the "view" package.

## Coding - "CenterView.java":

In this class, `Insets` stores the inside offsets for the four sides of the rectangular area. `ScrollPane` is a scrollable component used to display a large content in a limited space. It contains horizontal and vertical scroll bars.

Layout `Pane` is a container which is used for flexible and dynamic arrangements of UI controls within a scene graph of a JavaFX application. As a window is resized, the layout pane automatically repositions and resizes the nodes it contains.

```java
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
```

## Coding - "TopView.java":

According to "geeksforgeeks", Pos class contains values which state the horizontal and vertical positioning or alignment. When the Button is pressed in the application, an Action Event is sent. This Action Event can be managed by an EventHandler. Buttons can also respond to mouse events by implementing an EventHandler to process the MouseEvent.



According to "geeksforgeeks", TextField class is a component that allows the user to enter a line of unformatted text, it does not allow multi-line input it only allows the user to enter a single line of text. The text can then be used as per requirement. HBox lays out its children in form of horizontal columns. If the HBox has a border and/or padding set, then the contents will be layed out within those insets. 

```java
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
```

## Coding - ".classpath":

The class path for the application.

```java
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
   <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-9">
      <attributes>
         <attribute name="module" value="true"/>
      </attributes>
   </classpathentry>
   <classpathentry kind="src" path="src"/>
   <classpathentry kind="output" path="bin"/>
</classpath>
```

## Coding - ".project":

The GUI for the application.

```java
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
   <name>HuffmanEncoding</name>
   <comment></comment>
   <projects>
   </projects>
   <buildSpec>
      <buildCommand>
         <name>org.eclipse.jdt.core.javabuilder</name>
         <arguments>
         </arguments>
      </buildCommand>
   </buildSpec>
   <natures>
      <nature>org.eclipse.jdt.core.javanature</nature>
   </natures>
</projectDescription>
```