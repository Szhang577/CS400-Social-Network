/**
 * 
 */
package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author Siyuan
 *
 */
public class SocialNetwork implements SocialNetworkADT {

  private Graph graph = new Graph();
  private String operation = "";

  public boolean centralSet = false; // boolean to determine whether central user been set
  public String centralUser = ""; // the central user

  @Override
  public boolean addFriends(String person1, String person2)
      throws DuplicatePersonException, PersonNotFoundException, DuplicateEdgesException {
    // TODO Auto-generated method stub
    boolean nodeAdded1 = false;
    boolean nodeAdded2 = false;
    boolean edgeAdded = false;
    if (!person1.equals(null) && !person2.equals(null)) {
      Person user1 = new Person(person1);
      Person user2 = new Person(person2);
      nodeAdded1 = graph.addNode(user1);
      nodeAdded2 = graph.addNode(user2);
      edgeAdded = graph.addEdge(user1, user2);
    }
    if (nodeAdded1 == true && nodeAdded2 == true && edgeAdded == true) {
      operation.concat("a " + person1 + " " + person2 + "\n");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean removeFriends(String person1, String person2)
      throws PersonNotFoundException, NoEdgeExistsException {
    // TODO Auto-generated method stub
    boolean nodeRemoved = false;
    if (!person1.equals(null) && !person2.equals(null)) {
      Person user1 = graph.getNode(person1);
      Person user2 = graph.getNode(person2);
      nodeRemoved = graph.removeEdge(user1, user2);
    }
    if (nodeRemoved) {
      operation.concat("r " + person1 + " " + person2 + "\n");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean addUser(String person) throws DuplicatePersonException {
    // TODO Auto-generated method stub
    boolean userAdded = false;
    if (!person.equals(null)) {
      Person user = new Person(person);
      userAdded = graph.addNode(user);
    }
    if (userAdded) {
      operation.concat("a " + person + "\n");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean removeUser(String person) throws PersonNotFoundException {
    // TODO Auto-generated method stub
    boolean userRemoved = false;
    if (!person.equals(null)) {
      Person user = graph.getNode(person);
      userRemoved = graph.removeNode(user);
    }
    if (userRemoved) {
      operation.concat("r " + person + "\n");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Set<Person> getFriends(String person) {
    // TODO Auto-generated method stub
    Set<Person> friends;
    if (!person.equals(null)) {
      Person user = graph.getNode(person);
      if (user.equals(null)) {
        return null;
      }
      friends = (Set<Person>) graph.getNeighbors(user);
      return friends;
    }
    return null;
  }

  @Override
  public Set<Person> getMutualFriends(String person1, String person2) {
    // TODO Auto-generated method stub
    Set<Person> mutualFriends = new HashSet<Person>();
    List<Person> friendsUser1;
    List<Person> friendsUser2;
    if (!person1.equals(null) && !person2.equals(person2)) {
      Person user1 = graph.getNode(person1);
      Person user2 = graph.getNode(person2);
      if (user1.equals(null) || user2.equals(null)) {
        return null;
      }
      friendsUser1 = graph.getNeighbors(user1);
      friendsUser2 = graph.getNeighbors(user2);
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

  @Override
  public List<Person> getShortestPath(String person1, String person2) {
    // TODO Auto-generated method stub
    Set<Person> mutualFriends = new HashSet<Person>();
    List<Person> friendsUser1;
    if (!person1.equals(null) && !person2.equals(person2)) {
      Person user1 = graph.getNode(person1);
      Person user2 = graph.getNode(person2);
      if (user1.equals(null) || user2.equals(null)) {
        return null;
      }
      friendsUser1 = graph.getNeighbors(user1);
    }
    return null;
  }

  @Override
  public int getConnectedComponents() {
    // TODO Auto-generated method stub
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


  @Override
  public void loadFromFile(File file) throws FileNotFoundException, DuplicatePersonException,
      PersonNotFoundException, DuplicateEdgesException, NoEdgeExistsException {
    // TODO Auto-generated method stubScanner

    Scanner phraser = new Scanner(file);
    while (phraser.hasNextLine()) {
      String op = phraser.nextLine();
      operation.concat(op + "\n");
      String[] words = op.split("\\s");
      String mode = words[0];
      if (mode.equals("s")) {

      } else if (mode.equals("a")) {
        if (words.length == 2) {
          addUser(words[1]);
        } else {
          addFriends(words[1], words[2]);
        }
      } else if (mode.equals("r")) {
        if (words.length == 2) {
          removeUser(words[1]);
        } else {
          removeFriends(words[1], words[2]);
        }
      }
    }
  }

  @Override
  public void saveToFile(File file) throws IOException {
    // TODO Auto-generated method stub
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(operation);
    writer.close();
  }


}
