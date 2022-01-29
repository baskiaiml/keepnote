import { Time } from "@angular/common";

export class Reminder {

reminderId: Number;
reminderName: string;
reminderDescription: string;
reminderType: string;
reminderCreatedBy: string;
reminderTime: string;
reminderDate: Date;
constructor() {
    this.reminderName = '';
    this.reminderType = '';
    this.reminderDescription = '';
    this.reminderDate = null;
    this.reminderTime = null;
}
}
