import { Component, OnInit, Output,EventEmitter } from '@angular/core';
import { NotesService } from '../services/notes.service';
import { CategoryService } from '../services/category.service';
import { Category } from '../category';
import { Note } from '../note';
import { RouterService } from '../services/router.service';

export interface Tile {
  cols: number;
  rows: number;
}
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  isupdate = true;
  viewPage = "NOTE";
  isNoteView = true;
  isMenuVisible = true;
  isSearchView  = false;
  tile : Tile={cols:1,rows:2};
  notes: Note[];
  searchNotes: Note[];
categories: Array<Category>[];
  constructor(private notesService: NotesService, private categoryService: CategoryService,
    private routerService: RouterService) {
    this.notesService.fetchNotesFromServer();
  }

 
  ngOnInit() {
    this.isMenuVisible = true;
    this.tile = {cols:2,rows:2};
  }
  onMenuOpen(){
    console.log(this.isMenuVisible,"Is Menu Opened");
    if(this.isMenuVisible){
      this.isMenuVisible = false;
      this.tile = {cols:0,rows:3};
    }else if(!this.isMenuVisible){
      this.isMenuVisible = true;
      this.tile = {cols:2,rows:2};
    }

  }
  onViewupdate(isupdate) {
    this.viewPage = "NOTE";
    this.isupdate = !this.isupdate

  }
  onChangeListView(name) {
    this.viewPage = name;
  }

  switchToNoteView() {
    this.routerService.routeToNoteView();
    this.isNoteView = true;
  }

  switchToListView() {
    this.routerService.routeToListView();
    this.isNoteView = false;
  }

  onClickLogout(){
    this.routerService.routeToLogin();
  }

  onSelectedFilter(e) {
    console.log(e,"Selected filter");
    this.getFilteredExpenseList();
  }

  getFilteredExpenseList() {
    console.log(this.notesService.searchOption,"Selected");
    console.log(this.notesService.filteredListOptions().length,"Selected filtered");
    if (this.notesService.searchOption.length > 0){
      this.viewPage='SEARCH';
      this.isSearchView = true;
      this.searchNotes = this.notesService.searchOption;
    }
    else {
      this.notes = this.notesService.notesData;
    }

  }
  
}
