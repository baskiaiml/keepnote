package com.stackroute.keepnote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

/*
* This class is implementing the MongoRepository interface for Note.
* Annotate this class with @Repository annotation
* */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	List<Note> findNoteByCategoryCategoryName(String categoryName) ;
}
