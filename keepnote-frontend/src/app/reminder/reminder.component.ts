import { Component, Inject, OnInit } from '@angular/core';
import { RouterService } from '../services/router.service';
import {
  MatDialogRef, MAT_DIALOG_DATA, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';
import { ReminderService } from '../services/reminder.service';
import { Reminder } from '../reminder';
import * as moment from 'moment';
@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.css']
})
export class ReminderComponent implements OnInit {
  errMessage: string;
  reminder: Reminder = new Reminder();
  formattedReminderDate: string;
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  constructor(
    private routerService: RouterService,
    private reminderService: ReminderService,
    public dialogRef: MatDialogRef<ReminderComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Reminder, public reminderSnackBar: MatSnackBar) {
    this.reminder.reminderDescription = data.reminderDescription
    this.reminder.reminderName = data.reminderName
  }
  ngOnInit() {

    this.reminder = this.data;
  }
  onSave() {
    if (this.validateReminder()) {
      console.log(this.data, "reminder on save")
      this.reminderService.addReminder(this.reminder).subscribe(data => {
        this.dialogRef.close(data)
        this.showReminderSnackBar(this.reminder);
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

  validateReminder(): Boolean {
    console.log(this.reminder, 'Reminder');
    console.log(this.reminder.reminderDate, 'Date');
    console.log(this.reminder.reminderTime, 'Time');
    console.log(this.reminder.reminderType, 'Type');
    if (this.reminder.reminderDate == null ||
      this.reminder.reminderTime == null ||
      this.reminder.reminderType == '') {
      console.log('Validation Failed');
      this.errMessage = 'All Fields are Required';
      return false;
    }
    console.log('Validation Passed');
    return true;
  }
}
