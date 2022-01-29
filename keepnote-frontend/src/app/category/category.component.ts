import { Component, OnInit, Inject } from '@angular/core';
import { Category } from './../category'
import { Note } from './../note'
import { CategoryService } from '../services/category.service';
import { FormControl } from '@angular/forms';
import {
  MatDialogRef, MAT_DIALOG_DATA, MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition
} from '@angular/material';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {
  errMessage:string;
  categories: Category[] = [];
  category: Category = new Category();
  note: Note = new Note();
  myControl = new FormControl();
  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  constructor(private categoryService: CategoryService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Note, public categorySnackBar: MatSnackBar) {
    this.getCategoryList();
    this.note = data;
  }
  displayFn(user: Category): string {
    return user ? user.categoryName : "";
  }

  onSave() {
    console.log(this.category, "Category on save");
    if (this.category.categoryName != ''){
     
    if (!this.category.categoryId) {
      console.log(this.category, "Save new Category");
      //  this.category.categoryDescription = this.category.categoryName;
      this.categoryService.addCategory(new Category(<any>this.category)).subscribe(data => {
        this.category = data;
        this.dialogRef.close(data);
        this.showCategorySnackBar(this.category.categoryName);
      })
    } else {
      this.dialogRef.close(this.category);
    }

    
  }else{
    this.errMessage = 'Please enter Category Name';
  }
  }

  getCategoryList() {
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data

      },

      err => {
        console.error('error has occured while fetching note list');
        //this.serverError = err.error.message;
      }
    );
  }

  showCategorySnackBar(categoryName: string) {

    this.categorySnackBar.open('Category ' + categoryName + ' added Successfully', 'Close', {
      duration: 10000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

}
