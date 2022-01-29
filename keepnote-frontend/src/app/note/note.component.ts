import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Note } from '../note';
import { Reminder } from '../reminder';
import { Category } from '../category';
import { RouterService } from '../services/router.service';
import { NotesService } from '../services/notes.service';
import {
  MatDialog,MatSnackBar,MatSnackBarHorizontalPosition,MatSnackBarVerticalPosition
} from '@angular/material';
import { NoteDeleteComponent } from '../note-delete/note-delete.component';
import { EditNoteViewComponent } from '../edit-note-view/edit-note-view.component';
import { ReminderEditComponent } from '../reminder-edit/reminder-edit.component';
import { CategorySelectComponent } from '../category-select/category-select.component';

import { ReminderService } from '../services/reminder.service';
import { CategoryService } from '../services/category.service';



@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {
  @Input() note: Note;
  @Input() reminder: Reminder;
  @Input() category: Category;
  @Output() getNotes  = new EventEmitter()
  isRemoveReminderClicked: Boolean = false;
  isRemoveCategoryClicked: Boolean = false;
  serverError: any;
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  constructor(private routerService: RouterService, public dialog: MatDialog,
    private notesService: NotesService,
    private reminderService: ReminderService,
    private categoryService: CategoryService,
    public snackBar: MatSnackBar) {
  }

  openEditView(note) {
    console.log(note, ":WhenO pen Edit")
    this.note = note;
    const dialogRef = this.dialog.open(EditNoteViewComponent, {
      width: '250px',
      data: this.note
    });

    dialogRef.afterClosed().subscribe(data=>{
      console.log(data);
      if(data.isChanged){
        this.getNotes.emit();
      }
    })
  }

  ngOnInit() {
  }

  openDeleteConfirmDialog(note): void {
    console.log(note, ":On note delete note Id")
    this.note = note;
    const dialogRef = this.dialog.open(NoteDeleteComponent, {
      width: '250px',
      data: this.note
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
        this.getNotes.emit();
    });
  }
  onClickOnAddReminder(note){
    console.log(note, ":On note delete note Id")
    this.reminder = note;
    const dialogRef = this.dialog.open(ReminderEditComponent, {
      width: '250px',
      data: note
    });
    dialogRef.afterClosed().subscribe(result => {
      this.showActionCompleteMessage('Reminder','Added');
      console.log('The dialog was closed');
     // this.note= result;
     if(result){
      this.getNotes.emit();
     }
     
    });
  }
  onClickOnEditReminder(note): void {
    console.log(note, ":On note delete note Id")
    this.note = note;
    const dialogRef = this.dialog.open(ReminderEditComponent, {
      width: '250px',
      data: note
    });
    dialogRef.afterClosed().subscribe(result => {
      console.error('The dialog onClickOnEditReminder was closed');
      this.showActionCompleteMessage('Reminder','Updated');
      if(result){
        this.getNotes.emit();
       }
    //  this.note = result;
    }, err => {
      console.log('error has occured while fetching note list');
      this.serverError = err.error.message;
    });
  }

  onClickOnRemoveReminder(updatedNote: Note) {
    console.log(updatedNote,"Reminder id to be removed");
    this.reminderService.removeReminder(updatedNote.reminder.reminderId).subscribe(data => {
      if (data == null) {
        console.log("Reminder Removed Successfuly");
        updatedNote.reminder = null;
        this.notesService.editNote(updatedNote).subscribe(data => {
          this.note = data;
          this.showActionCompleteMessage('Reminder','removed');
          this.getNotes.emit();
        },
          err => {
            console.log('error has occured while fetching note list');
            this.serverError = err.error.message;
          })
       
      }
    },
      err => {
        console.log('error has occured while performing reminder remove');
        this.serverError = err.error.message;
      })
   //

  }
  onClickSetCategory(note:Note): void {
    console.log(note, ":On Set Category")
    this.note = note;
    const dialogRef = this.dialog.open(CategorySelectComponent, {
      width: '250px',
      data: note
    });
    dialogRef.afterClosed().subscribe(result => {
      this.showActionCompleteMessage('Category','Applied');
      console.log('The dialog was closed');
      this.getNotes.emit();
     // this.note= result;
    });

  }

  onClickChangeCategory(note:Note): void {
    console.log(note, ":On Change Category")
    this.note = note;
    const dialogRef = this.dialog.open(CategorySelectComponent, {
      width: '250px',
      data: note,
    });
    dialogRef.afterClosed().subscribe(result => {
      this.showActionCompleteMessage('Category','Changed');
      console.log('The dialog was closed');
      this.getNotes.emit();
 // this.note.category = result;
    });

  }

  onClickRemoveCategory(updatedNote: Note) {
    updatedNote.category = null;
    this.notesService.editNote(updatedNote).subscribe(data => {
      this.note = data;
      this.showActionCompleteMessage('Category','Removed');
      this.getNotes.emit();
    },
      err => {
        console.log('error has occured while fetching note list');
        this.serverError = err.error.message;
      })

  }


  showActionCompleteMessage(component: string, message: string) {
    this.snackBar.open(component + ' '+ message + ' Successfully ', 'Close', {
      duration: 10000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}