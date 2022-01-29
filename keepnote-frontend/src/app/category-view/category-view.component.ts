import { Component, OnInit } from '@angular/core';
import { Category } from '../category';
import { Note } from '../note';
import { CategoryService } from '../services/category.service';
import { NotesService } from '../services/notes.service';
import { AuthenticationService } from '../services/authentication.service';
import { FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { MatDialog, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition } from '@angular/material';
import { CategoryEditComponent } from '../category-edit/category-edit.component';

@Component({
  selector: 'app-category-view',
  templateUrl: './category-view.component.html',
  styleUrls: ['./category-view.component.css']
})
export class CategoryViewComponent implements OnInit {
  categoryName = new FormControl('', [Validators.required]);
  errMessage: string;
  category: Category = new Category();
  isNotesExist : Boolean = true;
  categories: Array<Category> = [];
  notes: Array<Note> = [];
  isCategoryEditOpen: Boolean = true;
  horizontalPosition : MatSnackBarHorizontalPosition = 'right';
  verticalPosition : MatSnackBarVerticalPosition = 'top';
  public submitMessage:string;
  constructor(private categoryService: CategoryService, private noteService: NotesService,
    private authService: AuthenticationService, public dialog: MatDialog,public categorySnackBar: MatSnackBar) { }

  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data
      },

      err => {
        console.log('error has occured while fetching note list');
        this.errMessage = err.error.message;
      }
    );
  }

  onGetNotes(catgory: Category) {
    console.log(this.category.categoryName,"Selected Category");
    this.noteService.getNotesByCategoryId(catgory).subscribe(
      data => {
        this.notes = data
        if(this.notes.length==0){
         this.isNotesExist = false;
        }else{
          this.isNotesExist = true;
        }
      },
      err => {
        console.log('error has occured while fetching note list');
        this.errMessage = err.error.message;
      }
    );
  }

  onAddNewCategory():void {
    if (this.categoryName.value === '') {
      this.errMessage = 'Please enter Category Name';
      return;
    }
    this.errMessage = '';
    const category: Category = new Category(this.categoryName.value);
    category.categoryCreatedBy = this.authService.getCurrentUser();
    this.categoryService.addCategory(category).subscribe(
      res => {
        this.categories.push(res);
        this.showCategorySnackBar(this.categoryName.value);
        this.categoryName.setValue('');
      },
      err => {
        if (err.status === 403) {
          this.submitMessage = 'Unauthorized';
        } else if (err.status === 404) {
          this.submitMessage = err.message;
        } else {
          this.submitMessage = err.message;
        }
      }
    );
  }

   onEditCategoryDialog(): void {
      const dialogRef = this.dialog.open(CategoryEditComponent, {
        width: '350px', height:'500px'
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed 1', result);
        this.getCategories()
      });
    
  }
    getCategoryErrorMessage() {
      return this.categoryName.hasError('required') ? 'Category Name is Required' : ' ';
    }

    showCategorySnackBar(categoryName:string) {
    
      this.categorySnackBar.open('Category '+categoryName +' added Successfully', 'Close', {
        duration: 10000,
        horizontalPosition: this.horizontalPosition,
        verticalPosition: this.verticalPosition,
      });
    }

}
