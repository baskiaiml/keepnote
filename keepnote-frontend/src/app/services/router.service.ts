import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
@Injectable()
export class RouterService {

  constructor(private router: Router,
    private location: Location) { }
  routeToDashboard() {
    this.router.navigate(['dashboard']);
  }

  routeToLogin() {
    localStorage.clear();
    this.router.navigate(['login']);
  }

  routeToEditNoteView(noteId) {
    this.router.navigate(['dashboard', {
      outlets: {
        noteEditOutlet: [
          'note', noteId, 'edit'
        ]
      }
    }
  ]);
  }

  routeToEditCategoryView(){
    this.router.navigate(['dashboard/view/categoryEdit']);
  }
  routeBack() {
    this.location.back();
  }

  routeToNoteView() {
    this.router.navigate(['dashboard/view/noteview']);
  }

  routeToRegistration() {
    this.router.navigate(['register']);
  }

  routeToListView() {
    this.router.navigate(['dashboard/view/listview']);
  }
  routeToReminder(){
    this.router.navigate(['dashboard/view/reminderview'])
  }

  routeToNoteDeleteView(noteId){
    this.router.navigate(['dashboard', {
      outlets: {
        noteDeleteOutlet: [
          'note', noteId, 'delete'
        ]
      }
    }
  ]);
  }
}
