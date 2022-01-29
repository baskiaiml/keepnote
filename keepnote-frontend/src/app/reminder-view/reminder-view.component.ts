import { Component, OnInit } from '@angular/core';
import { ReminderService } from '../services/reminder.service';
import { Reminder } from '../reminder';
import * as moment from 'moment';

@Component({
  selector: 'app-reminder-view',
  templateUrl: './reminder-view.component.html',
  styleUrls: ['./reminder-view.component.css']
})

export class ReminderViewComponent implements OnInit {

  serverError: string;
  reminder: Reminder = new Reminder();
  reminderList: Array<Reminder> = [];
  firedReminders: Array<Reminder> = new Array<Reminder>();
  todaysReminders: Array<Reminder> = new Array<Reminder>();
  upcomingReminders: Array<Reminder> = new Array<Reminder>();

  constructor(private reminderService: ReminderService) { }

  ngOnInit() {
    this.getRemindersList()
  }

  getRemindersList() {
    return this.reminderService.getRemindersList().subscribe(
      reminders => reminders.map(entry => {
        if (moment(entry.reminderDate).isBefore(new Date())) {
          this.firedReminders.push(entry);
        } else if (moment(entry.reminderDate).isSame(new Date())) {
          this.todaysReminders.push(entry);
        } else if (moment(entry.reminderDate).isAfter(new Date())) {
          this.upcomingReminders.push(entry);
        }
      }
      ),error=>{
        this.serverError = error.error ? error.error.message : error.message;
      });

  }
}