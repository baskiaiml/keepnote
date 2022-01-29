import { Component, OnInit,Inject } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { MAT_DIALOG_DATA,MatDialogRef,MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition} from '@angular/material';
import { ReminderService } from '../services/reminder.service';

@Component({
  selector: 'app-note-delete',
  templateUrl: './note-delete.component.html',
  styleUrls: ['./note-delete.component.css']
})
export class NoteDeleteComponent implements OnInit {

  note:  Note = new Note();
  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  constructor(@Inject(MAT_DIALOG_DATA) public data: Note, private noteService: NotesService,
  private reminderService: ReminderService,
  public dialogRef: MatDialogRef<NoteDeleteComponent>, public noteActionSnackbar: MatSnackBar) {
    this.note = data
  }
  ngOnInit() {
  }

  onDeleteNote(){
    console.log("On Note Delete")
    if(this.note.reminder != null && this.note.reminder.reminderId !=null){
      this.reminderService.removeReminder(this.note.reminder.reminderId).subscribe(remData=>{

      })
    }
    
    this.noteService.removeNote(this.note).subscribe(data=>{
      this.dialogRef.close(data);
      this.showNoteDeleteSnackBar(this.note.noteTitle,'Delete');
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
