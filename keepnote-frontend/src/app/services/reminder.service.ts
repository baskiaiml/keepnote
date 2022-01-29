import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Reminder } from '../reminder';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/do';
import { environment } from '../environments/environment';
import { AuthenticationService } from '../services/authentication.service';
import * as moment from 'moment';
@Injectable()
export class ReminderService {
  const_reminder_url = environment.baseURL + ':8081';
  token: any;
  allReminders: Array<Reminder> = new Array<Reminder>();
 
  constructor(private http: HttpClient,
    private authService: AuthenticationService) {
  }

  addReminder(reminder: Reminder): Observable<Reminder> {
    this.token = this.authService.getBearerToken();
    reminder.reminderCreatedBy = this.authService.getCurrentUser();
    console.log("Reminder Service:" + (this.token));
    return this.http.post<Reminder>(this.const_reminder_url + '/api/v1/reminder', reminder);
  }

  updateReminder(reminder: Reminder): Observable<Reminder> {
    this.token = this.authService.getBearerToken();
    reminder.reminderCreatedBy = this.authService.getCurrentUser();
    console.log("Reminder Service:" + (this.token));
    return this.http.put<Reminder>(this.const_reminder_url + '/api/v1/reminder/'+reminder.reminderId, reminder);
  }

  getRemindersList() {
    console.log('Get Notes');
    return  this.http.get<Array<Reminder>>(this.const_reminder_url+'/api/v1/reminder/'+this.authService.getCurrentUser())
  }

  removeReminder(reminderId){
    this.token = this.authService.getBearerToken();
    console.log(reminderId,"Delete Reminder:");
   return this.http.delete<Reminder>(this.const_reminder_url+'/api/v1/reminder/'+reminderId);
  }
}
