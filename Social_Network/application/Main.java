/**
 * 
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import java.util.Optional;
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
  public VBox statusBox;
  public VBox leftSide;
  public HBox basicMenuBox;
  public VBox advanceMenuBox;
  private int numOfConnectedGraph;
  private Random rand = new Random();

  private void setUpgraphBox() {

  }

  private void setUpstatusBox() {
		statusBox = new VBox(10);
		Label text = new Label("Status Message:\n ");
		Label message = new Label("No Instruction Yet");
		if(getCurrentInstruction()!= null) {
			message.setText(getCurrentInstruction());
		}
		statusBox.getChildren().addAll(text, message);
		
	}
	private String getCurrentInstruction() {
		return null;
	}

  private void setUpBasicMenuBox() {
    basicMenuBox = new HBox(10);

    VBox buttonCollections = new VBox(20);
    Button addUser = new Button("Add User");
    Button removeUser = new Button("Remove User");
    Button addFriends = new Button("Add Friendship");
    Button removeFriends = new Button("Remove Friendship");
    Button loadFile = new Button("Load File");

    buttonCollections.getChildren().addAll(addUser, removeUser, addFriends, removeFriends,
        loadFile);

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

    inputCollections.getChildren().addAll(addPerson, removePerson, addFriendship, removeFriendship,
        loadFileName);

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

  /**
   * @author Ruokai
   *
   */
  private void setUpAdvanceMenuBox() {
    advanceMenuBox = new VBox(20);

    HBox buttonCollection1 = new HBox(10);
    Button clear = new Button("Clear");
    Button undo = new Button("Undo");
    Button exit = new Button("Exit");
    
    Alert a = new Alert(AlertType.NONE); 
    EventHandler<ActionEvent> eventClear = new EventHandler<ActionEvent>() { 
    	public void handle(ActionEvent e) 
    	{ 
    		// set alert type 
    		a.setAlertType(AlertType.CONFIRMATION); 

    		// set content text 
    		a.setContentText("Do you want to clear the network?"); 

    		// show the dialog 
    		a.show(); 
    	} 
    }; 
    
    EventHandler<ActionEvent> eventUndo = new EventHandler<ActionEvent>() { 
    	public void handle(ActionEvent e) 
    	{ 
    		// set alert type 
    		a.setAlertType(AlertType.CONFIRMATION); 

    		// set content text 
    		a.setContentText("Do you want to undo last instruction?"); 

    		// show the dialog 
    		a.show(); 
    	} 
    }; 
    
    EventHandler<ActionEvent> eventExit = new EventHandler<ActionEvent>() { 
    	public void handle(ActionEvent e) 
    	{ 
    		TextInputDialog dialog = new TextInputDialog("myNetwork");
    		dialog.setContentText("If you wish to save your network\nPlease enter file name:");
    		dialog.setTitle("Exit Dialog");
    		
    		ButtonType save = new ButtonType("Save",ButtonData.OK_DONE);
    		ButtonType exit_without_save = new ButtonType("Exit without Save",ButtonData.OK_DONE);
    		
    		dialog.getDialogPane().getButtonTypes().setAll(save, exit_without_save);
    		
    		Optional<String> result = dialog.showAndWait();
    		
    		dialog.setResultConverter(dialogButton -> {
    		    if (result.get() != null) {
        		    return null;//...user chose save
    		    }
    		    else {
        		    return null; // ... user chose exit without save
        		}

    		});
    		
    		
    	} 
    }; 
    
    clear.setOnAction(eventClear);
    undo.setOnAction(eventUndo);
    exit.setOnAction(eventExit);
    
    HBox buttonCollection2 = new HBox(10);
    Button export = new Button("Export");
    TextField exportInp = new TextField();
    exportInp.setMaxWidth(410);

    HBox buttonCollection3 = new HBox(10);
    Button mutual = new Button("Mutual Friend");
    TextField mutualFriend1 = new TextField();
    mutualFriend1.setMaxWidth(200);
    TextField mutualFriend2 = new TextField();
    mutualFriend2.setMaxWidth(200);

    HBox buttonCollection4 = new HBox(10);
    Button sequence_friends = new Button("Sequence of Friends");
    TextField sequence1 = new TextField();
    sequence1.setMaxWidth(200);
    TextField sequence2 = new TextField();
    sequence2.setMaxWidth(200);

    HBox buttonCollection5 = new HBox(10);
    Button search = new Button("Search");
    TextField searchInp = new TextField();
    searchInp.setMaxWidth(410);

    buttonCollection1.getChildren().addAll(clear, undo, exit);
    buttonCollection2.getChildren().addAll(export, exportInp);
    buttonCollection3.getChildren().addAll(mutual, mutualFriend1, mutualFriend2);
    buttonCollection4.getChildren().addAll(sequence_friends,sequence1,sequence2);
    buttonCollection5.getChildren().addAll(search, searchInp);


    advanceMenuBox.getChildren().addAll(buttonCollection1, buttonCollection2, buttonCollection3,
        buttonCollection4, buttonCollection5);
    
    // actions
    
    search.setOnAction((click) -> {
      String user = searchInp.getText();
      if(user != null) {
        setSelectedUser(user);
      }else {
        System.out.println("name is null");
      }
    });
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

    VBox rightBox = new VBox(30);
    setUpBasicMenuBox();
    setUpAdvanceMenuBox();
    rightBox.getChildren().addAll(basicMenuBox, advanceMenuBox);

    pane.setRight(rightBox);
    setUpstatusBox();
    leftSide = new VBox(20);
	  leftSide.getChildren().addAll(statusGraphBox,statusBox );
	  pane.setLeft(leftSide);
    Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

}
