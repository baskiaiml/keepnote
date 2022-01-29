package com.stackroute.keepnote.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  */
@Document(collection = "reminder")
public class Reminder {

	/*
	 * This class should have six fields
	 * (reminderId,reminderName,reminderDescription,reminderType,
	 * reminderCreatedBy,reminderCreationDate). Out of these six fields, the field
	 * reminderId should be annotated with @Id. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of reminderCreationDate should not
	 * be accepted from the user but should be always initialized with the system
	 * date.
	 */
	@Id
	private String reminderId;

	private String reminderName;

	private String reminderDescription;

	private String reminderType;

	private String reminderCreatedBy;

	private String reminderTime;
	
	private Date reminderDate;
	
	private LocalDateTime reminderCreationDate;

	public Reminder() {

	}

	public String getReminderId() {
		if(this.reminderId == null) {
			ObjectId id = new ObjectId();
			return id.toString();
		}
		return reminderId;		
	}

	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public String getReminderName() {
		return reminderName;
	}

	public void setReminderName(String reminderName) {
		this.reminderName = reminderName;
	}

	public String getReminderDescription() {
		return reminderDescription;
	}

	public void setReminderDescription(String reminderDescription) {
		this.reminderDescription = reminderDescription;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}

	public String getReminderCreatedBy() {
		return reminderCreatedBy;
	}

	public void setReminderCreatedBy(String reminderCreatedBy) {
		this.reminderCreatedBy = reminderCreatedBy;
	}


	/**
	 * @return the reminderTime
	 */
	public String getReminderTime() {
		return reminderTime;
	}

	/**
	 * @param reminderTime the reminderTime to set
	 */
	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	/**
	 * @return the reminderDate
	 */
	public Date getReminderDate() {
		return reminderDate;
	}

	/**
	 * @param reminderDate the reminderDate to set
	 */
	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	

	/**
	 * @return the reminderCreationDate
	 */
	public LocalDateTime getReminderCreationDate() {
		if(reminderCreationDate == null) {
			reminderCreationDate = LocalDateTime.now();
		}
		return reminderCreationDate;
	}

	/**
	 * @param reminderCreationDate the reminderCreationDate to set
	 */
	public void setReminderCreationDate(LocalDateTime reminderCreationDate) {
		this.reminderCreationDate = reminderCreationDate;
	}

	@Override
	public String toString() {
		return reminderId + "-" + reminderName;
	}

	@Override
	public boolean equals(Object reminder) {

		if (reminder instanceof Reminder) {
			Reminder remndr = (Reminder) reminder;
			return (this.reminderId == remndr.reminderId) ? true : false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.reminderId.hashCode();
	}

}
