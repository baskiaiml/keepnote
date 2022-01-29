package com.stackroute.keepnote.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.ReminderService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/v1")
public class ReminderController {

	private static final Logger logger = LoggerFactory.getLogger(ReminderController.class);
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement five functionalities regarding reminder. They are as
	 * following:
	 * 
	 * 1. Create a reminder 2. Delete a reminder 3. Update a reminder 4. Get all
	 * reminders by userId 5. Get a specific reminder by id.
	 * 
	 */

	private ReminderService reminderService;

	/*
	 * Autowiring should be implemented for the ReminderService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	@Autowired
	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	/*
	 * Define a handler method which will create a reminder by reading the
	 * Serialized reminder object from request body and save the reminder in
	 * database. Please note that the reminderId has to be unique. This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the reminder
	 * 2. 409(CONFLICT) - In case of duplicate reminder ID
	 *
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP POST
	 * method".
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/reminder", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reqReminder, HttpSession session) {
		Reminder reminder = null;
		try {
			reminder = reminderService.createReminder(reqReminder);
		} catch (ReminderNotCreatedException e) {

			logger.debug("Unable to create Reminder");
			return new ResponseEntity<Reminder>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Reminder>(reminder,HttpStatus.CREATED);

	}

	/*
	 * Define a handler method which will delete a reminder from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the reminder with specified reminderId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid reminderId without {}
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/reminder/{id}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Reminder> deleteReminder(@PathVariable("id") String reminderId) {
		boolean isReminderDeleted = false;
		try {
			isReminderDeleted = reminderService.deleteReminder(reminderId);
		} catch (ReminderNotFoundException e) {
			logger.debug("Unable to create Reminder:" + e.getMessage());
		}
		return (isReminderDeleted) ? new ResponseEntity<Reminder>(HttpStatus.OK)
				: new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);

	}

	/*
	 * Define a handler method which will update a specific reminder by reading the
	 * Serialized object from request body and save the updated reminder details in
	 * a database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the reminder updated
	 * successfully. 2. 404(NOT FOUND) - If the reminder with specified reminderId
	 * is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * PUT method.
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/reminder/{id}", //
			method = RequestMethod.PUT, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Reminder> updateReminder(@PathVariable("id") String reminderId,
			@RequestBody Reminder reqReminder) {

		Reminder reminder = null;
		try {
			reminder = reminderService.updateReminder(reqReminder, reminderId);
		} catch (ReminderNotFoundException e) {
			logger.debug("Unable to find Reminder:" + e.getMessage());
		}
		return (reminder != null) ? new ResponseEntity<Reminder>(reminder, HttpStatus.OK)
				: new ResponseEntity<Reminder>(reminder, HttpStatus.NOT_FOUND);

	}

	/*
	 * Define a handler method which will show details of a specific reminder. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder found successfully. 2.
	 * 404(NOT FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * GET method where "id" should be replaced by a valid reminderId without {}
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/reminder/{id}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Reminder>> getReminderByUserId(@PathVariable("id") String id) {

		List<Reminder> reminderList = null;
		try {
			reminderList = reminderService.getAllReminderByUserId(id);
		} catch (NoSuchElementException e) {
			logger.debug("Unable to find Reminder:" + e.getMessage());
			return new ResponseEntity<List<Reminder>>(reminderList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Reminder>>(reminderList, HttpStatus.OK);

	}

	/*
	 * Define a handler method which will get us the all reminders. This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 200(OK) - If the reminder found successfully. 2. 404(NOT
	 * FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP GET
	 * method
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/reminder", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Reminder>> getAllReminders() {

		List<Reminder> reminderList = null;

		reminderList = reminderService.getAllReminders();
		return (reminderList != null && reminderList.size() > 0)
				? new ResponseEntity<List<Reminder>>(reminderList, HttpStatus.OK)
				: new ResponseEntity<List<Reminder>>(reminderList, HttpStatus.NOT_FOUND);

	}
}
