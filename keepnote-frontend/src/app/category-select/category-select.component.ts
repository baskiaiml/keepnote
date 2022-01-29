import { Component, OnInit, Inject } from '@angular/core';
import { Category } from '../category';
import { Note } from '../note';
import { CategoryService } from '../services/category.service';
import { NotesService } from '../services/notes.service';
import { AuthenticationService } from '../services/authentication.service';
import { FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import {
  MatDialogRef, MAT_DIALOG_DATA, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';
import { CategoryEditComponent } from '../category-edit/category-edit.component';

@Component({
  selector: 'app-category-select',
  templateUrl: './category-select.component.html',
  styleUrls: ['./category-select.component.css']
})
export class CategorySelectComponent implements OnInit {

  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'
  serverError: string;
  categorySelected: Category = new Category();
  selectedCategoryId: string;
  note: Note = new Note();
  categories: Array<Category> = [];
  constructor(private categoryService: CategoryService, private notesService: NotesService,
    private authService: AuthenticationService, public dialogRef: MatDialogRef<CategorySelectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Note, public categorySnackBar: MatSnackBar) {
    this.note = data;
  }

  ngOnInit() {
    this.getCategories()
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data
      },

      err => {
        console.log('error has occured while fetching note list');
        this.serverError = err.error.message;
      }
    );
  }

  onCategorySelectClose(): void {
    console.log(this.selectedCategoryId, " 1 Selected Category");
    console.log(this.note, " 2 Selected Category");

    this.categoryService.getCategoryById(this.selectedCategoryId).subscribe(data => {
      console.log(data, "Received Category");
      if (data != null) {
        console.log("Category  Added Successfuly");
        this.categorySelected = data;
        if (this.categorySelected != null) {
          this.note.category = this.categorySelected;
          this.notesService.editNote(this.note).subscribe(data => {
            this.note = data;
          },
            err => {
              console.log('error has occured while updating note with newly selected category');
              this.serverError = err.error.message;
            })
        }
      }
    },
      err => {
        console.log('error has occured while performing category  addition');
        this.serverError = err.error.message;
      });

    this.dialogRef.close();
  }

  onSelectedCategory(selectedCategoryId) {
    this.selectedCategoryId = selectedCategoryId;
    console.log(selectedCategoryId, "Selected Category");
  }

}
