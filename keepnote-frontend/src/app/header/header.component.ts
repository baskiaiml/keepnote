import { Component } from '@angular/core';
import { RouterService } from '../services/router.service';
import { NotesService } from '../services/notes.service';
import { CategoryService } from '../services/category.service';
import { Category } from '../category';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isNoteView = true;

  categories: Array<Category>[];
  constructor(private routerService: RouterService,private notesService: NotesService, private categoryService: CategoryService) {
    this.notesService.fetchNotesFromServer();
  }

}
