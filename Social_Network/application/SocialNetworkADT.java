/**
 * 
 */
package application;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * @author Siyuan
 *
 */
public interface SocialNetworkADT {
  public boolean addFriends(String person1, String person2);
  public boolean removeFriends(String person1, String person2);
  public boolean addUser(String person);
  public boolean removeUser(String person);
  public Set<Person> getFriends(String person);
  public Set<Person> getMutualFriends(String person1, String person2);
  public List<Person> getShortestPath(String person1, String person2);
  public Set<Graph> getConnectedComponents();
  public void loadFromFile(File file);
  public void saveToFile(File file);
}
