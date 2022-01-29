package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/v1")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * This handler method should map to the URL "/user" using HTTP POST method
	 */
	 @RequestMapping(value = "/user", //
	            method = RequestMethod.POST, //
	            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public ResponseEntity<User> registerUser(@RequestBody User reqUser, HttpSession session) {
		 try {
			userService.registerUser(reqUser);
			return new ResponseEntity<User>(HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			logger.info("User Already Exist: "+e.getMessage());
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		 
	    }
	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the user updated successfully.
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP PUT
	 * method.
	 */
	 @RequestMapping(value = "/user/{id}", //
	            method = RequestMethod.PUT, //
	            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public ResponseEntity<User> updateUserDetails(@PathVariable("id") String id, @RequestBody User reqUser) {
		 
		
		 try {
			User user = userService.updateUser(id,reqUser);
				return (user==null)?new ResponseEntity<User>(HttpStatus.NOT_FOUND):new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
		    logger.info("User Not Found: "+e.getMessage());
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		 catch (Exception e) {
			//	logger.info("Unexpected exception during update: "+e.getMessage());
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
	      
	    }
	/*
	 * Define a handler method which will delete a user from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid userId without {}
	 */
	 @RequestMapping(value = "/user/{id}", //
	            method = RequestMethod.DELETE, //
	            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public ResponseEntity<User> deleteUser(@PathVariable("id") String userId, HttpSession session) {
			Boolean isDeleted = false;
			try {
				isDeleted = userService.deleteUser(userId);
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
			return (isDeleted)?new ResponseEntity<User>(HttpStatus.OK):new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	    }
	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user found successfully. 2. 404(NOT
	 * FOUND) - If the user with specified userId is not found. This handler method
	 * should map to the URL "/api/v1/user/{id}" using HTTP GET method where "id"
	 * should be replaced by a valid userId without {}
	 */
	 @RequestMapping(value = "/user/{id}", //
	            method = RequestMethod.GET, //
	            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	    @ResponseBody
	    public ResponseEntity<User> getUserDetails(@PathVariable("id") String userId, HttpSession session) {
		 
		 try {
			User user = userService.getUserById(userId);
			return (user==null)?new ResponseEntity<User>(HttpStatus.NOT_FOUND):new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			logger.info("User Not Found: "+e.getMessage());
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	      
	    }
}
