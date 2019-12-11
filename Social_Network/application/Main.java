// package application;
// import javafx.application.Application;
// import javafx.beans.binding.Bindings;
// import javafx.beans.property.ObjectProperty;
// import javafx.beans.property.SimpleObjectProperty;
// import javafx.css.PseudoClass;
// import javafx.geometry.Point2D;
// import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.Pane;
// import javafx.scene.shape.Circle;
// import javafx.scene.shape.Line;
// import javafx.stage.Stage;
//
// public class Main extends Application {
//
//
//
// @Override
// public void start(Stage primaryStage) {
//
//
//
// Pane grid = new Pane();
//
// for (int x = 0 ; x < numColumns; x++) {
// double gridX = x*(spacing + radius + radius) + spacing ;
// grid.getChildren().add(new Line(gridX, 0, gridX, numRows*(spacing + radius + radius)));
// }
//
// for (int y = 0; y < numRows ; y++) {
// double gridY = y*(spacing + radius + radius) + spacing ;
// grid.getChildren().add(new Line(0, gridY, numColumns*(spacing + radius + radius), gridY));
// }
//
// for (int x = 0 ; x < numColumns; x++) {
// for (int y = 0 ;y < numRows ; y++) {
// );
// }
// }
//
//
//
//
// BorderPane root = new BorderPane(new ScrollPane(grid));
// root.setTop(label);
//
//
// Scene scene = new Scene(root);
// scene.getStylesheets().add("grid.css");
// primaryStage.setScene(scene);
// primaryStage.show();
// }
//
//
//
// public static void main(String[] args) {
// launch(args);
// }
// }
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
  // program size and title
  private static final double WINDOW_WIDTH = 1170;
  private static final double WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Weibo Social Network";
  private static final PseudoClass SELECTED_P_C = PseudoClass.getPseudoClass("selected");
  private final double radius = 30;
  private final double spacing = 20;
  private final ObjectProperty<Circle> selectedCircle = new SimpleObjectProperty<>();
  private final ObjectProperty<Point2D> selectedLocation = new SimpleObjectProperty<>();

  // Box objects within the program
  public BorderPane pane = new BorderPane();
  public SocialNetwork socialNetwork = new SocialNetwork();
  public VBox statusGraphBox;
  public BorderPane drawGraphPane;
  public VBox statusBox;
  public VBox leftSide;
  public HBox basicMenuBox;
  public VBox advanceMenuBox;
  public HBox infoBox;
  private int numOfConnectedGraph;
  private Random rand = new Random();
  private List<Person> userList;
  private String statusMessage;
  private String centralUser;
  private String headUser;
  private String tailUser;

  ComboBox<String> personList = new ComboBox<String>();
  ComboBox<String> removeFriendList2 = new ComboBox<String>();
  ComboBox<String> friendList1 = new ComboBox<String>();
  ComboBox<String> friendList2 = new ComboBox<String>();
  ComboBox<String> removeFriendList1 = new ComboBox<String>();
  ComboBox<String> mutualFriend1 = new ComboBox<String>();
  ComboBox<String> mutualFriend2 = new ComboBox<String>();
  ComboBox<String> sequence1 = new ComboBox<String>();
  ComboBox<String> sequence2 = new ComboBox<String>();
  ComboBox<String> searchInp = new ComboBox<String>();

  /**
   * @author Kaiwen Shen
   *
   */
  // Status box: show user the last instruction
  private void setUpstatusBox() {
    statusBox = new VBox(10);
    statusBox.setPrefHeight(150);
    Label text = new Label("Status Message:\n ");
    Label message = new Label("No Instruction Yet");

    // If instruction is found, set content to it
    if (getCurrentInstruction() != null) {
      message.setText(getCurrentInstruction());
    }

    statusBox.getChildren().addAll(text, message);
    // Box border
    statusBox.setStyle("-fx-border-color: blue;\n" + "-fx-border-widths: 2;\n"
        + "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");
  }

  // Helper method to get the last instruction
  private String getCurrentInstruction() {
    return this.statusMessage;
    
  }
  private void setCurrentInstruction(String status) {
    this.statusMessage = status;
}

  // BasicMenu: add,remove users and friendship, load file
  private void setUpBasicMenuBox() {
    basicMenuBox = new HBox(10);

    // create button objects
    VBox buttonCollections = new VBox(35);
    Button addUser = new Button("Add User");
    Button removeUser = new Button("Remove User");
    Button addFriends = new Button("Add Friendship");
    Button removeFriends = new Button("Remove Friendship");
    Button loadFile = new Button("Load File");

    buttonCollections.getChildren().addAll(addUser, removeUser, addFriends, removeFriends,
        loadFile);

    // create text input objects
    VBox inputCollections = new VBox(35);
    TextField addPerson = new TextField();
    addPerson.setMaxWidth(350);
    addPerson.setPrefWidth(300);

    personList.setMaxWidth(350);
    personList.setPrefWidth(300);

    HBox addFriendship = new HBox(10);

    friendList1.setMaxWidth(170);
    friendList1.setPrefWidth(145);

    friendList2.setMaxWidth(170);
    friendList2.setPrefWidth(145);

    addFriendship.getChildren().addAll(friendList1, friendList2);

    HBox removeFriendship = new HBox(10);

    removeFriendList1.setMaxWidth(170);
    removeFriendList1.setPrefWidth(145);

    removeFriendList2.setMaxWidth(170);
    removeFriendList2.setPrefWidth(145);

    removeFriendship.getChildren().addAll(removeFriendList1, removeFriendList2);

    TextField loadFileName = new TextField();
    loadFileName.setMaxWidth(350);
    loadFileName.setPrefWidth(300);

    // if (userList != null) {
    // for (int i = 0; i < userList.size(); i++) {
    // String userName = userList.get(i).getName();
    // personList.getItems().add(userName);
    // friendList1.getItems().add(userName);
    // friendList2.getItems().add(userName);
    // removeFriendList1.getItems().add(userName);
    // removeFriendList2.getItems().add(userName);
    // }
    // }

    inputCollections.getChildren().addAll(addPerson, personList, addFriendship, removeFriendship,
        loadFileName);

    // combine buttons and inputs
    basicMenuBox.getChildren().addAll(buttonCollections, inputCollections);

    // set up buttons
    addUser.setOnAction((event) -> {


      String name = addPerson.getText();
      try {
        socialNetwork.addUser(name);

      } catch (DuplicatePersonException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      updateAddList();

      ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(name);
      centralUser = name;
      drawGraph(name, friends);

      try {
        pane.setLeft(statusGraphBox);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    removeUser.setOnAction((event) -> {
      String name = personList.getValue();
      System.out.println(name);

      // delete node if input is valid
      if (name != null && !name.isEmpty()) {
        if (name.equals(centralUser)) {
          if (!name.equals(socialNetwork.graph.getAllNodes().get(0).getName())) {
            centralUser = socialNetwork.graph.getAllNodes().get(0).getName();
          } else if (socialNetwork.graph.getAllNodes().size() > 1) {
            centralUser = socialNetwork.graph.getAllNodes().get(1).getName();
          } else {
            centralUser = null;
          }
        }
        
        try {
          socialNetwork.removeUser(name);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          statusMessage = e.getMessage();
        }
        updateRemoveList();
        ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
        drawGraph(centralUser, friends);

        try {
          pane.setLeft(statusGraphBox);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          statusMessage = e.getMessage();
        }
      }
    });

    addFriends.setOnAction((event) -> {
      String user1 = friendList1.getValue();
      String user2 = friendList2.getValue();
      // draw friendship if input is valid
      try {
        socialNetwork.addFriends(user1, user2);
      } catch (PersonNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (DuplicateEdgesException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (DuplicatePersonException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
      drawGraph(centralUser, friends);
      try {
        pane.setLeft(statusGraphBox);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    removeFriends.setOnAction((event) -> {
      String user1 = removeFriendList1.getValue();
      String user2 = removeFriendList2.getValue();
      // remove friendship if input is valid
      if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
        try {
          socialNetwork.removeFriends(user1, user2);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          statusMessage = e.getMessage();
        }

        ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
        drawGraph(centralUser, friends);

        try {
          pane.setLeft(statusGraphBox);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          statusMessage = e.getMessage();
        }
      }
    });

    loadFile.setOnAction((event) -> {
      String fileName = loadFileName.getText();
      // establish current network if file is valid
      if (fileName != null && !fileName.isEmpty()) {
        File file = new File(fileName);

        try {
          socialNetwork.loadFromFile(file);
          if (socialNetwork.centralSet) {
            centralUser = socialNetwork.centralUser;
          }
          ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
          drawGraph(centralUser, friends);
          updateAddList();
          updateRemoveList();
          try {
            pane.setLeft(statusGraphBox);
          } catch (Exception e) {
            // TODO Auto-generated catch block
            statusMessage = e.getMessage();
          }
        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (DuplicatePersonException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (PersonNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (DuplicateEdgesException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (NoEdgeExistsException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } else {
        System.out.println("name is null");
      }
    });

  }

  /**
     * @author Ruokai
     *
     */
    /*
     * AdvanceMenu: clear network, undo last instruction, exit program with pop-ups export current
     * network, find mutual friend, find friend sequences, search for a user
     */
  
    private void setUpAdvanceMenuBox() {
      advanceMenuBox = new VBox(35);
      // set up clear undo and exit
      HBox buttonCollection1 = new HBox(30);
      Button clear = new Button("Clear");
      Button exitWS = new Button("Exit without Save");
      Button save = new Button("Save");
  
      // clear alert pop-up
      Alert a = new Alert(AlertType.NONE);
      EventHandler<ActionEvent> eventClear = new EventHandler<ActionEvent>() {
       public void handle(ActionEvent e) {
        // set alert type
        a.setAlertType(AlertType.CONFIRMATION);
  
        // set content text
        a.setContentText("Do you want to clear the network?");
  
        // show the dialog
  //      a.show();
  
        Optional<ButtonType> option = a.showAndWait();
  
        if (option.get() == null) {
         return;
        } else if (option.get() == ButtonType.OK) {
         socialNetwork = new SocialNetwork();
         setUpBasicMenuBox();
         setUpAdvanceMenuBox();
         updateRemoveList();
         VBox rightBox = new VBox(35);
         rightBox.getChildren().addAll(basicMenuBox, advanceMenuBox);
         rightBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n"
             + "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");
         pane.setRight(rightBox);
         drawGraph(null,null);
        
         try {
          pane.setLeft(statusGraphBox);
         } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
         }
         updateRemoveList();
        } else if (option.get() == ButtonType.CANCEL) {
         return;
        } else {
         return;
        }
       }
      };
  
      // undo alert pop-up
      EventHandler<ActionEvent> eventUndo = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          // set alert type
          a.setAlertType(AlertType.CONFIRMATION);
  
          // set content text
          a.setContentText("Do you want to exit withou save");
          
          Optional<ButtonType> option = a.showAndWait();
          
          if (option.get() == null) {
            return;
          }else if (option.get() == ButtonType.OK) {
            System.exit(0);
          }
            // show the dialog
          a.show();
        }
      };
  
      // exit dialog pop-up
      EventHandler<ActionEvent> eventExit = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
          TextInputDialog dialog = new TextInputDialog("myNetwork");
          dialog.setContentText("If you wish to save your network\nPlease enter file name:");
          dialog.setTitle("Exit Dialog");
  
          // custom buttons
          ButtonType save = new ButtonType("Save", ButtonData.OK_DONE);
            
          // add save, exit without save and cancel
          dialog.getDialogPane().getButtonTypes().setAll(save);
          dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
          Optional<String> result = dialog.showAndWait();
          // if input is valid ... else...

            if (result.get() != null) {
              System.out.println("exist");
              File saveFile = new File(result.get());
            
              try {
             
                socialNetwork.saveToFile(saveFile);
                System.out.println("1");
                System.exit(0);
              } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
              
             
  
          
  
        }
      };
  
      clear.setOnAction(eventClear);
      exitWS.setOnAction(eventUndo);
      save.setOnAction(eventExit);
  
      buttonCollection1.getChildren().addAll(clear, exitWS, save);
  
      // set up buttons
      VBox buttonCollection2 = new VBox(35);
      Button export = new Button("Export");
      Button mutual = new Button("Mutual Friend");
      Button sequence_friends = new Button("Shortest Friendship");
      Button search = new Button("Search");
  
      buttonCollection2.getChildren().addAll(export, mutual, sequence_friends, search);
  
      // set up text inputs
      VBox inputCollection = new VBox(35);
      TextField exportInp = new TextField();
      exportInp.setMaxWidth(350);
      exportInp.setPrefWidth(300);
  
      HBox mutualFriend = new HBox(10);
  
      // TextField mutualFriend1 = new TextField();
      mutualFriend1.setMaxWidth(170);
      mutualFriend1.setPrefWidth(145);
  
      // TextField mutualFriend2 = new TextField();
      mutualFriend2.setMaxWidth(170);
      mutualFriend2.setPrefWidth(145);
      mutualFriend.getChildren().addAll(mutualFriend1, mutualFriend2);
  
      HBox sequence = new HBox(10);
  
      // TextField sequence1 = new TextField();
      sequence1.setMaxWidth(170);
      sequence1.setPrefWidth(145);
  
      // TextField sequence2 = new TextField();
      sequence2.setMaxWidth(170);
      sequence2.setPrefWidth(145);
      sequence.getChildren().addAll(sequence1, sequence2);
  
      // TextField searchInp = new TextField();
      searchInp.setMaxWidth(350);
      searchInp.setPrefWidth(300);
  
  
      inputCollection.getChildren().addAll(exportInp, mutualFriend, sequence, searchInp);
  
      HBox buttonAndInput = new HBox(10);
      buttonAndInput.getChildren().addAll(buttonCollection2, inputCollection);
  
      advanceMenuBox.getChildren().addAll(buttonCollection1, buttonAndInput);
  
      // actions
  
      search.setOnAction((click) -> {
        String user = searchInp.getValue();
        if (user != null) {
          setSelectedUser(user);
        } 
      });
      
      mutual.setOnAction((event) -> {
        String user1 = mutualFriend1.getValue();
        String user2 = mutualFriend2.getValue();
        if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
  //        socialNetwork.getMutualFriends(user1, user2);
          drawMutualFriend(user1, user2);
        }
        
        try {
          pane.setLeft(statusGraphBox);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });
      
      sequence_friends.setOnAction((click) -> {
        String user1 = sequence1.getValue();
        String user2 = sequence2.getValue();
        String pathString = "";
        if (user1 != null && user2 != null) {
            List<Person> path;
            try {
              path = socialNetwork.getShortestPath(user1, user2);
              pathString = pathString.concat(path.get(0).getName());
              for (int i = 1; i < path.size(); ++i ) {
                  pathString = pathString.concat(" -> " + path.get(i).getName());
              }
              pathString = pathString.concat(" (shortest path)");
            } catch (DuplicatePersonException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
//            pathString = pathString.concat(path.get(0).getName());
//            for (int i = 1; i < path.size(); ++i ) {
//                pathString = pathString.concat(" -> " + path.get(i).getName());
//            }
            setCurrentInstruction(pathString);
            setUpstatusBox();

//            drawSrtPath(user1, user2);
            
            try {
              pane.setBottom(statusBox);
              pane.setLeft(statusGraphBox);
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            
        } else {
            System.out.println("name is null");
        }
    });
    }

  // helper method for reading the num of grps
  private Label updateNumOfConnectedGroups(int numOfGroups) {
    String numUpdate = "Number of Connected Groups is " + numOfGroups;
    Label numLabel = new Label(numUpdate);
    return numLabel;

  }

  // method for drawing the network
  private void drawGraph(String name, ArrayList<Person> adjacencyList) {
    drawGraphPane = new BorderPane();
    drawGraphPane.prefWidth(580);
    drawGraphPane.prefHeight(500);
    statusGraphBox = new VBox(20);
    infoBox = new HBox(20);
    infoBox.prefWidth(580);
    Label updateNum = updateNumOfConnectedGroups(socialNetwork.getConnectedComponents());
    // gc.setFill(Color.BLUE);
    // gc.setFont(new Font(20));
    // gc.setStroke(Color.BLUE);
    // gc.setLineWidth(2);
    // gc.strokeLine(60, 120, 270, 70);

    Text appTitle = new Text("Weibo Social Network");
    appTitle.setStyle(
        "-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" + "-fx-font-size: 20px;\n");
    appTitle.setFill(Color.rgb(207, 18, 22));

    System.out.println(name);
    if (name != null) {
      drawNode(drawGraphPane, name, 284, 250);
    }

    // if(adjacentcyList != null) {
    // for(int i = 0; i < adjacentcyList.length; i++) {
    // drawNode(drawGraphBox, adjacentcyList[i].toString(), drawGraphBox.getWidth()*1/2,
    // drawGraphBox.getHeight()*1/2);
    // }
    // }
    int[][] coors = new int[90][2];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 9; j++) {
        coors[j * 10 + i][0] = i;
        coors[j * 10 + i][1] = j;
      }
    }
    coors[44] = null;
    if (adjacencyList != null && adjacencyList.size() != 0) {
      for (int i = 0; i < adjacencyList.size(); i++) {
        Random rand = new Random();
        int randNum = rand.nextInt(90);
        if (coors[randNum] != null) {
          drawNode(drawGraphPane, adjacencyList.get(i).getName(), 66 * coors[randNum][0] + 20,
              10 + 60 * coors[randNum][1]);
          drawEdge(drawGraphPane, 284, 250, 66 * coors[randNum][0] + 20,
              10 + 60 * coors[randNum][1]);
          coors[randNum] = null;
        }
      }
    }
    infoBox.getChildren().addAll(appTitle);
    infoBox.setAlignment(Pos.CENTER);

    statusGraphBox.getChildren().addAll(updateNum, infoBox, drawGraphPane);
    // statusGraphBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n" +
    // "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");

    // anchorPane.getChildren().addAll(statusGraphBox);
    // Button test1 = new Button("Mike");
    // test1.setStyle("-fx-background-color: transparent;");
    // AnchorPane.setTopAnchor(test1, 210.0);
    // AnchorPane.setLeftAnchor(test1, 60.0);
    // anchorPane.getChildren().addAll(test1);

  }

  private void drawMutualFriend(String name1, String name2) {
    drawGraphPane = new BorderPane();
    List<Person> mutualFriends = new ArrayList<Person>();
    try {
      mutualFriends = socialNetwork.getMutualFriends(name1, name2);
    } catch (DuplicatePersonException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    statusGraphBox = new VBox(20);
    infoBox = new HBox(20);
    infoBox.prefWidth(580);
    Label updateNum = updateNumOfConnectedGroups(socialNetwork.getConnectedComponents());

    Text appTitle = new Text("Weibo Social Network");
    appTitle.setStyle(
        "-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" + "-fx-font-size: 20px;\n");
    appTitle.setFill(Color.rgb(207, 18, 22));
    
    headUser = name1;
    tailUser = name2;
    String temp = centralUser;
    centralUser = null;

    System.out.println(mutualFriends.size());
    if (name1 != null) {
      drawNode(drawGraphPane, name1, 30, 250);
    }
    if (name2 != null) {
      drawNode(drawGraphPane, name2, 550, 250);
    }
    int[][] coors = new int[90][2];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 9; j++) {
        coors[j * 10 + i][0] = i;
        coors[j * 10 + i][1] = j;
      }
    }
    coors[40] = null;
    coors[49] = null;
    if (mutualFriends != null && mutualFriends.size() != 0) {
      for (int i = 0; i < mutualFriends.size(); i++) {
        Random rand = new Random();
        int randNum = rand.nextInt(90);
        if (coors[randNum] != null) {
          drawNode(drawGraphPane, mutualFriends.get(i).getName(), 66 * coors[randNum][0] + 20,
              10 + 60 * coors[randNum][1]);
          drawEdge(drawGraphPane, 30, 250, 66 * coors[randNum][0] + 20,
              10 + 60 * coors[randNum][1]);
          drawEdge(drawGraphPane, 550, 250, 66 * coors[randNum][0] + 20,
              10 + 60 * coors[randNum][1]);
          coors[randNum] = null;
        }
      }
    }

    infoBox.getChildren().addAll(appTitle);
    infoBox.setAlignment(Pos.CENTER);

    statusGraphBox.getChildren().addAll(updateNum, infoBox, drawGraphPane);
    headUser = null;
    tailUser = null;
    centralUser = temp;
  }
  
//  private void drawSrtPath(String name1, String name2) {
//    List<Person> SrtPath = new ArrayList<Person>();
//    try {
//      SrtPath = socialNetwork.getShortestPath(name1, name2);
//    } catch (DuplicatePersonException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//
//    if (name1 != null) {
//        drawNode(drawGraphPane, name1, 30, 30);
//    }
//    if (name2 != null) {
//        drawNode(drawGraphPane, name2, 550, 470);
//    }
//
//    if (SrtPath != null && SrtPath.size() != 0) {
//        drawEdge(drawGraphPane, 30, 30, (520 / SrtPath.size()) * (1) + 30, (440 / SrtPath.size()) * (1) + 30);
//        drawEdge(drawGraphPane, 550, 470, (520 / SrtPath.size()) * (SrtPath.size()) + 30,
//                (440 / SrtPath.size()) * (SrtPath.size()) + 30);
//        for (int i = 0; i < SrtPath.size(); i++) {
//            drawNode(drawGraphPane, SrtPath.get(i).getName(), (520 / SrtPath.size()) * (i + 1) + 30,
//                    (440 / SrtPath.size()) * (i + 1) + 30);
//            drawEdge(drawGraphPane, (520 / SrtPath.size()) * (i + 1) + 30, (440 / SrtPath.size()) * (i + 1) + 30,
//                    (520 / SrtPath.size()) * (i + 2) + 30, (440 / SrtPath.size()) * (i + 2) + 30);
//            
//        }
//    }
//}
  
  // helper method for drawing circles
  private void drawNode(BorderPane pane, String name, double x, double y) {

    StackPane stack = new StackPane();
    stack = createCircle(name, x, y);
    stack.setLayoutX(x);
    stack.setLayoutY(y);
    // stack.relocate(x,y);

    pane.getChildren().addAll(stack);
    // selectedCircle.addListener((obs, oldSelection, newSelection) -> {
    // if (oldSelection != null) {
    // oldSelection.pseudoClassStateChanged(SELECTED_P_C, false);
    // }
    // if (newSelection != null) {
    // newSelection.pseudoClassStateChanged(SELECTED_P_C, true);
    // System.out.println(name);
    // setSelectedUser(name);
    // }
    // });

    // Label label = new Label();
    // label.textProperty().bind(Bindings.createStringBinding(() -> {
    // Point2D loc = selectedLocation.get();
    // if (loc == null) {
    // return "";
    // }
    // return String.format("Location: [%.0f, %.0f]", loc.getX(), loc.getY());
    // }, selectedLocation));

  }

  private void drawEdge(BorderPane drawGraphPane, double x1, double y1, double x2, double y2) {
    Line line = new Line();
    line.setStartX(x1);
    line.setStartY(y1);
    line.setEndX(x2);
    line.setEndY(y2);
    drawGraphPane.getChildren().addAll(line);
  }

  private void removeNode(BorderPane drawGraphPane, double x, double y) {

  }

  private void removeEdge(BorderPane drawGraphPane, double x1, double y1, double x2, double y2) {

  }

  private void updateAddList() {
    userList = socialNetwork.graph.getAllNodes();

    if (userList != null) {
      for (int i = 0; i < userList.size(); i++) {
        String userName = userList.get(i).getName();
        if (!personList.getItems().contains(userName)) {
          personList.getItems().add(userName);
          friendList1.getItems().add(userName);
          friendList2.getItems().add(userName);
          removeFriendList1.getItems().add(userName);
          removeFriendList2.getItems().add(userName);
          mutualFriend1.getItems().add(userName);
          mutualFriend2.getItems().add(userName);
          sequence1.getItems().add(userName);
          sequence2.getItems().add(userName);
          searchInp.getItems().add(userName);

        }
      }
    }
  }


  private void compareAndDelete(ComboBox<String> currentList, List<Person> nextList) {
    if (nextList.size() != 0) {
      if (currentList != null) {
        for (int i = 0; i < currentList.getItems().size(); i++) {
          String userName = currentList.getItems().get(i);
          Person person = socialNetwork.graph.getNode(userName);
          if (!nextList.contains(person)) {
            currentList.getItems().remove(i);
          }
        }
      }
    } else {
      int size = currentList.getItems().size();
      for (int i = 0; i < size; i++) {
        currentList.getItems().remove(0);
      }
    }
  }

  private void updateRemoveList() {
    userList = socialNetwork.graph.getAllNodes();

    compareAndDelete(personList, userList);
    compareAndDelete(friendList1, userList);
    compareAndDelete(friendList2, userList);
    compareAndDelete(removeFriendList1, userList);
    compareAndDelete(removeFriendList2, userList);
    compareAndDelete(mutualFriend1, userList);
    compareAndDelete(mutualFriend2, userList);
    compareAndDelete(sequence1, userList);
    compareAndDelete(sequence2, userList);
    compareAndDelete(searchInp, userList);
  }



  private String getNameFromCoordinates(double x1, double x2) {
    return null;

  }

  private double[] getCoordinatesFromName(String name) {
    return null;
  }

  private void setSelectedUser(String name) {
    ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(name);
    centralUser = name;
    drawGraph(name, friends);
    try {
      pane.setLeft(statusGraphBox);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private StackPane createCircle(String name, double x, double y) {
    // Circle circle = new Circle();
    // circle.getStyleClass().add("intersection");
    // circle.setRadius(30);
    //
    //
    // circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
    // selectedCircle.set(circle);
    // selectedLocation.set(new Point2D(x, y));
    // });
    //
    // Text text = new Text(name);
    // text.setBoundsType(TextBoundsType.VISUAL);
    // StackPane stack = new StackPane();
    // stack.getChildren().addAll(circle, text);
    // stack.setPrefSize(40,40);
    // stack.relocate(x,y);
    // return stack ;
    Button roundButton = new Button(name);
    if (name.equals(centralUser)||name.equals(headUser)||name.equals(tailUser)) {
      roundButton.setStyle("-fx-background-radius: 30px; " + "-fx-min-width: 60px; "
        + "-fx-min-height: 60px; " + "-fx-max-width: 60px; " + "-fx-max-height: 60px;" + 
          "-fx-background-color: CORNFLOWERBLUE;");
    } else {

    roundButton.setStyle("-fx-background-radius: 30px; " + "-fx-min-width: 60px; "
        + "-fx-min-height: 60px; " + "-fx-max-width: 60px; " + "-fx-max-height: 60px;");
    }
    // roundButton.setLayoutX(x);
    // roundButton.setLayoutY(y);
    roundButton.setOnAction((event) -> {
      if (name != null) {
        
        setSelectedUser(name);
        
      }
    });
    StackPane layout = new StackPane(roundButton);
    layout.setPadding(new Insets(10));
    return layout;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub
    pane.setPadding(new Insets(10, 10, 10, 10));
    VBox rightBox = new VBox(35);
    drawGraph(null, null);
//    socialNetwork.addUser("p1");
//    socialNetwork.addUser("p2");
//    socialNetwork.addUser("p3");
//    socialNetwork.addUser("p4");
//
//    socialNetwork.addFriends("p1", "p2");
//    socialNetwork.addFriends("p1", "p3");
//    socialNetwork.addFriends("p1", "p4");

//    drawGraph("p1", (ArrayList<Person>) socialNetwork.getFriends("p1"));
    setUpBasicMenuBox();
    setUpAdvanceMenuBox();
    rightBox.getChildren().addAll(basicMenuBox, advanceMenuBox);
    rightBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n"
        + "-fx-padding: 10pt;\n" + "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");

    pane.setRight(rightBox);
    setUpstatusBox();

    pane.setLeft(statusGraphBox);
    // leftSide = new VBox(20);
    // leftSide.getChildren().addAll(statusGraphBox, statusBox );
    // pane.setLeft(leftSide);

    pane.setBottom(statusBox);

    Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add("grid.css");
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(scene);
    primaryStage.show();
    updateAddList();

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }

}
