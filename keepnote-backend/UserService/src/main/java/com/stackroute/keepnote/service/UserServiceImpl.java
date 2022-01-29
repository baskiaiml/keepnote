package com.stackroute.keepnote.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	public User registerUser(User user) throws UserAlreadyExistsException {

		boolean isUserExist = userRepository.existsById(user.getUserId());
		if(isUserExist){
			throw new UserAlreadyExistsException("User Id "+user.getUserId()+" Already Exist");
		}
		user.setUserAddedDate(new Date());
		userRepository.insert(user);
		return user;
	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId,User user) throws UserNotFoundException {
		User tempUser = userRepository.findById(userId).get();
		if(tempUser == null){
			throw new UserNotFoundException("User not Found");
		}
		tempUser.setLastName(user.getLastName());
		tempUser.setFirstName(user.getFirstName());
		tempUser.setUserPassword(user.getUserPassword());
		tempUser.setUserAddedDate(new Date());
		tempUser.setEmailId(user.getEmailId());
		tempUser.setMobileNumber(user.getMobileNumber());
		userRepository.save(tempUser);
		return tempUser;
	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {
		User tempUser = userRepository.findById(userId).get();
		if(tempUser == null){
			throw new UserNotFoundException("User not Found");
		}
		 userRepository.deleteById(userId);
		return true;
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {

		User user = userRepository.findById(userId).get();
		if(user == null){
			throw new UserNotFoundException("User not Found");
		}
		return user;
	}

}
