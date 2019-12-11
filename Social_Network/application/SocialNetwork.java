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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Filename: SocialNetwork.java Project: ateam
 * 
 * @author Erzhen Zhang, Ruokai Yin, Seanna Zhang, Kaiwen Shen
 * 
 *         SocialNetwork is used to represent a graph manager to do some
 *         operation associated with the graph such as add users, remove users,
 *         etc.
 */
public class SocialNetwork implements SocialNetworkADT {

	public Graph graph = new Graph();
	public String operation = "";

	public boolean centralSet = false; // boolean to determine whether central user been set
	public String centralUser = ""; // the central user

	public int numberOfUsers = 0;
	public int numberOfFriends = 0;

	/**
	 * Add the friendship between two users in the social net work
	 * 
	 * @param String person1 first user
	 * @param String person2 second user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws DuplicateEdgesException  throw if the friendship already existed
	 * @return boolean true if add successfully, otherwise false
	 */
	@Override
	public boolean addFriends(String person1, String person2)
			throws DuplicatePersonException, PersonNotFoundException, DuplicateEdgesException {
		boolean edgeAdded = false;
		// check if the strings are null
		if (!person1.equals(null) && !person2.equals(null)) {
			if (person1.equals(person2)) {
				throw new DuplicatePersonException("Unable to add friends because two selected user are same");
			} else {
				Person user1 = graph.getNode(person1);
				Person user2 = graph.getNode(person2);
				edgeAdded = graph.addEdge(user1, user2); // add the edge
			}
		}
		if (edgeAdded == true) {
			numberOfFriends++;
			operation = operation.concat("a " + person1 + " " + person2 + "\n");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove the friendship between two users in the social net work
	 * 
	 * @param String person1 first user
	 * @param String person2 second user
	 * @throws DuplicatePersonException throw if these two users are same
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws NoEdgeExistsException    throw if the friendship does not exist
	 * @return boolean true if removed successfully, otherwise false
	 */
	@Override
	public boolean removeFriends(String person1, String person2)
			throws PersonNotFoundException, NoEdgeExistsException, DuplicatePersonException {
		boolean nodeRemoved = false;
		if (!person1.equals(null) && !person2.equals(null)) {
			// if two users are same, throw exception
			if (person1.equals(person2)) {
				throw new DuplicatePersonException("Unable to remove friends because two selected user are same");
			} else {
				Person user1 = graph.getNode(person1);
				Person user2 = graph.getNode(person2);
				nodeRemoved = graph.removeEdge(user1, user2);
			}
		}
		if (nodeRemoved) {
			numberOfFriends--;
			operation = operation.concat("r " + person1 + " " + person2 + "\n");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add a user into the social net work
	 * 
	 * @param String person user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @return boolean true if added successfully, otherwise false
	 */
	public boolean addUser(String person) throws DuplicatePersonException {

		boolean userAdded = false;
		if (!person.equals(null)) {
			// check if the user already in the net work
			if (graph.getNode(person) == null) {
				Person user = new Person(person);
				userAdded = graph.addNode(user);
			} else {
				throw new DuplicatePersonException(
						"Unable to add because already have same user in social" + " network.");
			}
		}
		if (userAdded) {
			numberOfUsers++;
			operation = operation.concat("a " + person + "\n");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove a user out of the social net work
	 * 
	 * @param String person user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws NoEdgeExistsException    throw if the friendship does not exist
	 * @return boolean true if removed successfully, otherwise false
	 */
	@Override
	public boolean removeUser(String person) throws PersonNotFoundException, NoEdgeExistsException {
		boolean userRemoved = false;
		int mutual = 0;
		if (person != null) {
			Person user = graph.getNode(person);
			mutual = graph.getNeighbors(user).size();
			userRemoved = graph.removeNode(user);
		}
		if (userRemoved) {
			numberOfUsers--;
			numberOfFriends = numberOfFriends - mutual;
			operation = operation.concat("r " + person + "\n");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get a list of friends of one specific user
	 * 
	 * @param String person user
	 * @return List<Person> List of friends
	 */
	@Override
	public List<Person> getFriends(String person) {
		if (person != null) {
			Person user = graph.getNode(person);
			if (user == null) {
				return null;
			}
			return graph.getNeighbors(user);
		}
		return null;
	}

	/**
	 * Get a list of Mutual Friends of two users
	 * 
	 * @param String person1 user1
	 * @param String person2 user2
	 * @throws DuplicatePersonException throw if these two persons are same
	 * @return List<Person> List of mutual friends
	 */
	@Override
	public List<Person> getMutualFriends(String person1, String person2) throws DuplicatePersonException {
		List<Person> mutualFriends = new ArrayList<Person>();
		List<Person> friendsUser1;
		List<Person> friendsUser2;
		// check if these two persons are same
		if (person1 != null && person2 != null) {
			if (person1.equals(person2)) {
				throw new DuplicatePersonException("Unable to get mutual friends because two selected user are same");
			}
			Person user1 = graph.getNode(person1);
			Person user2 = graph.getNode(person2);
			if (user1 == null || user2 == null) {
				return null;
			}
			friendsUser1 = graph.getNeighbors(user1);
			friendsUser2 = graph.getNeighbors(user2);
			// use two loops to get the list
			for (Person userFirst : friendsUser1) {
				for (Person userSecond : friendsUser2) {
					if (userSecond.equals(userFirst)) {
						mutualFriends.add(userSecond);
					}
				}
			}
		}
		return mutualFriends;
	}

	/**
	 * Get a list of shortest path of two users
	 * 
	 * @param String person1 user1
	 * @param String person2 user2
	 * @throws DuplicatePersonException throw if these two persons are same
	 * @return List<Person> List of shortest path
	 */
	public List<Person> getShortestPath(String person1, String person2) throws DuplicatePersonException {
		List<Person> reversePath = new ArrayList<Person>();
		List<Person> Path = new ArrayList<Person>();
		List<Person> allusers = graph.getAllNodes();
		if (!person1.equals(null) && !person2.equals(null)) {
			// check if these two persons are same
			if (person1.equals(person2)) {
				throw new DuplicatePersonException("Unable to find shortest path because two selected user are same");
			} else {
				int numOfusers = allusers.size();
				Person user1 = graph.getNode(person1);
				Person user2 = graph.getNode(person2);
				if (user1 == null || user2 == null) {
					return null;
				}
				// call BFS to get the array of array
				Person[][] previousTable = BFS(user1);
				Person prev = user2;
				reversePath.add(user2);
				// use previous table to recursively find the path
				while (!prev.equals(user1)) {
					for (int i = 0; i < numOfusers; ++i) {
						if (previousTable[i][0].equals(prev)) {
							prev = previousTable[i][1];
							reversePath.add(prev);
						}
					}
				}
				// reverse the reversed path
				for (int i = reversePath.size() - 1; i >= 0; i--) {
					Path.add(reversePath.get(i));
				}
			}
		}
		return Path;
	}

	/**
	 * Private helper method that is used to get the BFS table of the graph
	 * 
	 * @param String person user
	 * @return Person[][] previousTable represent each node with its previous node
	 *         in the array of array
	 */
	private Person[][] BFS(Person user) {
		List<Person> allusers = graph.getAllNodes();
		int numOfusers = allusers.size();
		Person none = new Person("none");
		Person[][] previousTable = new Person[numOfusers][2];
		// set all the node to unvisited
		for (int i = 0; i < numOfusers; ++i) {
			allusers.get(i).setVisited(false);
			previousTable[i][0] = allusers.get(i);
			previousTable[i][1] = none;
		}
		// use queue to find the shortest path
		Queue<Person> queue = new LinkedList<>();
		user.setVisited(true);
		queue.add(user);
		while (!queue.isEmpty()) {
			Person userLoop = queue.poll();
			List<Person> friendsUserLoop = graph.getNeighbors(userLoop);
			for (Person u : friendsUserLoop) {
				if (u.getVisited() == false) {
					u.setVisited(true);
					for (int i = 0; i < numOfusers; ++i) {
						if (previousTable[i][0].equals(u)) {
							previousTable[i][1] = userLoop;
						}
					}
					queue.add(u);
				}
			}
		}

		return previousTable;
	}

	/**
	 * Get the number of connected components
	 * 
	 * @return int number of connected components
	 */
	@Override
	public int getConnectedComponents() {
		int count = 0;
		List<Person> allPerson = graph.getAllNodes();
		List<Person> visited = new ArrayList<Person>();
		for (int i = 0; i < allPerson.size(); i++) {
			if (!visited.contains(allPerson.get(i))) {
				DFSUtil(allPerson.get(i), visited);
				count++;
			}
		}
		return count;
	}

	/**
	 * Private helper method that is used to get the DFS order
	 * 
	 * @param String       person user
	 * @param List<Person> visited visited list
	 */
	private void DFSUtil(Person person, List<Person> visited) {
		visited.add(person);

		List<Person> neighbors = graph.getNeighbors(person);

		if (neighbors != null && neighbors.size() != 0) {
			for (int i = 0; i < neighbors.size(); i++) {
				if (!visited.contains(neighbors.get(i))) {
					DFSUtil(neighbors.get(i), visited);
				}
			}
		}
	}

	/**
	 * Load a file into the social net work
	 * 
	 * @param File file file loaded into social network
	 * @throws DuplicatePersonException throw if these two users are same
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws FileNotFoundException    throw if the file can not be founded
	 * @throws DuplicateEdgesException  throw if the edge already existed
	 * @throws NoEdgeExistsException    throw if the edge can not be found
	 */
	@Override
	public void loadFromFile(File file) throws FileNotFoundException, DuplicatePersonException, PersonNotFoundException,
			DuplicateEdgesException, NoEdgeExistsException {

		Scanner phraser = new Scanner(file);
		while (phraser.hasNextLine()) {
			String op = phraser.nextLine();
			operation.concat(op + "\n");
			String[] words = op.split("\\s");
			String mode = words[0];
			if (mode.equals("s")) {
				if (words.length == 2) {
					centralSet = true;
					centralUser = words[1];
				} else {

				}
			} else if (mode.equals("a")) {
				if (words.length == 2) {
					addUser(words[1]);
				} else if (words.length == 3) {
					addFriends(words[1], words[2]);
				}
			} else if (mode.equals("r")) {
				if (words.length == 2) {
					removeUser(words[1]);
				} else if (words.length == 3) {
					System.out.println("2");
					removeFriends(words[1], words[2]);
				}
			} else {

			}
		}
		phraser.close();
	}

	/**
	 * Save a file associated with the social net work
	 * 
	 * @param File file file loaded into social network
	 * @throws IOException throw if there is a IOException
	 */
	@Override
	public void saveToFile(File file) throws IOException {

		// open the file with specific file name
		FileOutputStream fileByteStream = null; // used for print line into file
		PrintWriter outFS = null; // user for print line into file

		fileByteStream = new FileOutputStream(file);
		outFS = new PrintWriter(fileByteStream);
		outFS.print(operation);
		outFS.flush();

		fileByteStream.close(); // close the file
	}

}
