package com.stackroute.keepnote.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

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
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private ReminderRepository reminderRepository;

	public ReminderServiceImpl(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		reminder.setReminderCreationDate(LocalDateTime.now());
		Reminder tempReminder = reminderRepository.insert(reminder);
		if (tempReminder == null) {
			throw new ReminderNotCreatedException("Unable to Create Reminder");
		}

		return tempReminder;
	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {

		Reminder reminder = reminderRepository.findById(reminderId).get();
		reminderRepository.delete(reminder);
		return (reminder == null) ? false : true;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {

		Reminder tempReminder = reminderRepository.findById(reminderId).get();
		tempReminder.setReminderName(reminder.getReminderName());
		tempReminder.setReminderDescription(reminder.getReminderDescription());
		tempReminder.setReminderId(reminder.getReminderId());
		tempReminder.setReminderCreationDate(LocalDateTime.now());
		tempReminder.setReminderType(reminder.getReminderType());
		reminderRepository.save(tempReminder);
		return tempReminder;
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {

		try {
			Reminder tempReminder = reminderRepository.findById(reminderId).get();

			if (tempReminder == null) {
				throw new ReminderNotFoundException("Reminder Not Found");
			}
			return tempReminder;
		} catch (NoSuchElementException e) {
			throw new ReminderNotFoundException("Reminder Not Found");
		}
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return reminderRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.stackroute.keepnote.service.ReminderService#getAllReminderByUserId(java.lang.String)
	 */
	@Override
	public List<Reminder> getAllReminderByUserId(String userId) {
		// TODO Auto-generated method stub
		return reminderRepository.findReminderByReminderCreatedBy(userId);
	}

}
