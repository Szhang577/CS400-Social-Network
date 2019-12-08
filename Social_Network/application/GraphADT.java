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
  public boolean addEdge(Person person1, Person person2) throws PersonNotFoundException;
  public boolean removeEdge(Person person1, Person person2) throws PersonNotFoundException;
  public boolean addNode(Person person) throws DuplicatePersonException;
  public boolean removeNode(Person person) throws PersonNotFoundException;
  public Set<Person> getNeighbors(Person person);
  public Person getNode(String personName);
  public Set<Person> getAllNodes();
}
