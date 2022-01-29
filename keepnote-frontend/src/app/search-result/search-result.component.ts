
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Note } from '../note';
@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {
  @Input() searchNotes: Note[];
  constructor() { }

  ngOnInit() {
  }

}
