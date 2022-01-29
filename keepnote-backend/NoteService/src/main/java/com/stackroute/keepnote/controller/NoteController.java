package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */


@RestController
@RequestMapping(value = "/api/v1")
public class NoteController {

	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */

	private NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Note> createNote(@RequestBody Note reqNote, HttpSession session) {

		boolean isNoteCreated = noteService.createNote(reqNote);
		return (isNoteCreated) ? new ResponseEntity<Note>(HttpStatus.CREATED)
				: new ResponseEntity<Note>(HttpStatus.CONFLICT);

	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid noteId without {}
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/{userId}/{id}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Note> deleteNote(@PathVariable("userId") String userId, @PathVariable("id") String noteId) {
		boolean isNoteDeleted = noteService.deleteNote(userId, noteId);
		return (isNoteDeleted) ? new ResponseEntity<Note>(HttpStatus.OK)
				: new ResponseEntity<Note>(HttpStatus.NOT_FOUND);

	}
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/{userId}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Note> deleteAllNoteByUserId(@PathVariable("userId") String userId) {
		boolean isNoteDeleted = false;
		try {
			isNoteDeleted = noteService.deleteAllNotes(userId);
		} catch (NoteNotFoundExeption e) {
			e.printStackTrace();
		}
		return (isNoteDeleted) ? new ResponseEntity<Note>(HttpStatus.OK)
				: new ResponseEntity<Note>(HttpStatus.NOT_FOUND);

	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT
	 * method.
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/{userId}/{id}", //
			method = RequestMethod.PUT, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Note> updateNote(@PathVariable("userId") String userId, @PathVariable("id") String id,
			@RequestBody Note reqNote) {

		try {
			Note note = noteService.updateNote(reqNote, id, userId);
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.info("User Not Found: " + e.getMessage());
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// logger.info("Unexpected exception during update: "+e.getMessage());
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET
	 * method
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/{userId}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable("userId") String userId) {

		List<Note> noteList = noteService.getAllNoteByUserId(userId);
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/{userId}/{id}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Note> getNoteByUserId(@PathVariable("userId") String userId, @PathVariable("id") String id) {

		try {
			Note note = noteService.getNoteByNoteId(userId, id);
			return (note == null) ? new ResponseEntity<Note>(HttpStatus.NOT_FOUND)
					: new ResponseEntity<Note>(note, HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.info("User Not Found: " + e.getMessage());
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * @param categoryId
	 * @return
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/note/category/{categoryName}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Note>> getNotesByCategory(@PathVariable("categoryName") String categoryName) {

		try {
			List<Note> note = noteService.getNotesByCategory(categoryName);
			return (note == null) ? new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND)
					: new ResponseEntity<List<Note>>(note, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.info("User Not Found: " + e.getMessage());
			return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
		}
	}

}
