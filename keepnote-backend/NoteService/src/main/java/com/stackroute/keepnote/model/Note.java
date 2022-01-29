package com.stackroute.keepnote.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notes")
public class Note {

	/*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). This class should also contain the getters and
	 * setters for the fields along with the no-arg , parameterized constructor and
	 * toString method. The value of createdAt should not be accepted from the user
	 * but should be always initialized with the system date.
	 *
	 */

	@Id
	private String noteId;

	private String noteTitle;

	private String noteContent;

	private String noteStatus;

	private Date noteCreationDate;

	private String noteCreatedBy;

	private Reminder reminder;

	private Category category;

	@Transient
	private Boolean reminderExist = false;

	@Transient
	private Boolean categoryExist = false;

	public Note() {
	}

	/* All the getters/setters definition should be implemented here */

	/**
	 * @return the noteId
	 */

	public String getNoteId() {
		if(noteId == null){
			this.noteId = new ObjectId().toString();
		}

		return this.noteId;
	}

	/**
	 * @param noteId
	 *            the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return the noteTitle
	 */

	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param noteTitle
	 *            the noteTitle to set
	 */
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	/**
	 * @return the noteContent
	 */

	public String getNoteContent() {
		return noteContent;
	}

	/**
	 * @param noteContent
	 *            the noteContent to set
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	/**
	 * @return the noteStatus
	 */

	public String getNoteStatus() {
		return noteStatus;
	}

	/**
	 * @param noteStatus
	 *            the noteStatus to set
	 */
	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}



	/**
	 * @return the noteCreationDate
	 */
	public Date getNoteCreationDate() {
		return noteCreationDate;
	}

	/**
	 * @param noteCreationDate the noteCreationDate to set
	 */
	public void setNoteCreationDate(Date noteCreationDate) {
		this.noteCreationDate = noteCreationDate;
	}

	/**
	 * @return the noteCreatedBy
	 */
	public String getNoteCreatedBy() {
		return noteCreatedBy;
	}

	/**
	 * @param noteCreatedBy the noteCreatedBy to set
	 */
	public void setNoteCreatedBy(String noteCreatedBy) {
		this.noteCreatedBy = noteCreatedBy;
	}

	public Boolean isReminderExist() {
		return reminderExist;
	}

	public void setReminderExist(Boolean isReminderExist) {
		this.reminderExist = isReminderExist;
	}

	public Boolean isCategoryExist() {
		return categoryExist;
	}

	public void setCategoryExist(Boolean isCategoryExist) {
		this.categoryExist = isCategoryExist;
	}

	/**
	 * @return the reminder
	 */
	public Reminder getReminder() {
		return reminder;
	}

	/**
	 * @param reminder the reminder to set
	 */
	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	/* Override the toString() method */

	@Override
	public String toString() {
		return noteId + "-" + noteTitle + "-" + noteStatus;
	}

	@Override
	public boolean equals(Object note) {

		if (note instanceof Note) {
			Note nt = (Note) note;
			return (this.noteId == nt.noteId) ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.noteId.hashCode();
	}

}
