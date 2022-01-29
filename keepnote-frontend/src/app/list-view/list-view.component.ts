import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.css']
})
export class ListViewComponent implements OnInit {
  notStartedNotes: Array<Note>;
  startedNotes: Array<Note>;
  completedNotes: Array<Note>;
  constructor(private notesService: NotesService) {
    this.notStartedNotes = new Array<Note>();
    this.startedNotes = new Array<Note>();
    this.completedNotes = new Array<Note>();
  }
  ngOnInit() {
    
    this.notesService.getNotes().subscribe(notes => notes.map(note => {
      if (note.noteStatus === 'not-started') {
        this.notStartedNotes.push(note);
      } else if (note.noteStatus === 'started') {
        this.startedNotes.push(note);
      } else if (note.noteStatus === 'completed') {
        this.completedNotes.push(note);
      }
      }));
  }

  ngOnChange(){
    console.log("ONCHAGE--------------")
  }
}
