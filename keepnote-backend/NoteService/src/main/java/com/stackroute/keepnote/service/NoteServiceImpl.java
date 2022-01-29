package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;
import com.stackroute.keepnote.repository.NoteUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
public class NoteServiceImpl implements NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private NoteRepository noteRepository;
	private NoteUserRepository noteUserRepository;

	@Autowired
	public NoteServiceImpl(NoteRepository repository,NoteUserRepository noteUserRepository) {
		this.noteRepository = repository;
		this.noteUserRepository = noteUserRepository;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		boolean isNoteUserExist = noteUserRepository.existsById(note.getNoteCreatedBy());
		note.setNoteCreationDate(new Date());
		if (isNoteUserExist) {
			NoteUser tempNoteUser = noteUserRepository.findById(note.getNoteCreatedBy()).get();
			tempNoteUser.setUserId(note.getNoteCreatedBy());
			tempNoteUser.getNotes().add(note);
			noteRepository.save(note);
			return noteUserRepository.save(tempNoteUser) == null ? false : true;
		} else {
			NoteUser newNote = new NoteUser();
			newNote.setUserId(note.getNoteCreatedBy());
			newNote.getNotes().add(note);
			noteRepository.save(note);
			return noteUserRepository.insert(newNote) == null ? false : true;
		}
	}

	/* This method should be used to delete an existing note. */
	public boolean deleteNote(String userId, String noteId) {
		NoteUser tempNoteUser = noteUserRepository.findById(userId).get();
		Note tempNote = null;
		if (tempNoteUser == null) {
			return false;
		}
		for (Note note : tempNoteUser.getNotes()) {
			if (note.getNoteId().equals(noteId)) {
				tempNote = note;
				break;
			}
		}
		tempNoteUser.getNotes().remove(tempNote);
		noteRepository.delete(tempNote);
		noteUserRepository.save(tempNoteUser);
		return true;
	}

	/* This method should be used to delete all notes with specific userId. */
	public boolean deleteAllNotes(String userId) {
		NoteUser tempNoteUser = noteUserRepository.findById(userId).get();
		if (tempNoteUser == null) {
			return false;
		}
		noteUserRepository.delete(tempNoteUser);
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, String id, String userId) throws NoteNotFoundExeption {
		try {
			NoteUser tempNoteUser = noteUserRepository.findById(userId).get();
			Note tempNote = null;
			if (tempNoteUser == null) {
				throw new NoteNotFoundExeption("Note not Found for user: " + userId);
			}
			for (Note _note : tempNoteUser.getNotes()) {
				if (_note.getNoteId().equals(id)) {
					tempNote = _note;
					break;
				}
			}
			tempNoteUser.getNotes().remove(tempNote);
			note.setNoteCreationDate(new Date());
			tempNoteUser.getNotes().add(note);
			noteRepository.save(note);
			noteUserRepository.save(tempNoteUser);
		} catch (NoSuchElementException e) {
			throw new NoteNotFoundExeption(e.getMessage());
		}
		return note;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, String noteId) throws NoteNotFoundExeption {
		Note tempNote = null;
		try {
			NoteUser tempNoteUser = noteUserRepository.findById(userId).get();

			if (tempNoteUser == null) {
				throw new NoteNotFoundExeption("Note not Found for user: " + userId);
			}
			for (Note _note : tempNoteUser.getNotes()) {
				if (_note.getNoteId().equals(noteId)) {
					tempNote = _note;
					break;
				}
			}

			if (tempNote == null) {
				throw new NoteNotFoundExeption("Note not Found for user: " + userId);
			}
		} catch (NoSuchElementException e) {
			throw new NoteNotFoundExeption(e.getMessage());
		}
		return tempNote;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		boolean isNoteExist =  noteUserRepository.existsById(userId);
		if(!isNoteExist){
			return new ArrayList<Note>();
		}
		NoteUser tempNoteUser = noteUserRepository.findById(userId).get();
		for(Note note : tempNoteUser.getNotes()){

			if(note.getReminder() !=null && !"".equals(note.getReminder().getReminderName())){
				note.setReminderExist(true);
			}
			if(note.getCategory() !=null && !"".equals(note.getCategory().getCategoryName())){
				note.setCategoryExist(true);
			}
		}
		return tempNoteUser.getNotes();
	}

	/* (non-Javadoc)
	 * @see com.stackroute.keepnote.service.NoteService#getNotesByCategoryId(java.lang.String)
	 */
	@Override
	public List<Note> getNotesByCategory(String categoryName) {
		// TODO Auto-generated method stub
		return noteRepository.findNoteByCategoryCategoryName(categoryName);
	}
	
	

}
