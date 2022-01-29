import { Component, Inject, OnInit } from '@angular/core';
import { NotesService } from '../services/notes.service';
import {
  MatDialogRef, MAT_DIALOG_DATA, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';
import { ReminderService } from '../services/reminder.service';
import { Reminder } from '../reminder';
import * as moment from 'moment';
import { Note } from '../note';
@Component({
  selector: 'app-reminder-edit',
  templateUrl: './reminder-edit.component.html',
  styleUrls: ['./reminder-edit.component.css']
})
export class ReminderEditComponent implements OnInit {

  errMessage: string;
  isReminderUpdate: Boolean = false;
  note: Note = new Note();
  formattedReminderDate: string;
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  constructor(
    private noteService: NotesService,
    private reminderService: ReminderService,
    public dialogRef: MatDialogRef<ReminderEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Note, public reminderSnackBar: MatSnackBar) {
    if (data.reminder != null && data.reminder.reminderName != '') {
      this.isReminderUpdate = true;
      this.note = data;
    } else {
      this.note = data;
      this.note.reminder = new Reminder();
    }

  }
  ngOnInit() {

    this.note = this.data;
    this.note.reminder = this.data.reminder;
  }
  onReminderUpdate(reminderUpdatedNote: Note) {
    if (this.validateReminder(reminderUpdatedNote.reminder)) {
      console.log(this.data, " On reminder on update", reminderUpdatedNote)
      this.reminderService.updateReminder(reminderUpdatedNote.reminder).subscribe(data => {
        this.dialogRef.close(data)
      })

      this.noteService.editNote(this.note).subscribe(data => {
        this.dialogRef.close(data)
        this.showReminderSnackBar(this.note.reminder);
      })
    }
  }

  onReminderSave(reminderAddedNote: Note) {
    if (this.validateReminder(reminderAddedNote.reminder)) {
    console.log(reminderAddedNote, " On reminder on edited")
    console.log(this.note, " On reminder on save")
    this.note.reminder.reminderDescription = this.note.noteContent;
    this.note.reminder.reminderName = this.note.noteTitle;
    this.reminderService.addReminder(this.note.reminder).subscribe(data => {
      this.dialogRef.close(data)
    })

    this.noteService.editNote(this.note).subscribe(data => {
      this.dialogRef.close(data)
      this.showReminderSnackBar(this.note.reminder);
    })
  }
  }

  showReminderSnackBar(reminder: Reminder) {
    const reminderMsg = reminder.reminderDate + ' ' + reminder.reminderTime;
    this.reminderSnackBar.open('Reminder Set on ' + reminderMsg, 'Close', {
      duration: 10000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
  validateReminder(reminder: Reminder): Boolean {
    console.log(reminder, 'Reminder');
    console.log(reminder.reminderDate, 'Date');
    console.log(reminder.reminderTime, 'Time');
    console.log(reminder.reminderType, 'Type');
    if (reminder.reminderDate == null ||
      reminder.reminderTime == null ||
      reminder.reminderType == '') {
      console.log('Validation Failed');
      this.errMessage = 'All Fields are Required';
      return false;
    }
    console.log('Validation Passed');
    return true;
  }
}
