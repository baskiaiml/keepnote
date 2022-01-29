package com.stackroute.keepnote.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection="category")
public class Category {

	/*
	 * This class should have five fields
	 * (categoryId,categoryName,categoryDescription,
	 * categoryCreatedBy,categoryCreationDate). This class should also contain the
	 * getters and setters for the fields along with the toString method. The value
	 * of categoryCreationDate should not be accepted from the user but should be
	 * always initialized with the system date.
	 */

	@Id
	private String categoryId;

	
	private String categoryName;

	
	private String categoryDescription;

	
	private String categoryCreatedBy;

	
	private Date categoryCreationDate;

	private Set<Note> notes;

	public Category() {

	}

	public Category(String categoryId, String categoryName, String categoryDescription, Date categoryCreationDate, String categoryCreatedBy, Set<Note> notes) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryCreationDate = categoryCreationDate;
		this.categoryCreatedBy = categoryCreatedBy;
		this.notes = notes;
	}

	public String getCategoryId() {
		if(this.categoryId == null){
			this.categoryId = new ObjectId().toString();
		}
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryCreatedBy() {
		return categoryCreatedBy;
	}

	public void setCategoryCreatedBy(String categoryCreatedBy) {
		this.categoryCreatedBy = categoryCreatedBy;
	}

	public Date getCategoryCreationDate() {
		return categoryCreationDate;
	}

	public void setCategoryCreationDate(Date categoryCreationDate) {
		this.categoryCreationDate = categoryCreationDate;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}


	@Override
	public String toString() {
		return categoryId + "-" + categoryName;
	}

	@Override
	public boolean equals(Object category) {

		if (category instanceof Category) {
			Category catgry = (Category) category;
			return (this.categoryId == catgry.categoryId) ? true : false;
		}
			return false;
	}

	@Override
	public int hashCode() {
		return this.categoryId.hashCode();
	}
}
