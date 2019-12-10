/**
 * 
 */
package application;

/**
 * @author Siyuan
 *
 */
public class Person {
  private String name;
  private boolean visited;
  public Person() {
    name = "";
  }
  public Person(String name) {
    this.name = name;
    this.visited = false;
  }
  
  public String getName() {
    return name;
  }
  
  public boolean getVisited() {
	  return this.visited;
  }
  
  public void setVisited(boolean status) {
	  this.visited = status;
  }
}
