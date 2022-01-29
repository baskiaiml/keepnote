package com.stackroute.keepnote.service;

import java.util.List;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;


public interface NoteService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */


    boolean createNote(Note note);

    boolean deleteNote(String userId, String noteId);

    boolean deleteAllNotes(String userId) throws NoteNotFoundExeption;

    Note updateNote(Note note, String id, String userId) throws NoteNotFoundExeption;

    Note getNoteByNoteId(String userId,String noteId) throws NoteNotFoundExeption;

    List<Note> getAllNoteByUserId(String userId);
    
    List<Note> getNotesByCategory(String categoryName);


}
