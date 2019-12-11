/////////////////////////////// File Header ///////////////////////////////////
//
// Assignment name: ateam Final Project - Social Network
// Author(s) and email addresses: 
// 				Erzhen Zhang, ezhang25@wisc.edu
//				Ruokai Yin, ryin25@wisc.edu	
//				Seanna Zhang, szhang577@wisc.edu
//				Kaiwen Shen, kshen26@wisc.edu
// Due date: Dec 11th
// Other source credits:
// Known bugs: None
//
///////////////////////////////////////////////////////////////////////////////
package application;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Filename: Main.java Project: ateam
 * 
 * @author Erzhen Zhang, Ruokai Yin, Seanna Zhang, Kaiwen Shen
 * 
 *         Main is to represent a socialNetwork, which is used to represent a
 *         graph manager to do some operation associated with the graph such as
 *         add users, remove users, etc.
 */
public class Main extends Application {
	// program size and title
	private static final double WINDOW_WIDTH = 1170;
	private static final double WINDOW_HEIGHT = 800;
	private static final String APP_TITLE = "Weibo Social Network";

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

	// Status Info
	private List<Person> userList;
	private String statusMessage;

	// special Users
	private String centralUser;
	private String headUser;
	private String tailUser;
	private HBox updateBox;

	// Dropbox content
	private ComboBox<String> personList = new ComboBox<String>();
	private ComboBox<String> removeFriendList2 = new ComboBox<String>();
	private ComboBox<String> friendList1 = new ComboBox<String>();
	private ComboBox<String> friendList2 = new ComboBox<String>();
	private ComboBox<String> removeFriendList1 = new ComboBox<String>();
	private ComboBox<String> mutualFriend1 = new ComboBox<String>();
	private ComboBox<String> mutualFriend2 = new ComboBox<String>();
	private ComboBox<String> sequence1 = new ComboBox<String>();
	private ComboBox<String> sequence2 = new ComboBox<String>();
	private ComboBox<String> searchInp = new ComboBox<String>();

	/**
	 * set up a status Box
	 *
	 */
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
		statusBox.setStyle("-fx-border-color: blue;\n" + "-fx-border-widths: 2;\n" + "-fx-padding: 10pt;\n"
				+ "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");
	}

	/**
	 * Helper method to get the lastest instruction
	 *
	 */
	private String getCurrentInstruction() {
		return this.statusMessage;

	}

	/**
	 * Helper method to set the lastest instruction into status parameter
	 * 
	 * @param status varibale that stores instructions
	 *
	 */
	private void setCurrentInstruction(String status) {
		this.statusMessage = status;
	}

	/**
	 * BasicMenu: add,remove users and friendship, load file
	 *
	 */
	private void setUpBasicMenuBox() {
		basicMenuBox = new HBox(10);

		// create button objects
		VBox buttonCollections = new VBox(35);
		Button addUser = new Button("Add User");
		Button removeUser = new Button("Remove User");
		Button addFriends = new Button("Add Friendship");
		Button removeFriends = new Button("Remove Friendship");
		Button loadFile = new Button("Load File");

		buttonCollections.getChildren().addAll(addUser, removeUser, addFriends, removeFriends, loadFile);

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

		inputCollections.getChildren().addAll(addPerson, personList, addFriendship, removeFriendship, loadFileName);

		// combine buttons and inputs
		basicMenuBox.getChildren().addAll(buttonCollections, inputCollections);

		// set up buttons
		addUser.setOnAction((event) -> {

			String name = addPerson.getText();
			// add user and update network
			try {
				socialNetwork.addUser(name);
				updateAddList();

				ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(name);
				centralUser = name;
				drawGraph(name, friends);
				String succMessage = "New user successfully added!";
				pane.setLeft(statusGraphBox);
				setCurrentInstruction(succMessage);
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (DuplicatePersonException e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (Exception e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			}
		});

		// delete node and update network
		removeUser.setOnAction((event) -> {
			String name = personList.getValue();
			System.out.println(name);

			// repick central user
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
				// delete node and update network
				try {
					socialNetwork.removeUser(name);
					updateRemoveList();
					ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
					drawGraph(centralUser, friends);
					String succMessage = "User successfully removed!";
					pane.setLeft(statusGraphBox);
					setCurrentInstruction(succMessage);
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (Exception e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				}
			}
		});

		// add friendship and update network
		addFriends.setOnAction((event) -> {
			String user1 = friendList1.getValue();
			String user2 = friendList2.getValue();
			// draw friendship if input is valid
			try {
				socialNetwork.addFriends(user1, user2);
				ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
				drawGraph(centralUser, friends);
				String succMessage = "Friendship successfully added!";
				pane.setLeft(statusGraphBox);
				setCurrentInstruction(succMessage);
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (PersonNotFoundException e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (DuplicateEdgesException e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (DuplicatePersonException e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			} catch (Exception e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			}
		});

		// remove friendship and update network
		removeFriends.setOnAction((event) -> {
			String user1 = removeFriendList1.getValue();
			String user2 = removeFriendList2.getValue();
			// remove friendship if input is valid
			if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
				try {
					socialNetwork.removeFriends(user1, user2);
					ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(centralUser);
					drawGraph(centralUser, friends);
					String succMessage = "Friendship successfully removed!";
					pane.setLeft(statusGraphBox);
					setCurrentInstruction(succMessage);
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (Exception e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				}
			}
		});

		// load from file and establish network
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
					String succMessage = "File successfully loaded!";
					try {
						pane.setLeft(statusGraphBox);
						setCurrentInstruction(succMessage);
						setUpstatusBox();
						pane.setBottom(statusBox);
					} catch (Exception e) {
						setCurrentInstruction(e.getMessage());
						setUpstatusBox();
						pane.setBottom(statusBox);
					}
				} catch (FileNotFoundException e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (DuplicatePersonException e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (PersonNotFoundException e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (DuplicateEdgesException e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (NoEdgeExistsException e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				}
			} else {
				System.out.println("name is null");
			}
		});

	}

	/**
	 * AdvanceMenu: clear network, undo last instruction, exit program with pop-ups
	 * export current network, find mutual friend, find friend sequences, search for
	 * a user
	 *
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

				// await for userinput and decide whether clear all
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
					drawGraph(null, null);
					String succMessage = "Clear successfully!";

					// clear all and update all lists and boxes
					try {
						pane.setLeft(statusGraphBox);
						setCurrentInstruction(succMessage);
						setUpstatusBox();
						pane.setBottom(statusBox);
					} catch (Exception e1) {
						setCurrentInstruction(e1.getMessage());
						setUpstatusBox();
						pane.setBottom(statusBox);
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
				} else if (option.get() == ButtonType.OK) {
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
						setCurrentInstruction(e1.getMessage());
						setUpstatusBox();
						pane.setBottom(statusBox);
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

		mutualFriend1.setMaxWidth(170);
		mutualFriend1.setPrefWidth(145);

		mutualFriend2.setMaxWidth(170);
		mutualFriend2.setPrefWidth(145);
		mutualFriend.getChildren().addAll(mutualFriend1, mutualFriend2);

		HBox sequence = new HBox(10);

		sequence1.setMaxWidth(170);
		sequence1.setPrefWidth(145);

		sequence2.setMaxWidth(170);
		sequence2.setPrefWidth(145);
		sequence.getChildren().addAll(sequence1, sequence2);

		searchInp.setMaxWidth(350);
		searchInp.setPrefWidth(300);

		inputCollection.getChildren().addAll(exportInp, mutualFriend, sequence, searchInp);

		HBox buttonAndInput = new HBox(10);
		buttonAndInput.getChildren().addAll(buttonCollection2, inputCollection);

		advanceMenuBox.getChildren().addAll(buttonCollection1, buttonAndInput);

		// actions
		// search for user and set to central
		search.setOnAction((click) -> {
			String user = searchInp.getValue();
			if (user != null) {
				setSelectedUser(user);
			}
		});

		// find mutual friends and display on network
		mutual.setOnAction((event) -> {
			String user1 = mutualFriend1.getValue();
			String user2 = mutualFriend2.getValue();
			if (user1 != null && user2 != null && !user1.isEmpty() && !user2.isEmpty()) {
				// socialNetwork.getMutualFriends(user1, user2);
				drawMutualFriend(user1, user2);
			}

			try {
				pane.setLeft(statusGraphBox);
			} catch (Exception e) {
				setCurrentInstruction(e.getMessage());
				setUpstatusBox();
				pane.setBottom(statusBox);
			}
		});

		// find shortest path between two users and display at status box
		sequence_friends.setOnAction((click) -> {
			String user1 = sequence1.getValue();
			String user2 = sequence2.getValue();
			String pathString = "";
			if (user1 != null && user2 != null) {
				List<Person> path;
				try {
					path = socialNetwork.getShortestPath(user1, user2);
					pathString = pathString.concat(path.get(0).getName());
					for (int i = 1; i < path.size(); ++i) {
						pathString = pathString.concat(" -> " + path.get(i).getName());
					}
					pathString = pathString.concat(" (shortest path)");
					pane.setBottom(statusBox);
					setCurrentInstruction(pathString);
					setUpstatusBox();
				} catch (DuplicatePersonException e1) {
					setCurrentInstruction(e1.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (Exception e) {
					setCurrentInstruction(e.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				}

			} else {
				System.out.println("name is null");
			}
		});

		// export all instructions into a file
		export.setOnAction((event) -> {
			String fileName = exportInp.getText();
			// establish network if file is valid
			if (fileName != null && !fileName.isEmpty()) {
				File file = new File(fileName);
				String succMessage = "Saved successfully!";
				try {
					socialNetwork.saveToFile(file);
					setCurrentInstruction(succMessage);
					setUpstatusBox();
					pane.setBottom(statusBox);
				} catch (IOException e1) {
					setCurrentInstruction(e1.getMessage());
					setUpstatusBox();
					pane.setBottom(statusBox);
				}
			}
		});
	}

	/**
	 * Helper method for reading the num of grps
	 * 
	 * @param int numOfGroups num of groups
	 * @return Label num Label
	 */
	private Label updateNumOfConnectedGroups(int numOfGroups) {
		String numUpdate = "Number of Connected Groups is " + numOfGroups;
		Label numLabel = new Label(numUpdate);
		return numLabel;
	}

	/**
	 * Helper method for reading the num of friends
	 * 
	 * @param int numOfFriends num of frineds
	 * @return Label num Label
	 */
	private Label updateNumOfFriends(int numOfFriends) {
		String numUpdate = "Number of Freind is " + numOfFriends;
		Label numLabel = new Label(numUpdate);
		return numLabel;
	}

	/**
	 * Helper method for reading the num of users
	 * 
	 * @param int numOfUsers num of users
	 * @return Label num Label
	 */
	private Label updateNumOfUsers(int numOfUsers) {
		String numUpdate = "Number of Users is " + numOfUsers;
		Label numLabel = new Label(numUpdate);
		return numLabel;
	}

	/**
	 * Method for drawing the network
	 * 
	 * @param String            name name of central user
	 * @param ArrayList<Person> adjacencyList
	 */
	private void drawGraph(String name, ArrayList<Person> adjacencyList) {
		// outlayer
		drawGraphPane = new BorderPane();
		drawGraphPane.prefWidth(580);
		drawGraphPane.prefHeight(500);
		statusGraphBox = new VBox(20);
		infoBox = new HBox(20);
		infoBox.prefWidth(580);
		updateBox = new HBox(20);

		Label updateGrp = updateNumOfConnectedGroups(socialNetwork.getConnectedComponents());
		Label updateUsr = updateNumOfUsers(socialNetwork.numberOfUsers);
		Label updateFri = updateNumOfFriends(socialNetwork.numberOfFriends);

		updateBox.getChildren().addAll(updateGrp, updateUsr, updateFri);

		Text appTitle = new Text("Weibo Social Network");
		appTitle.setStyle("-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" + "-fx-font-size: 20px;\n");
		appTitle.setFill(Color.rgb(207, 18, 22));

		// draw central user
		System.out.println(name);
		if (name != null) {
			drawNode(drawGraphPane, name, 284, 250);
		}

		// draw all friends with random coors from bank
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
					drawEdge(drawGraphPane, 284, 250, 66 * coors[randNum][0] + 20, 10 + 60 * coors[randNum][1]);
					coors[randNum] = null;
				}
			}
		}
		infoBox.getChildren().addAll(appTitle);
		infoBox.setAlignment(Pos.CENTER);

		statusGraphBox.getChildren().addAll(updateBox, infoBox, drawGraphPane);

	}

	/**
	 * Help method for drawing mutual friends on network
	 * 
	 * @param String name1
	 * @param String name2
	 */
	private void drawMutualFriend(String name1, String name2) {
		// layer
		drawGraphPane = new BorderPane();
		List<Person> mutualFriends = new ArrayList<Person>();
		try {
			mutualFriends = socialNetwork.getMutualFriends(name1, name2);
		} catch (DuplicatePersonException e) {
			setCurrentInstruction(e.getMessage());
			setUpstatusBox();
			pane.setBottom(statusBox);
		}
		statusGraphBox = new VBox(20);
		infoBox = new HBox(20);
		infoBox.prefWidth(580);
		updateBox = new HBox(20);

		Label updateGrp = updateNumOfConnectedGroups(socialNetwork.getConnectedComponents());
		Label updateUsr = updateNumOfUsers(socialNetwork.numberOfUsers);
		Label updateFri = updateNumOfFriends(socialNetwork.numberOfFriends);

		updateBox.getChildren().addAll(updateGrp, updateUsr, updateFri);

		Text appTitle = new Text("Weibo Social Network");
		appTitle.setStyle("-fx-text-alignment: center;\n" + "-fx-font-weight: bolder;\n" + "-fx-font-size: 20px;\n");
		appTitle.setFill(Color.rgb(207, 18, 22));

		// fixing the head and tail before drawing
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

		// drawing all mutual friends
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
					drawEdge(drawGraphPane, 30, 250, 66 * coors[randNum][0] + 20, 10 + 60 * coors[randNum][1]);
					drawEdge(drawGraphPane, 550, 250, 66 * coors[randNum][0] + 20, 10 + 60 * coors[randNum][1]);
					coors[randNum] = null;
				}
			}
		}

		infoBox.getChildren().addAll(appTitle);
		infoBox.setAlignment(Pos.CENTER);

		statusGraphBox.getChildren().addAll(updateBox, infoBox, drawGraphPane);
		headUser = null;
		tailUser = null;
		centralUser = temp;
	}

	/**
	 * Helper method for drawing circles
	 * 
	 * @param BorderPane pane
	 * @param String     name
	 * @param double     x x-coord
	 * @param double     y y-coord
	 */
	private void drawNode(BorderPane pane, String name, double x, double y) {

		StackPane stack = new StackPane();
		stack = createCircle(name, x, y);
		stack.setLayoutX(x);
		stack.setLayoutY(y);
		pane.getChildren().addAll(stack);

	}

	/**
	 * Helper method for drawing edge
	 * 
	 * @param BorderPane pane
	 * @param double     x1 x-coord
	 * @param double     y1 y-coord
	 * @param double     x2 x-coord
	 * @param double     y2 y-coord
	 */
	private void drawEdge(BorderPane drawGraphPane, double x1, double y1, double x2, double y2) {
		Line line = new Line();
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);
		drawGraphPane.getChildren().addAll(line);
	}

	/**
	 * Helper method for updating combobox when adding
	 */
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

	/**
	 * Helper method for updating combobox when deleting
	 * 
	 * @param ComboBox<String> currentList
	 * @param List<Person>     nextList
	 */
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

	/**
	 * Helper method for updating combobox when deleting
	 */
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

	/**
	 * Helper method for deciding central User
	 */
	private void setSelectedUser(String name) {
		ArrayList<Person> friends = (ArrayList<Person>) socialNetwork.getFriends(name);
		centralUser = name;
		drawGraph(name, friends);
		socialNetwork.operation = socialNetwork.operation.concat("s " + name + "\n");
		try {
			pane.setLeft(statusGraphBox);

		} catch (Exception e) {
			setCurrentInstruction(e.getMessage());
			setUpstatusBox();
			pane.setBottom(statusBox);
		}
	}

	/**
	 * Helper method for drawing circles
	 * 
	 * @param String name
	 * @param double x x-coord
	 * @param double y y-coord
	 */
	private StackPane createCircle(String name, double x, double y) {
		Button roundButton = new Button(name);
		// draw special users with different color
		if (name.equals(centralUser) || name.equals(headUser) || name.equals(tailUser)) {
			roundButton.setStyle("-fx-background-radius: 30px; " + "-fx-min-width: 60px; " + "-fx-min-height: 60px; "
					+ "-fx-max-width: 60px; " + "-fx-max-height: 60px;" + "-fx-background-color: CORNFLOWERBLUE;");
		} else {
			// draw common users
			roundButton.setStyle("-fx-background-radius: 30px; " + "-fx-min-width: 60px; " + "-fx-min-height: 60px; "
					+ "-fx-max-width: 60px; " + "-fx-max-height: 60px;");
		}
		roundButton.setOnAction((event) -> {
			if (name != null) {
				setSelectedUser(name);
			}
		});
		StackPane layout = new StackPane(roundButton);
		layout.setPadding(new Insets(10));
		return layout;
	}

	/**
	 * Main application that display all GUI components
	 * 
	 * @param Stage primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		pane.setPadding(new Insets(10, 10, 10, 10));
		VBox rightBox = new VBox(35);
		drawGraph(null, null);

		// draw right side
		setUpBasicMenuBox();
		setUpAdvanceMenuBox();
		rightBox.getChildren().addAll(basicMenuBox, advanceMenuBox);
		rightBox.setStyle("-fx-border-color: rgb(207,18,22);\n" + "-fx-border-widths: 2;\n" + "-fx-padding: 10pt;\n"
				+ "-fx-border-insets: 5;\n" + "-fx-border-radius: 5;");

		pane.setRight(rightBox);

		// draw left side
		setUpstatusBox();

		pane.setLeft(statusGraphBox);

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
