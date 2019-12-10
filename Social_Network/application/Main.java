//package application;
//import javafx.application.Application;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.css.PseudoClass;
//import javafx.geometry.Point2D;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    
//
//    @Override
//    public void start(Stage primaryStage) {
//
//       
//
//        Pane grid = new Pane();
//
//        for (int x = 0 ; x < numColumns; x++) {
//            double gridX = x*(spacing + radius + radius) + spacing ;
//            grid.getChildren().add(new Line(gridX, 0, gridX, numRows*(spacing + radius + radius)));
//        }
//
//        for (int y = 0; y < numRows ; y++) {
//            double gridY = y*(spacing + radius + radius) + spacing ;
//            grid.getChildren().add(new Line(0, gridY, numColumns*(spacing + radius + radius), gridY));
//        }
//
//        for (int x = 0 ; x < numColumns; x++) {
//            for (int y = 0 ;y < numRows ; y++) {
//               );
//            }
//        }
//
//
//        
//
//        BorderPane root = new BorderPane(new ScrollPane(grid));
//        root.setTop(label);
//
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add("grid.css");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
/**
 * 
 */
package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.scene.layout.AnchorPane;

/**
 * @author Siyuan
 *
 */
public class Main extends Application {
	//program size and title
  private static final double WINDOW_WIDTH = 1170;
  private static final double WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Weibo Social Network";
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final double radius = 20 ;
  private final double spacing = 20 ;
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>(); 
  private final ObjectProperty<Point2D> selectedLocation = new SimpleObjectProperty<>();
  
  //Box objects within the program
  public SocialNetwork socialNetwork;
  public VBox statusGraphBox;
  public VBox drawGraphBox;
  public VBox statusBox;
  public VBox leftSide;
  public HBox basicMenuBox;
  public VBox advanceMenuBox;
  public HBox infoBox;
  private int numOfConnectedGraph;
  private Random rand = new Random();
  private AnchorPane anchorPane = new AnchorPane();
  private ArrayList<Person> userList;

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
    addPerson.setMaxWidth(350);
    addPerson.setPrefWidth(300);

    ComboBox<String> personList = new ComboBox<String>();
    personList.setMaxWidth(350);
    personList.setPrefWidth(300);



    HBox addFriendship = new HBox(10);
    ComboBox<String> friendList1 = new ComboBox<String>();
    friendList1.setMaxWidth(170);
    friendList1.setPrefWidth(145);


    ComboBox<String> friendList2 = new ComboBox<String>();
    friendList2.setMaxWidth(170);
    friendList2.setPrefWidth(145);


    addFriendship.getChildren().addAll(friendList1, friendList2);

    HBox removeFriendship = new HBox(10);
    ComboBox<String> removeFriendList1 = new ComboBox<String>();
    removeFriendList1.setMaxWidth(170);
    removeFriendList1.setPrefWidth(145);

    ComboBox<String> removeFriendList2 = new ComboBox<String>();
    removeFriendList2.setMaxWidth(170);
    removeFriendList2.setPrefWidth(145);

    removeFriendship.getChildren().addAll(removeFriendList1, removeFriendList2);

    TextField loadFileName = new TextField();
    loadFileName.setMaxWidth(350);
    loadFileName.setPrefWidth(300);

    if (userList != null) {
      for (int i = 0; i < userList.size(); i++) {
        String userName = userList.get(i).getName();
        personList.getItems().add(userName);
        friendList1.getItems().add(userName);
        friendList2.getItems().add(userName);
        removeFriendList1.getItems().add(userName);
        removeFriendList2.getItems().add(userName);
      }
    }
    
    inputCollections.getChildren().addAll(addPerson, personList, addFriendship, removeFriendship,
        loadFileName);
    

    //combine buttons and inputs
    basicMenuBox.getChildren().addAll(buttonCollections, inputCollections);
    
    //set up buttons
    addUser.setOnAction((event) -> {
      String name = addPerson.getText();
      
      //draw node if input is valid
      if (name != null && !name.isEmpty()) {
        double x = (double) (rand.nextInt(400) + 50);
        double y = (double) (rand.nextInt(400) + 50);
        while (getNameFromCoordinates(x, y) != null) {
          x = (double) (rand.nextInt(400) + 50);
          y = (double) (rand.nextInt(400) + 50);
        }
        drawNode(drawGraphBox, name, x, y);
      } else {
        System.out.println("name is null");
      }
    });

    
    removeUser.setOnAction((event) -> {
      String name = personList.getValue();
      //delete node if input is valid
      if (name != null && !name.isEmpty()) {
        double[] coord = getCoordinatesFromName(name);
        removeNode(drawGraphBox, coord[0], coord[1]);
      } else {
        System.out.println("name is null");
      }
    });

    
    addFriends.setOnAction((event) -> {
      String user1 = friendList1.getValue();
      String user2 = friendList2.getValue();
      //draw friendship if input is valid
      if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
        double[] coord1 = getCoordinatesFromName(user1);
        double[] coord2 = getCoordinatesFromName(user2);
        drawEdge(drawGraphBox, coord1[0], coord1[1], coord2[0], coord2[1]);
      } else {
        System.out.println("name is null");
      }
    });

    removeFriends.setOnAction((event) -> {
      String user1 = removeFriendList1.getValue();
      String user2 = removeFriendList2.getValue();
      //remove friendship if input is valid
      if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
        double[] coord1 = getCoordinatesFromName(user1);
        double[] coord2 = getCoordinatesFromName(user2);
        removeEdge(drawGraphBox, coord1[0], coord1[1], coord2[0], coord2[1]);
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
    exportInp.setMaxWidth(350);
    exportInp.setPrefWidth(300);

    HBox mutualFriend = new HBox(10);
    ComboBox<String> mutualFriend1 = new ComboBox<String>();
//    TextField mutualFriend1 = new TextField();
    mutualFriend1.setMaxWidth(170);
    mutualFriend1.setPrefWidth(145);
    ComboBox<String> mutualFriend2 = new ComboBox<String>();
//    TextField mutualFriend2 = new TextField();
    mutualFriend2.setMaxWidth(170);
    mutualFriend2.setPrefWidth(145);
    mutualFriend.getChildren().addAll(mutualFriend1, mutualFriend2);


    HBox sequence = new HBox(10);
    ComboBox<String> sequence1 = new ComboBox<String>();
//    TextField sequence1 = new TextField();
    sequence1.setMaxWidth(170);
    sequence1.setPrefWidth(145);
    ComboBox<String> sequence2 = new ComboBox<String>();
//    TextField sequence2 = new TextField();
    sequence2.setMaxWidth(170);
    sequence2.setPrefWidth(145);
    sequence.getChildren().addAll(sequence1, sequence2);

    ComboBox<String> searchInp = new ComboBox<String>();
//    TextField searchInp = new TextField();
    searchInp.setMaxWidth(350);
    searchInp.setPrefWidth(300);
    
    if (userList != null) {
      for (int i = 0; i < userList.size(); i++) {
        String userName = userList.get(i).getName();
        mutualFriend1.getItems().add(userName);
        mutualFriend2.getItems().add(userName);
        sequence1.getItems().add(userName);
        sequence2.getItems().add(userName);
        searchInp.getItems().add(userName);
      }
    }
    
    inputCollection.getChildren().addAll(exportInp, mutualFriend, sequence, searchInp);

    HBox buttonAndInput = new HBox(10);
    buttonAndInput.getChildren().addAll(buttonCollection2, inputCollection);

    advanceMenuBox.getChildren().addAll(buttonCollection1, buttonAndInput);
    
    // actions
    
    search.setOnAction((click) -> {
      String user = searchInp.getValue();
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
  private void drawGraph(String name, ArrayList[] adjacentcyList) {
	drawGraphBox = new VBox(20);
    statusGraphBox = new VBox(20);
    infoBox = new HBox(20);
    infoBox.prefWidth(580);
    Label updateNum = updateNumOfConnectedGroups(this.numOfConnectedGraph);
//    gc.setFill(Color.BLUE);
//    gc.setFont(new Font(20));
//    gc.setStroke(Color.BLUE);
//    gc.setLineWidth(2);
//    gc.strokeLine(60, 120, 270, 70);

    Text appTitle = new Text("Weibo Social Network");
    appTitle.setStyle("-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" +
    "-fx-font-size: 20px;\n");
    appTitle.setFill(Color.rgb(207, 18, 22));
    
    drawNode(drawGraphBox, name, drawGraphBox.getWidth()*1/2, drawGraphBox.getHeight()*1/2);
    
    if(adjacentcyList != null) {
    	for(int i = 0; i < adjacentcyList.length; i++) {
    		drawNode(drawGraphBox, adjacentcyList[i].toString(), drawGraphBox.getWidth()*1/2, drawGraphBox.getHeight()*1/2);
    	}
    }
    infoBox.getChildren().addAll(appTitle);
    infoBox.setAlignment(Pos.CENTER);
    
    statusGraphBox.getChildren().addAll(updateNum, infoBox, drawGraphBox);
//    statusGraphBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n" + 
//        "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");
    
//    anchorPane.getChildren().addAll(statusGraphBox);
//    Button test1 = new Button("Mike");
//    test1.setStyle("-fx-background-color: transparent;");
//    AnchorPane.setTopAnchor(test1, 210.0);
//    AnchorPane.setLeftAnchor(test1, 60.0);
//    anchorPane.getChildren().addAll(test1);
    

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

//    Text textContent = new Text(name + "");
    Button textContent = new Button(name);
    stackPane.getChildren().add(textContent);
    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    return stackPane.snapshot(parameters, null);
  }

  private void drawNode(VBox graph, String name, double x, double y) {
	  selectedCircle.addListener((obs, oldSelection, newSelection) -> {
	        if (oldSelection != null) {
	            oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
	        }
	        if (newSelection != null) {
	            newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
	        }
	  });
	  graph.getChildren().addAll(createCircle(x, y));
	    
	  Label label = new Label();
	  label.textProperty().bind(Bindings.createStringBinding(() -> {
		  Point2D loc = selectedLocation.get();
	      if (loc == null) {
	          return "" ;
	      }
	      return String.format("Location: [%.0f, %.0f]", loc.getX(), loc.getY());
	  }, selectedLocation));
	    
  }

  private void removeNode(VBox drawGraphBox2, double x, double y) {

  }

  private void drawEdge(VBox drawGraphBox2, double x1, double y1, double x2, double y2) {

  }

  private void removeEdge(VBox drawGraphBox2, double x1, double y1, double x2, double y2) {

  }

  private String getNameFromCoordinates(double x1, double x2) {
    return null;

  }

  private double[] getCoordinatesFromName(String name) {
    return null;
  }

  private void setSelectedUser(String person) {

  }
  
  private Circle createCircle(double x, double y) {
      Circle circle = new Circle();
      circle.getStyleClass().add("intersection");
      circle.setCenterX(x * (10 + 20 + 20) + 10);
      circle.setCenterY(y * (10 + 20 + 20) + 10);
      circle.setRadius(20);

      circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
          selectedCircle.set(circle);
          selectedLocation.set(new Point2D(x, y));
      });

      return circle ;
  }

  
  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub
    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(10,10,10,10));
    drawGraph("qwer",null);
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
    scene.getStylesheets().add("grid.css");
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
