/**
 * 
 */
package application;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Siyuan
 *
 */
public interface SocialNetworkADT {
  public boolean addFriends(String person1, String person2)
      throws DuplicatePersonException, PersonNotFoundException, DuplicateEdgesException;

  public boolean removeFriends(String person1, String person2)
      throws PersonNotFoundException, NoEdgeExistsException, DuplicatePersonException;

  public boolean addUser(String person) throws DuplicatePersonException;

  public boolean removeUser(String person) throws PersonNotFoundException, NoEdgeExistsException;

  public List<Person> getFriends(String person);

  public List<Person> getMutualFriends(String person1, String person2) throws DuplicatePersonException;

  public List<Person> getShortestPath(String person1, String person2) throws DuplicatePersonException;

  public int getConnectedComponents();

  public void loadFromFile(File file) throws FileNotFoundException, DuplicatePersonException,
      PersonNotFoundException, DuplicateEdgesException, NoEdgeExistsException;

  public void saveToFile(File file) throws IOException;
}
