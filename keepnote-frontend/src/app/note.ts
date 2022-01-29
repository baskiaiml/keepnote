import { Category } from "./category";
import { Reminder } from "./reminder";
import { DatePipe } from '@angular/common';
export class Note {
  public noteId: Number;
  public noteTitle: string;
  public noteContent: string;
  public noteStatus: string;
  public noteCreationDate: DatePipe;
  public reminder: Reminder;
  public noteCreatedBy: string;
  public category: Category;
  public reminderExist: Boolean = false;
  public categoryExist: Boolean = false;
  constructor(title: string = '', content: string = '', status: string = '') {
    this.noteTitle =title;
    this.noteContent = content;
    this.noteStatus = 'not-started';
    this.reminder = new Reminder();
  }
}
