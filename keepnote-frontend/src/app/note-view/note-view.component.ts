import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';

@Component({
  selector: 'app-note-view',
  templateUrl: './note-view.component.html',
  styleUrls: ['./note-view.component.css']
})
export class NoteViewComponent implements OnInit {

  @Input()
  isupdate: boolean;
  serverError: string;
  note: Note = new Note();
  notes: Array<Note> = [];
  noteTempList: Array<Note> = [];
  isNoteExist: Boolean = false;
  constructor(private notesService: NotesService) {
    this.notes = new Array<Note>();
  }

  ngOnInit() {
    this.getNotes();
    console.log("Notes Length:" + this.notes.length)
  }
  ngOnChanges() {
    console.log("onchnge")
    this.getNotes();
  }
  getNotes() {
    this.notesService.getNotes().subscribe(
      data => data.map(note => {

        this.notes = data;
        console.log(this.notes,"Subscribed note length");
        if (this.notes.length > 0) {
          this.isNoteExist = true;
        }
      })
      , error => {
        this.serverError = error.error ? error.error.message : error.message;
      });

  }

}
