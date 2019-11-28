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
public class SocialNetwork implements SocialNetworkADT{
  
  private Graph graph;

  @Override
  public boolean addFriends(String person1, String person2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeFriends(String person1, String person2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean addUser(String person) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeUser(String person) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<Person> getFriends(String person) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Person> getMutualFriends(String person1, String person2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Person> getShortestPath(String person1, String person2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Graph> getConnectedComponents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void loadFromFile(File file) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void saveToFile(File file) {
    // TODO Auto-generated method stub
    
  }

}
