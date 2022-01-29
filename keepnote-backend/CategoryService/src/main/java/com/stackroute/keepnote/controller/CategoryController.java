package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	/*
	 * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * database. Please note that the careatorId has to be unique.This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the category
	 * 2. 409(CONFLICT) - In case of duplicate categoryId
	 *
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP POST
	 * method".
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/category", //
			method = RequestMethod.POST, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> createCategory(@RequestBody Category reqCategory, HttpSession session) {
		Category category = null;
		try {
			category = categoryService.createCategory(reqCategory);
		} catch (CategoryNotCreatedException e) {

			logger.debug("Unable to create Categoy");
			return new ResponseEntity<Category>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Category>(category,HttpStatus.CREATED);

	}

	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid categoryId without {}
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/category/{id}", //
			method = RequestMethod.DELETE, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") String categoryId) {
		boolean isNoteDeleted = false;
		try {
			isNoteDeleted = categoryService.deleteCategory(categoryId);
		} catch (CategoryDoesNoteExistsException e) {
			logger.debug("Unable to create Categoy:" + e.getMessage());
		}
		return (isNoteDeleted) ? new ResponseEntity<Category>(HttpStatus.OK)
				: new ResponseEntity<Category>(HttpStatus.NOT_FOUND);

	}

	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. This handler method should map to the URL
	 * "/api/v1/category/{id}" using HTTP PUT method.
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/category/{id}", //
			method = RequestMethod.PUT, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> updateCategory(@PathVariable("id") String id, @RequestBody Category reqCategory) {

		Category category = categoryService.updateCategory(reqCategory, id);
		return (category != null) ? new ResponseEntity<Category>(category, HttpStatus.OK)
				: new ResponseEntity<Category>(category, HttpStatus.CONFLICT);

	}

	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully.
	 * 
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP GET
	 * method
	 */
	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/category/{id}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<List<Category>> getCategoryByUserId(@PathVariable("id") String id) {

		List<Category> categoryList = new ArrayList<>();
		try {
			categoryList = categoryService.getAllCategoryByUserId(id);
		} catch (NoSuchElementException e) {
			logger.debug("Unable to find Categoy:" + e.getMessage());
			return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
		}
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);

	}

	@CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3000)
	@RequestMapping(value = "/category/id/{id}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public ResponseEntity<Category> getCategoryByCategoryId(@PathVariable("id") String id) {

		Category category = null;
		try {
			category = categoryService.getCategoryById(id);
		} catch (NoSuchElementException  | CategoryNotFoundException e) {
			logger.debug("Unable to find Categoy:" + e.getMessage());
			return new ResponseEntity<Category>(category, HttpStatus.OK);
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);

	}

}
