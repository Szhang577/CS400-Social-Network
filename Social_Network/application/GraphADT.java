/**
 * 
 */
package application;

import java.util.Set;

/**
 * @author Siyuan
 *
 */
public interface GraphADT {
  public boolean addEdge(Person person1, Person person2);
  public boolean removeEdge(Person person1, Person person2);
  public boolean addNode(Person person);
  public boolean removeNode(Person person);
  public Set<Person> getNeighbors(Person person);
  public Person getNode(String personName);
  public Set<Person> getAllNodes();
}
