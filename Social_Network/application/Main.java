/**
 * 
 */
package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Region;
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
	//program size and title
  private static final double WINDOW_WIDTH = 1170;
  private static final double WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Weibo Social Network";
  
  //Box objects within the program
  public SocialNetwork socialNetwork;
  public Canvas graphCanvas;
  private GraphicsContext gc;
  public VBox statusGraphBox;
  public VBox statusBox;
  public VBox leftSide;
  public HBox basicMenuBox;
  public VBox advanceMenuBox;
  public HBox infoBox;
  private int numOfConnectedGraph;
  private Random rand = new Random();

  /**
   * @author Kaiwen Shen
   *
   */
  //Status box: show user the last instruction
  private void setUpstatusBox() {
		statusBox = new VBox(10);
		statusBox.setPrefHeight(150);
		Label text = new Label("Status Message:\n ");
		Label message = new Label("No Instruction Yet");
		
		//If instruction is found, set content to it
		if(getCurrentInstruction()!= null) {
			message.setText(getCurrentInstruction());
		}
		
		statusBox.getChildren().addAll(text, message);
		//Box border
		statusBox.setStyle("-fx-border-color: blue;\n" + "-fx-border-widths: 2;\n" + 
		"-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");
  }
  
  //Helper method to get the last instruction
  private String getCurrentInstruction() {
		return null;
  }
  
  //BasicMenu: add,remove users and friendship, load file
  private void setUpBasicMenuBox() {
    basicMenuBox = new HBox(10);
    
    //create button objects
    VBox buttonCollections = new VBox(35);
    Button addUser = new Button("Add User");
    Button removeUser = new Button("Remove User");
    Button addFriends = new Button("Add Friendship");
    Button removeFriends = new Button("Remove Friendship");
    Button loadFile = new Button("Load File");
    
   
    buttonCollections.getChildren().addAll(addUser, removeUser, addFriends, removeFriends,
        loadFile);
    
    
    //create text input objects
    VBox inputCollections = new VBox(35);
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

    //combine buttons and inputs
    basicMenuBox.getChildren().addAll(buttonCollections, inputCollections);
    
    //set up buttons
    addUser.setOnAction((event) -> {
      String name = addPerson.getText();
      
      //draw node if input is valid
      if (name != null && !name.isEmpty()) {
        gc = graphCanvas.getGraphicsContext2D();
        double x = (double) (rand.nextInt(400) + 50);
        double y = (double) (rand.nextInt(400) + 50);
        while (getNameFromCoordinates(x, y) != null) {
          x = (double) (rand.nextInt(400) + 50);
          y = (double) (rand.nextInt(400) + 50);
        }
        drawNode(gc, name, x, y);
      } else {
        System.out.println("name is null");
      }
    });

    
    removeUser.setOnAction((event) -> {
      String name = removePerson.getText();
      //delete node if input is valid
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
      //draw friendship if input is valid
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
      //remove friendship if input is valid
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
      //establish current network if file is valid
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
  /*AdvanceMenu: clear network, undo last instruction, exit program with pop-ups
  	export current network, find mutual friend, find friend sequences, search for a user*/
  				
  private void setUpAdvanceMenuBox() {
    advanceMenuBox = new VBox(35);
    //set up clear undo and exit
    HBox buttonCollection1 = new HBox(30);
    Button clear = new Button("Clear");
    Button undo = new Button("Undo");
    Button exit = new Button("Exit");
    
    //clear alert pop-up
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
    
    //undo alert pop-up
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
    
    //exit dialog pop-up
    EventHandler<ActionEvent> eventExit = new EventHandler<ActionEvent>() { 
    	public void handle(ActionEvent e) 
    	{ 
    		TextInputDialog dialog = new TextInputDialog("myNetwork");
    		dialog.setContentText("If you wish to save your network\nPlease enter file name:");
    		dialog.setTitle("Exit Dialog");
    		
    		//custom buttons
    		ButtonType save = new ButtonType("Save",ButtonData.OK_DONE);
    		ButtonType exit_without_save = new ButtonType("Exit without Save",ButtonData.OK_DONE);
    		
    		//add save, exit without save and cancel
    		dialog.getDialogPane().getButtonTypes().setAll(save, exit_without_save);
    		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
    		Optional<String> result = dialog.showAndWait();
    		
    		//if input is valid ... else...
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
    
    buttonCollection1.getChildren().addAll(clear, undo, exit);
    
    //set up buttons
    VBox buttonCollection2 = new VBox(35);
    Button export = new Button("Export");
    Button mutual = new Button("Mutual Friend");
    Button sequence_friends = new Button("Friends Sequences");
    Button search = new Button("Search");
    
    buttonCollection2.getChildren().addAll(export, mutual, sequence_friends, search);
    
    //set up text inputs
    VBox inputCollection = new VBox(35);
    TextField exportInp = new TextField();
    exportInp.setMaxWidth(410);

    HBox mutualFriend = new HBox(10);
    TextField mutualFriend1 = new TextField();
    mutualFriend1.setMaxWidth(200);
    TextField mutualFriend2 = new TextField();
    mutualFriend2.setMaxWidth(200);
    mutualFriend.getChildren().addAll(mutualFriend1, mutualFriend2);


    HBox sequence = new HBox(10);
    TextField sequence1 = new TextField();
    sequence1.setMaxWidth(200);
    TextField sequence2 = new TextField();
    sequence2.setMaxWidth(200);
    sequence.getChildren().addAll(sequence1, sequence2);

    TextField searchInp = new TextField();
    searchInp.setMaxWidth(410);
    
    inputCollection.getChildren().addAll(exportInp, mutualFriend, sequence, searchInp);

    HBox buttonAndInput = new HBox(10);
    buttonAndInput.getChildren().addAll(buttonCollection2, inputCollection);

    advanceMenuBox.getChildren().addAll(buttonCollection1, buttonAndInput);
    
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

  //helper method for reading the num of grps
  private Label updateNumOfConnectedGroups(int numOfGroups) {
    String numUpdate = "Number of Connected Groups is " + numOfGroups;
    Label numLabel = new Label(numUpdate);
    return numLabel;

  }

  // method for drawing the network
  private void drawGraph(GraphicsContext graph) {
    statusGraphBox = new VBox(20);
    infoBox = new HBox(20);
    infoBox.prefWidth(580);
    Label updateNum = updateNumOfConnectedGroups(this.numOfConnectedGraph);
    gc.setFill(Color.BLUE);
    gc.setFont(new Font(20));
    // gc.fillText("Weibo Social Network", 200, 25);
    // Draw a line
    // Lines use the stroke color
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(2);
    gc.strokeLine(60, 120, 270, 70);
    gc.drawImage(createdTextedCircle("Mike"), 40, 100);
    gc.drawImage(createdTextedCircle("Abby"), 250, 50);
    gc.drawImage(createdTextedCircle("Eric"), 70, 200);
    gc.drawImage(createdTextedCircle("Kevin"), 200, 300);
    gc.drawImage(createdTextedCircle("Tim"), 230, 260);
    gc.drawImage(createdTextedCircle("Jordon"), 180, 400);
    Text appTitle = new Text("Weibo Social Network");
    appTitle.setStyle("-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" +
    "-fx-font-size: 20px;\n");
    appTitle.setFill(Color.rgb(207, 18, 22));
    
    infoBox.getChildren().addAll(appTitle);
    infoBox.setAlignment(Pos.CENTER);
    
    statusGraphBox.getChildren().addAll(updateNum, infoBox, graphCanvas);
    statusGraphBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n" + 
        "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");

  }

  //helper method for drawing circles
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
    pane.setPadding(new Insets(10,10,10,10));
    graphCanvas = new Canvas(580, 500);
    gc = graphCanvas.getGraphicsContext2D();
    drawGraph(gc);

    VBox rightBox = new VBox(35);
    setUpBasicMenuBox();
    setUpAdvanceMenuBox();
    rightBox.getChildren().addAll(basicMenuBox, advanceMenuBox);
    rightBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n" + 
        "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");

    pane.setRight(rightBox);
    setUpstatusBox();
    

    pane.setLeft(statusGraphBox);
//    leftSide = new VBox(20);
//	  leftSide.getChildren().addAll(statusGraphBox, statusBox );
//	  pane.setLeft(leftSide);

    pane.setBottom(statusBox);
    
    
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
