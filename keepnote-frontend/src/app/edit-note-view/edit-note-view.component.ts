import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition} from '@angular/material';
import { NotesService } from '../services/notes.service';
import { ReminderService } from '../services/reminder.service';
import { Note } from '../note';

@Component({
  selector: 'app-edit-note-view',
  templateUrl: './edit-note-view.component.html',
  styleUrls: ['./edit-note-view.component.css']
})
export class EditNoteViewComponent implements OnInit {
  note: Note;
  states: Array<string> = ['not-started', 'started', 'completed'];
  errMessage: string;
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  constructor(private dialogRef: MatDialogRef<EditNoteViewComponent>,
    @Inject(MAT_DIALOG_DATA)private data: any,
    private noteService: NotesService,
    private reminderService:ReminderService, public noteActionSnackbar: MatSnackBar) {
}
ngOnInit() {
  this.note = this.data
  console.log(this.note,"On Edit Note Id");
  this.noteService.getNoteByNoteId(this.note.noteId).subscribe(receivedNote=>{
    this.note = receivedNote;
  });
  console.log(this.note,"On NgOnInit Edit Note");
}
  onSave() {
    console.log(this.note,"On Edit Note");
    if(this.note.reminder != null && this.note.reminder.reminderId !=null){
      console.log(this.note.reminder,"update corresponding reminder");
      this.note.reminder.reminderName = this.note.noteTitle;
      this.note.reminder.reminderDescription = this.note.noteContent;
      this.reminderService.updateReminder(this.note.reminder).subscribe(remData=>{

      });
    }
    this.noteService.editNote(this.note).subscribe(
      editNote => {
        this.dialogRef.close({isChanged:true});
        this.showNoteDeleteSnackBar(this.note.noteTitle,'Updated');
        this.note = editNote;
      },
      err => {
      this.errMessage = err.message;
      });
  }
    
  showNoteDeleteSnackBar(noteTitle: string, action: string) {
    this.noteActionSnackbar.open('Note ' + noteTitle + ' ' + action + ' Successfully ', 'Close', {
      duration: 10000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
