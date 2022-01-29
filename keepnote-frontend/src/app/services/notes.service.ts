import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Note } from '../note';
import { Reminder } from '../reminder';
import { Category } from '../category';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/do';

import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AuthenticationService } from '../services/authentication.service';
import { environment } from '../environments/environment';
import { ReminderService } from './reminder.service';

@Injectable()
export class NotesService {
  const_note_url=environment.baseURL+':8082'; 
  notes: Array<Note>;
  notesSubject: BehaviorSubject<Array<Note>>;
  token: any;
  currentUser: any;
  searchOption=[];
  public notesData: Note[] 

  constructor(private http: HttpClient,
    private authService: AuthenticationService,private reminderService: ReminderService) {
    this.notes = [];
    this.notesSubject = new BehaviorSubject(this.notes);
    this.fetchNotesFromServer();
  }
  fetchNotesFromServer() {
    console.log('Fetch Notes');
    this.token = this.authService.getBearerToken();
    return this.http.get<Array<Note>>(this.const_note_url+'/api/v1/note/admin').subscribe(notes => {
      this.notes = notes;
      this.notesSubject.next(this.notes);
    }, err => {
    });
  }

  // getNotes(): Observable<Array<Note>> {
  //   console.log('Get Notes');
  
  //   return this.notesSubject.catch((error: HttpErrorResponse) => {
  //     console.error('An error occurred:', error.error);
  //     return Observable.throw(error);
  //   });
  // }
  getNotes() {
    console.log('Get Notes');
  
    return  this.http.get<Array<Note>>(this.const_note_url+'/api/v1/note/'+this.authService.getCurrentUser());
  }

  addNote(note: Note): Observable<Note> {
 
      console.log('Add note'+note);
      console.log('Add note'+this.authService.getCurrentUser());
      note.noteCreatedBy = this.authService.getCurrentUser();
      return this.http.post<Note>(this.const_note_url+'/api/v1/note', note)
    // .do( addedNote => {
    //   this.notes.push(addedNote);
    //   this.notesSubject.next(this.notes);
    // });
  }

  editNote(note: Note): Observable<Note> {
    console.log('Bearer'+this.token);
    return this.http.put<Note>(this.const_note_url+'/api/v1/note/'+this.authService.getCurrentUser()+'/'+note.noteId, note).do( editedNote => {
      const noteObject = this.notes.find(noteObj => noteObj.noteId === editedNote.noteId);
      Object.assign(noteObject , editedNote);
      this.notesSubject.next(this.notes);
    });
  }

  getNoteById(noteId): Note {
    console.log('get note by id');
    const noteObject = this.notes.find(note => note.noteId === noteId);
    return Object.assign({}, noteObject);
  }

  getNoteByNoteId(noteId): Observable<Note> {
    return this.http.get<Note>(this.const_note_url+'/api/v1/note/'+this.authService.getCurrentUser()+'/'+noteId);
  }

  getNotesByCategoryId(category: Category): Observable<Array<Note>> {
    this.token = this.authService.getBearerToken();
    console.log("getNotesByCategoryId Service:"+(this.token));
    category.categoryCreatedBy=this.authService.getCurrentUser();
   return this.http.get<Array<Note>>(this.const_note_url+'/api/v1/note/category/'+category.categoryName);
  }

  removeNote(note: Note){
    this.token = this.authService.getBearerToken();
    this.currentUser = this.authService.getCurrentUser();
    console.log(note.noteId,"note id to be deleted:");
    if(note.reminder !=null ){
      console.log("Delete Reminder if exist")
      this.reminderService.removeReminder(note.reminder.reminderId);
    }
   return this.http.delete<Note>(this.const_note_url+'/api/v1/note/'+this.currentUser+"/"+note.noteId);
  }

  filteredListOptions() {
    let notes = this.notes;
        let filteredPostsList = [];
        for (let note of notes) {
            for (let notses of this.searchOption) {
                if (notses.noteTitle === note.noteTitle) {
                  filteredPostsList.push(note);
                }
            }
        }
        console.log(filteredPostsList);
        return filteredPostsList;
  }
}
