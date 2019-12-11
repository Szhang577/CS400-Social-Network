/////////////////////////////// File Header ///////////////////////////////////
//
// Assignment name: ateam Final Project - Social Network
// Author(s) and email addresses:
// Erzhen Zhang, ezhang25@wisc.edu
// Ruokai Yin, ryin25@wisc.edu
// Seanna Zhang, szhang577@wisc.edu
// Kaiwen Shen, kshen26@wisc.edu
// Due date: Dec 11th
// Other source credits:
// Known bugs: None
//
///////////////////////////////////////////////////////////////////////////////
package application;

/**
 * 
 * Class of a single person, contain basic getter and setter methods
 * 
 */
public class Person {

  // private fields
  private String name;
  private boolean visited;

  /**
   * default constructor
   */
  public Person() {
    name = "";
  }

  /**
   * default constructor with name
   */
  public Person(String name) {
    this.name = name;
    this.visited = false;
  }

  /**
   * private helper to get name
   * 
   * @return name of the person
   */
  public String getName() {
    return name;
  }

  /**
   * private helper to get status of been visited
   * 
   * @return status of visiting
   */
  public boolean getVisited() {
    return this.visited;
  }

  /**
   * private helper to set the status of a person
   */
  public void setVisited(boolean status) {
    this.visited = status;
  }


}
