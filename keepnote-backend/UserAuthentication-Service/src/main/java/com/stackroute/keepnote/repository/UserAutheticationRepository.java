package com.stackroute.keepnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.User;


/*
* This class is implementing the JpaRepository interface for User.
* Annotate this class with @Repository annotation
* */
@Repository
public interface UserAutheticationRepository extends JpaRepository<User, String> {



/*
	* Apart from the standard CRUD methods already available in JPA Repository, based
	* on our requirements, we might need to create few query methods for getting 
	* specific data from the database. 
	* */
	
	/*
	* This method will validate a user from database by username and password. 
    */
    @Query("SELECT u FROM User u WHERE u.userId=:userId AND u.userPassword=:password")
    User findByUserIdAndUserPassword(@Param("userId") String userId, @Param("password") String userPassword);
    
    @Query("SELECT u FROM User u WHERE u.id=:id")
    User findByUserId(@Param("id") Integer id);
}
