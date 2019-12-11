/**
 * 
 */
package application;

import java.util.List;
import java.util.Set;

/**
 * @author Siyuan
 *
 */
public interface GraphADT {
  public boolean addEdge(Person person1, Person person2) throws PersonNotFoundException, DuplicateEdgesException;
  public boolean removeEdge(Person person1, Person person2) throws PersonNotFoundException, NoEdgeExistsException;
  public boolean addNode(Person person) throws DuplicatePersonException;
  public boolean removeNode(Person person) throws PersonNotFoundException, NoEdgeExistsException;
  public List<Person> getNeighbors(Person person);
  public Person getNode(String personName);
  public List<Person> getAllNodes();
}
