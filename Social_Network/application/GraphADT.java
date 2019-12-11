/////////////////////////////// File Header ///////////////////////////////////
//
// Assignment name: ateam Final Project - Social Network
// Author(s) and email addresses: 
//              Erzhen Zhang, ezhang25@wisc.edu
//              Ruokai Yin, ryin25@wisc.edu 
//              Seanna Zhang, szhang577@wisc.edu
//              Kaiwen Shen, kshen26@wisc.edu
// Due date: Dec 11th
// Other source credits:
// Known bugs: None
//
///////////////////////////////////////////////////////////////////////////////
package application;

import java.util.List;

/**
 * Filename: GraphADT.java Project: ateam
 * 
 * @author Erzhen Zhang, Ruokai Yin, Seanna Zhang, Kaiwen Shen
 * 
 *         The interface of Graph implementation
 */
public interface GraphADT {
	/**
	 * Add the new person node to this graph.
	 * 
	 * Valid argument conditions: 1. node is not null 2. node is not in this graph
	 * 
	 * @param person the node need to add
	 * @throws DuplicatePersonException if user already exist in this graph
	 * @return true if successfully add user and false otherwise
	 */
	public boolean addNode(Person person) throws DuplicatePersonException;

	/**
	 * Remove the given person node to this graph.
	 * 
	 * Valid argument conditions: 1. node is not null 2. node is in this graph
	 * 
	 * @param person the node need to remove
	 * @throws PersonNotFoundException if did not find selected user
	 * @return true if successfully add user and false otherwise
	 */
	public boolean removeNode(Person person) throws PersonNotFoundException;

	/**
	 * Add the edge of person1 and person2 to this graph.
	 * 
	 * Valid argument conditions: 1. neither node is null 2. both nodes are in the
	 * graph 3. no edges between two users
	 * 
	 * @param person1 the start of the added edge
	 * @param person2 the destination of the added edge
	 * @throws PersonNotFoundException if did not find selected user
	 * @throws DuplicateEdgesException if there is already edges between users
	 * @return true if successfully add edges and false otherwise
	 */
	public boolean addEdge(Person person1, Person person2) throws PersonNotFoundException, DuplicateEdgesException;

	/**
	 * Remove the edge of person1 and person2 from this graph.
	 * 
	 * Valid argument conditions: 1. neither node is null 2. both nodes are in the
	 * graph 3. the edge between two vertices exists
	 * 
	 * @param person1 the start of the added edge
	 * @param person2 the destination of the added edge
	 * @throws PersonNotFoundException if did not find selected user
	 * @throws NoEdgeExistsException   if there is no edge between selected user
	 * @return true if successfully remove edges and false otherwise
	 */
	public boolean removeEdge(Person person1, Person person2) throws PersonNotFoundException, NoEdgeExistsException;

	/**
	 * Get all the neighbor (adjacent) people of a person
	 * 
	 * @param person given node for finding its edges
	 * @return the list of adjacent edges of the given person or null if did not
	 *         find
	 */
	public List<Person> getNeighbors(Person person);

	/**
	 * Get the corresponding vertex by given name
	 * 
	 * @param personName given name for finding vertex
	 * @return the vertex of given name or null if did not find
	 */
	public Person getNode(String personName);

	/**
	 * Returns a Set that contains all the vertices
	 * 
	 * @return all vertices in the graph
	 */
	public List<Person> getAllNodes();
}
