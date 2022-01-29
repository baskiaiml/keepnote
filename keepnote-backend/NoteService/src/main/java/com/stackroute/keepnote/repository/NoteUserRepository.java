package com.stackroute.keepnote.repository;

import com.stackroute.keepnote.model.NoteUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by baskaran.murugesan on 11/23/2018.
 */
@Repository
public interface NoteUserRepository extends MongoRepository<NoteUser, String> {

}