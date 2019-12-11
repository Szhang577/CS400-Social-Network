/////////////////////////////// File Header ///////////////////////////////////
//
// Assignment name: ateam Final Project - Social Network
// Author(s) and email addresses: 
// 				Erzhen Zhang, ezhang25@wisc.edu
//				Ruokai Yin, ryin25@wisc.edu	
//				Seanna Zhang, szhang577@wisc.edu
//				Kaiwen Shen, kshen26@wisc.edu
// Due date: Dec 11th
// Other source credits:
// Known bugs: None
//
///////////////////////////////////////////////////////////////////////////////
package application;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Filename: SocialNetworkADT.java Project: ateam
 * 
 * @author Erzhen Zhang, Ruokai Yin, Seanna Zhang, Kaiwen Shen
 * 
 *         SocialNetwork is used to represent a graph manager to do some
 *         operation associated with the graph such as add users, remove users,
 *         etc.
 */
public interface SocialNetworkADT {
	/**
	 * Add the friendship between two users in the social net work
	 * 
	 * @param String person1 first user
	 * @param String person2 second user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws DuplicateEdgesException  throw if the friendship already existed
	 * @return boolean true if add successfully, otherwise false
	 */
	public boolean addFriends(String person1, String person2)
			throws DuplicatePersonException, PersonNotFoundException, DuplicateEdgesException;

	/**
	 * Remove the friendship between two users in the social net work
	 * 
	 * @param String person1 first user
	 * @param String person2 second user
	 * @throws DuplicatePersonException throw if these two users are same
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws NoEdgeExistsException    throw if the friendship does not exist
	 * @return boolean true if removed successfully, otherwise false
	 */
	public boolean removeFriends(String person1, String person2)
			throws PersonNotFoundException, NoEdgeExistsException, DuplicatePersonException;

	/**
	 * Add a user into the social net work
	 * 
	 * @param String person user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @return boolean true if added successfully, otherwise false
	 */
	public boolean addUser(String person) throws DuplicatePersonException;

	/**
	 * Remove a user out of the social net work
	 * 
	 * @param String person user
	 * @throws DuplicatePersonException throw if the user already in the network
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws NoEdgeExistsException    throw if the friendship does not exist
	 * @return boolean true if removed successfully, otherwise false
	 */
	public boolean removeUser(String person) throws PersonNotFoundException, NoEdgeExistsException;

	/**
	 * Get a list of friends of one specific user
	 * 
	 * @param String person user
	 * @return List<Person> List of friends
	 */
	public List<Person> getFriends(String person);

	/**
	 * Get a list of Mutual Friends of two users
	 * 
	 * @param String person1 user1
	 * @param String person2 user2
	 * @throws DuplicatePersonException throw if these two persons are same
	 * @return List<Person> List of mutual friends
	 */
	public List<Person> getMutualFriends(String person1, String person2) throws DuplicatePersonException;

	/**
	 * Get a list of shortest path of two users
	 * 
	 * @param String person1 user1
	 * @param String person2 user2
	 * @throws DuplicatePersonException throw if these two persons are same
	 * @return List<Person> List of shortest path
	 */
	public List<Person> getShortestPath(String person1, String person2) throws DuplicatePersonException;

	/**
	 * Get the number of connected components
	 * 
	 * @return int number of connected components
	 */
	public int getConnectedComponents();

	/**
	 * Load a file into the social net work
	 * 
	 * @param File file file loaded into social network
	 * @throws DuplicatePersonException throw if these two users are same
	 * @throws PersonNotFoundException  throw if the user can not be found
	 * @throws FileNotFoundException    throw if the file can not be founded
	 * @throws DuplicateEdgesException  throw if the edge already existed
	 * @throws NoEdgeExistsException    throw if the edge can not be found
	 */
	public void loadFromFile(File file) throws FileNotFoundException, DuplicatePersonException, PersonNotFoundException,
			DuplicateEdgesException, NoEdgeExistsException;

	/**
	 * Save a file associated with the social net work
	 * 
	 * @param File file file loaded into social network
	 * @throws IOException throw if there is a IOException
	 */
	public void saveToFile(File file) throws IOException;
}
