/**
 * 
 */
package application;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Siyuan
 *
 */
public class Graph implements GraphADT{

  private HashMap<Person, List<Person>> adjacencyList;
  @Override
  public boolean addEdge(Person person1, Person person2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeEdge(Person person1, Person person2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean addNode(Person person) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeNode(Person person) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<Person> getNeighbors(Person person) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Person getNode(String personName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Person> getAllNodes() {
    // TODO Auto-generated method stub
    return null;
  }

}
