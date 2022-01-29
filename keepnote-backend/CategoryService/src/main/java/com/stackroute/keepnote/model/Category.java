package com.stackroute.keepnote.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  */
@Document(collection="category")
public class Category {

	/*
	 * This class should have five fields
	 * (categoryId,categoryName,categoryDescription,
	 * categoryCreatedBy,categoryCreationDate). Out of these five fields, the field
	 * categoryId should be annotated with @Id. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of categoryCreationDate should not
	 * be accepted from the user but should be always initialized with the system
	 * date. 
	 */
	@Id
	private String categoryId;

	private String categoryName;

	private String categoryDescription;

	private String categoryCreatedBy;

	private Date categoryCreationDate;

	public Category() {

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
