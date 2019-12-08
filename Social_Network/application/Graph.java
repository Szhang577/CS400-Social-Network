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


  private HashMap<Person, List<Person>> adjacencyList; // list of all person and its neighbors
  private LinkedHashSet<Person> adjacentVertices; // set of ajacentVertices
  private LinkedHashSet<Person> allVertices; // set of all vertices

  public Graph() {
    adjacencyList = new HashMap<Person, List<Person>>();
    adjacentVertices = new LinkedHashSet<Person>();
    allVertices = new LinkedHashSet<Person>();
  }

  /**
   * Add the edge of person1 and person2 to this graph. 
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
   * the graph
   * @param vertex1 the start of the added edge
   * @param vertex2 the destination of the added edge
   * @throws PersonNotFoundException if did not find selected user
   * @return true if successfully add edges 
   */
  @Override
  public boolean addEdge(Person person1, Person person2) throws PersonNotFoundException {
    // TODO Auto-generated method stub
    if (person1 != null && person2 != null) {
      if (adjacencyList.containsKey(person1) && adjacencyList.containsKey(person2)) {
        List<Person> edgelist1 = adjacencyList.get(person1);
        List<Person> edgelist2 = adjacencyList.get(person2);
        if (!edgelist1.contains(person2)) {
          edgelist1.add(person2);
        }
        if (!edgelist2.contains(person1)) {
          edgelist2.add(person1);
        }
        return true;
      } else {
        throw new PersonNotFoundException("Unable to add because did not find selected user in "
            + "social network.");
      }
    }
    return false;
  }

  @Override
  public boolean removeEdge(Person person1, Person person2) throws PersonNotFoundException {
    // TODO Auto-generated method stub
    if (person1 != null && person2 != null) {
      if (adjacencyList.containsKey(person1) && adjacencyList.containsKey(person2)) {
        List<Person> edgelist1 = adjacencyList.get(person1);
        List<Person> edgelist2 = adjacencyList.get(person2);
        if (!edgelist1.contains(person2)) {
          edgelist1.remove(person2);
        }
        if (!edgelist2.contains(person1)) {
          edgelist2.remove(person1);
        }
        return true;
      } else {
        throw new PersonNotFoundException("Unable to remove because did not find selected user in" +
      " social network.");
      }
    }
    return false;
  }

  @Override
  public boolean addNode(Person person) throws DuplicatePersonException {
    // TODO Auto-generated method stub
    if (person != null) {
      if (!adjacencyList.containsKey(person)) {
        adjacencyList.put(person, new ArrayList<Person>());
        return true;
      } else {
        throw new DuplicatePersonException("Unable to add because already have same user in social"
            + " network.");
      }
    }
    return false;
  }

  @Override
  public boolean removeNode(Person person) throws PersonNotFoundException {
    // TODO Auto-generated method stub
    if (person != null) {
      if (adjacencyList.containsKey(person)) {
        adjacencyList.remove(person);
        return true;
      } else {
        throw new PersonNotFoundException("Unable to remove because did not find selected user in"
            + " social network.");
      }
    }
    return false;
  }

  @Override
  public Set<Person> getNeighbors(Person person) {
    // TODO Auto-generated method stub
    if (person != null) {
      if (adjacencyList.containsKey(person)) {
        List<Person> neighbors = adjacencyList.get(person);
        for (Person aPerson : neighbors) {
          adjacentVertices.add(aPerson);
        }
        return adjacentVertices;
      }
    }
    return null;
  }

  @Override
  public Person getNode(String personName) {
    // TODO Auto-generated method stub
    for (Map.Entry<Person, List<Person>> entry : adjacencyList.entrySet()) {
      if (entry.getKey().getName().equals(personName)) {
        return entry.getKey();
      }
    }
    return null;
  }

  @Override
  public Set<Person> getAllNodes() {
    // TODO Auto-generated method stub

    for (Map.Entry<Person, List<Person>> entry : adjacencyList.entrySet()) {
      allVertices.add(entry.getKey());
    }
    return allVertices;
  }

}
