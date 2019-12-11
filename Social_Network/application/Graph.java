/**
 * 
 */
package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Siyuan
 *
 */
public class Graph implements GraphADT {


  private HashMap<Person, List<Person>> adjacencyList = new HashMap<Person, List<Person>>(); // list of all person and its neighbors
  private ArrayList<Person> allVertices = new ArrayList<Person>(); // set of all vertices

  public Graph() {
    adjacencyList = new HashMap<Person, List<Person>>();
    allVertices = new ArrayList<Person>();
  }

  /**
   * Add the edge of person1 and person2 to this graph. 
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
   * the graph
   * @param person1 the start of the added edge
   * @param person2 the destination of the added edge
   * @throws PersonNotFoundException if did not find selected user
   * @return true if successfully add edges and false otherwise
   * @throws DuplicateEdgesException 
   */
  @Override
  public boolean addEdge(Person person1, Person person2) 
      throws PersonNotFoundException, DuplicateEdgesException {
    if (person1 != null && person2 != null) {
      if (adjacencyList.containsKey(person1) && adjacencyList.containsKey(person2)) {
        List<Person> edgelist1 = adjacencyList.get(person1);
        List<Person> edgelist2 = adjacencyList.get(person2); // list of selected users' edges
        boolean edge1Exist = false;
        boolean edge2Exist = false;
        
        if (!edgelist1.contains(person2)) {
          edgelist1.add(person2);
          edge1Exist = true;
        }
        if (!edgelist2.contains(person1)) {
          edgelist2.add(person1);
          edge2Exist = true;
        }
        if (edge1Exist && edge2Exist) { // successfully add edge between two selected users
          return true;
        } else { // there is already exists edges between these two users
          throw new DuplicateEdgesException("Unable to add friendship because they already have "
              + "friendship");
        }
      } else { // did not find selected user in graph
        throw new PersonNotFoundException("Unable to add because did not find selected user in "
            + "social network.");
      }
    }
    return false;
  }

  /**
   * Remove the edge of person1 and person2 from this graph. 
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
   * the graph 3. the edge between two vertices exists
   * @param person1 the start of the added edge
   * @param person2 the destination of the added edge
   * @throws PersonNotFoundException if did not find selected user
   * @return true if successfully remove edges and false otherwise
   * @throws NoEdgeExistsException 
   */
  @Override
  public boolean removeEdge(Person person1, Person person2) 
      throws PersonNotFoundException, NoEdgeExistsException {
    if (person1 != null && person2 != null) {
      if (adjacencyList.containsKey(person1) && adjacencyList.containsKey(person2)) {
        List<Person> edgelist1 = adjacencyList.get(person1);
        List<Person> edgelist2 = adjacencyList.get(person2); // list of given person's edges
        boolean remove1 = false;
        boolean remove2 = false; // check if the edge exists
        
        if (!edgelist1.contains(person2)) {
          edgelist1.remove(person2);
          remove1 = true;
        }
        if (!edgelist2.contains(person1)) {
          edgelist2.remove(person1);
          remove2 = true;
        }
        if (remove1 && remove2) {
          return true;
        } else { // if there is no edge between two selected user
          throw new NoEdgeExistsException("Unable to remove because did not have any friendship" +
        " between selected users.");
        }
      } else { // the graph did not have given person, unable to remove edge
        throw new PersonNotFoundException("Unable to remove because did not find selected user in" +
      " social network.");
      }
    }
    return false;
  }

  /**
   * Add the new person node to this graph. 
   * 
   * Valid argument conditions: 1. vertex is not null 2. vertex is not in this graph
   * @param person the node need to add
   * @throws DuplicatePersonException if user already exist in this graph
   * @return true if successfully add user and false otherwise
   */
  @Override
  public boolean addNode(Person person) throws DuplicatePersonException {
    if (person != null) {
      if (!adjacencyList.containsKey(person)) { // if the graph did not have person, add it
        adjacencyList.put(person, new ArrayList<Person>());
        return true;
      } else { // if the graph has the given person, it cause duplicate
        throw new DuplicatePersonException("Unable to add because already have same user in social"
            + " network.");
      }
    }
    return false;
  }

  /**
   * Remove the given person node to this graph. 
   * 
   * Valid argument conditions: 1. vertex is not null 2. vertex is in this graph
   * @param person the node need to remove
   * @throws PersonNotFoundException if did not find selected user
   * @return true if successfully add user and false otherwise
   * @throws NoEdgeExistsException 
   */
  @Override
  public boolean removeNode(Person person) throws PersonNotFoundException, NoEdgeExistsException {
    if (person != null) {
      if (adjacencyList.containsKey(person)) { // check if graph contains the given person
        List<Person> friendList = getNeighbors(person);
        if (friendList != null && !friendList.isEmpty()) {
          for (int i = 0; i < friendList.size(); i++) {
            adjacencyList.get(friendList.get(i)).remove(person);
          }
        }
        adjacencyList.remove(person);
        return true;
      } else { // if the graph did not have given person
        throw new PersonNotFoundException("Unable to remove because did not find selected user in"
            + " social network.");
      }
    }
    return false;
  }

  /**
   * Get all the neighbor (adjacent) people of a person
   * 
   * @param person given vertex for finding its edges
   * @return the list of adjacent edges of the given person or null if did not find
   */
  @Override
  public List<Person> getNeighbors(Person person) {
    if (person != null) {
      if (adjacencyList.containsKey(person)) { // check if graph contains given person
        return adjacencyList.get(person);
      }
    }
    return null;
  }

  /**
   * Get the corresponding vertex by given name
   * 
   * @param personName given name for finding vertex
   * @return the vertex of given name or null if did not find
   */
  @Override
  public Person getNode(String personName) {
    for (Map.Entry<Person, List<Person>> entry : adjacencyList.entrySet()) {
      if (entry.getKey().getName().equals(personName)) {
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   * @return all vertices in the graph
   */
  @Override
  public List<Person> getAllNodes() {
    allVertices = new ArrayList<Person>();
    for (Map.Entry<Person, List<Person>> entry : adjacencyList.entrySet()) {
      allVertices.add(entry.getKey());
    }
    return allVertices;
  }

}
