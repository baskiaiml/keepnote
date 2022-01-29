import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  MatDialog, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { RouterService } from '../services/router.service';

import { ReminderComponent } from '../reminder/reminder.component';
import { CategoryComponent } from '../category/category.component';
import { Reminder } from '../reminder';
import { ReminderService } from '../services/reminder.service';
import { Category } from '../category';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent implements OnInit {
  note: Note = new Note();
  reminder: Reminder = new Reminder();
  category: Category = new Category();
  notes: any[] = [];
  errMessage: string;
  isReminderChip: Boolean = false;
  isCategoryApplied: Boolean = false;
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  @Output() viewupdate = new EventEmitter<boolean>();
  viewPage = "NOTE"
  constructor(private notesService: NotesService, private reminderService: ReminderService,
    private routerService: RouterService,
    public dialog: MatDialog, public snackBar: MatSnackBar
  ) {
  }
  ngOnInit() {
   
  }
  takeNotes() {
    if (this.validateNote('note')) {

      if (this.isReminderChip) {
        if (this.reminder != null && this.reminder.reminderTime != null) {
          this.reminder.reminderName = this.note.noteTitle;
          this.reminder.reminderDescription = this.note.noteContent;
          this.note.reminder = this.reminder;
        }
      }
      if (this.isCategoryApplied) {
        if(this.category!= null && this.category.categoryName != null){
          this.note.category = this.category;
        }
      }
      //  this.note.reminders.push(this.reminder);
      this.notesService.addNote(this.note).subscribe(
        data => {
          this.openSnackBar('Note ' + this.note.noteTitle);
          this.note = new Note()
          this.viewupdate.emit(true);
          this.isCategoryApplied = false;
          this.isReminderChip = false;
        },
        err => {
          this.errMessage = err.error ? err.error.message : err.message;
          const index: number = this.notes.findIndex(note => note.noteTitle === this.note.noteTitle);
          this.notes.splice(index, 1);
          this.isCategoryApplied = false;
          this.isReminderChip = false;
        });

    }
  }

  openReminderDialog(): void {
      console.log('Open Reminder Dialog');
    if (this.validateNote('reminder')) {
      this.reminder = new Reminder();
      this.reminder.reminderName = this.note.noteTitle;
      this.reminder.reminderDescription = this.note.noteContent;
      const dialogRef = this.dialog.open(ReminderComponent, {
        width: '250px',
        data: this.reminder
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed 1', result);
        this.reminder = result;
        if (this.reminder!=null && this.reminder.reminderDate != null && this.reminder.reminderTime.length != 0) {
          this.isReminderChip = true;
        }
        console.log('The dialog was closed 2', this.reminder);
      });
    }
  }

  openCategoryDialog(): void {
    if (this.validateNote('category')) {
    const dialogRef = this.dialog.open(CategoryComponent, {
      width: '250px',
      data: { categoryData: this.category }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed 1', result);
      this.category = result;
      if (this.category != null && this.category.categoryName.length != 0) {
        this.isCategoryApplied = true;
      }
      console.log('The dialog was closed 2', this.category);
    });
  }
}
  removeReminder(reminder: Reminder): void {
    this.note.reminder = null;
  }

  ngOnChanges() {
    console.log("insert onchange")
  }

  validateNote(dialog: string): Boolean {

    if ((this.note.noteTitle.length > 0) && (this.note.noteContent.length > 0)) {
      return true;
    } else {
      if (dialog == 'note') {
        this.errMessage = 'Title and Text both are required fields';
      } else if (dialog == 'reminder') {
        this.errMessage = 'Please enter Note Title and Text before set a reminder';
      }else if (dialog == 'category') {
        this.errMessage = 'Please enter Note Title and Text before set a category';
      }
      return false;
    }
  }


  openSnackBar(message) {
    this.snackBar.open(message + ' added Successfully', 'Close', {
      duration: 5000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
