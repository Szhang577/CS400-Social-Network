/**
 * 
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * @author Siyuan
 *
 */
public class Main extends Application {

	private static final double WINDOW_WIDTH = 1200;
	private static final double WINDOW_HEIGHT = 800;
	private static final String APP_TITLE = "Weibo Social Network";

	public SocialNetwork socialNetwork;
	public Canvas graphCanvas;
	private GraphicsContext gc;
	public VBox statusGraphBox;
	public HBox basicMenuBox;
	public VBox advanceMenuBox;
	private int numOfConnectedGraph;
	private Random rand = new Random();

	private void setUpgraphBox() {

	}

	private void setUpstatusBox() {

	}

	private void setUpBasicMenuBox() {
		basicMenuBox = new HBox(10);

		VBox buttonCollections = new VBox(20);
		Button addUser = new Button("Add User");
		Button removeUser = new Button("Remove User");
		Button addFriends = new Button("Add Friendship");
		Button removeFriends = new Button("Remove Friendship");
		Button loadFile = new Button("Load File");

		buttonCollections.getChildren().addAll(addUser, removeUser, addFriends, removeFriends, loadFile);

		VBox inputCollections = new VBox(20);
		TextField addPerson = new TextField();
		addPerson.setMaxWidth(410);

		TextField removePerson = new TextField();
		removePerson.setMaxWidth(410);

		HBox addFriendship = new HBox(10);
		TextField addFriend1 = new TextField();
		addFriend1.setMaxWidth(200);
		TextField addFriend2 = new TextField();
		addFriend2.setMaxWidth(200);
		addFriendship.getChildren().addAll(addFriend1, addFriend2);

		HBox removeFriendship = new HBox(10);
		TextField removeFriend1 = new TextField();
		removeFriend1.setMaxWidth(200);
		TextField removeFriend2 = new TextField();
		removeFriend2.setMaxWidth(200);
		removeFriendship.getChildren().addAll(removeFriend1, removeFriend2);

		TextField loadFileName = new TextField();
		loadFileName.setMaxWidth(410);

		inputCollections.getChildren().addAll(addPerson, removePerson, addFriendship, removeFriendship, loadFileName);

		basicMenuBox.getChildren().addAll(buttonCollections, inputCollections);

		addUser.setOnAction((event) -> {
			String name = addPerson.getText();
			if (name != null && !name.isEmpty()) {
				gc = graphCanvas.getGraphicsContext2D();
				double x = (double) (rand.nextInt(600) + 50);
				double y = (double) (rand.nextInt(600) + 50);
				while (getNameFromCoordinates(x, y) != null) {
					x = (double) (rand.nextInt(600) + 50);
					y = (double) (rand.nextInt(600) + 50);
				}
				drawNode(gc, name, x, y);
			} else {
				System.out.println("name is null");
			}
		});

		removeUser.setOnAction((event) -> {
			String name = removePerson.getText();
			if (name != null && !name.isEmpty()) {
				gc = graphCanvas.getGraphicsContext2D();
				double[] coord = getCoordinatesFromName(name);
				removeNode(gc, coord[0], coord[1]);
			} else {
				System.out.println("name is null");
			}
		});

		addFriends.setOnAction((event) -> {
			String user1 = addFriend1.getText();
			String user2 = addFriend2.getText();
			if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
				gc = graphCanvas.getGraphicsContext2D();
				double[] coord1 = getCoordinatesFromName(user1);
				double[] coord2 = getCoordinatesFromName(user2);
				drawEdge(gc, coord1[0], coord1[1], coord2[0], coord2[1]);
			} else {
				System.out.println("name is null");
			}
		});

		removeFriends.setOnAction((event) -> {
			String user1 = removeFriend1.getText();
			String user2 = removeFriend2.getText();
			if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
				gc = graphCanvas.getGraphicsContext2D();
				double[] coord1 = getCoordinatesFromName(user1);
				double[] coord2 = getCoordinatesFromName(user2);
				removeEdge(gc, coord1[0], coord1[1], coord2[0], coord2[1]);
			} else {
				System.out.println("name is null");
			}
		});

		loadFile.setOnAction((event) -> {
			String fileName = loadFileName.getText();
			if (fileName != null && !fileName.isEmpty()) {
				File file = new File(fileName);
				socialNetwork.loadFromFile(file);
			} else {
				System.out.println("name is null");
			}
		});
	}

	private void setUpAdvanceMenuBox() {

	}

	private Label updateNumOfConnectedGroups(int numOfGroups) {
		String numUpdate = "Number of Connected Groups is " + numOfGroups;
		Label numLabel = new Label(numUpdate);
		return numLabel;

	}

	private void drawGraph(GraphicsContext graph) {
		statusGraphBox = new VBox(20);
		Label updateNum = updateNumOfConnectedGroups(this.numOfConnectedGraph);
		gc.setFill(Color.BLUE);
		gc.setFont(new Font(30));
		gc.fillText("Weibo Social Network", 200, 600);
		// Draw a line
		// Lines use the stroke color
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(2);
		gc.strokeLine(40, 100, 250, 50);
		gc.drawImage(createdTextedCircle("Mike"), 40 - 15, 100 - 15);
		gc.drawImage(createdTextedCircle("Abby"), 250 - 15, 50 - 15);
		gc.drawImage(createdTextedCircle("Eric"), 70, 200);
		gc.drawImage(createdTextedCircle("Kevin"), 200, 300);
		gc.drawImage(createdTextedCircle("Tim"), 230, 260);
		gc.drawImage(createdTextedCircle("Jordon"), 180, 400);
		statusGraphBox.getChildren().addAll(updateNum, graphCanvas);

	}

	private WritableImage createdTextedCircle(String name) {
		StackPane stackPane = new StackPane();
		stackPane.setPrefSize(40, 40);

		Circle circle = new Circle(40 / 2.0);
		circle.setStroke(Color.YELLOW);
		circle.setFill(Color.WHITE);
		circle.setStrokeWidth(2);
		stackPane.getChildren().add(circle);

		Text textContent = new Text(name + "");
		stackPane.getChildren().add(textContent);
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		return stackPane.snapshot(parameters, null);
	}

	private void drawNode(GraphicsContext graph, String name, double x, double y) {

	}

	private void removeNode(GraphicsContext graph, double x, double y) {

	}

	private void drawEdge(GraphicsContext graph, double x1, double y1, double x2, double y2) {

	}

	private void removeEdge(GraphicsContext graph, double x1, double y1, double x2, double y2) {

	}

	private String getNameFromCoordinates(double x1, double x2) {
		return null;

	}

	private double[] getCoordinatesFromName(String name) {
		return null;
	}

	private void setSelectedUser(String person) {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane pane = new BorderPane();
		graphCanvas = new Canvas(750, 700);
		gc = graphCanvas.getGraphicsContext2D();
		drawGraph(gc);
		setUpBasicMenuBox();
		pane.setRight(basicMenuBox);
		pane.setLeft(statusGraphBox);
		Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
