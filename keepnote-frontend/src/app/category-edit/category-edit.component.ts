import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { CategoryService } from '../services/category.service';
import { Category } from '../category';
import { MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition} from '@angular/material';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent implements OnInit {

  categories: Array<Category> = [];
  serverError: string;
  category: Category = new Category();
  selectedCategory: Category = new Category();
  horizontalPosition: MatSnackBarHorizontalPosition = 'right'
  verticalPosition: MatSnackBarVerticalPosition = 'top'

  constructor(private categoryService: CategoryService,
    private authService: AuthenticationService,public categoryActionSnackbar: MatSnackBar) { }

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
        this.serverError = err.error.message;
      }
    );
  }
  onSelectPopulateCategory(category) {
    this.selectedCategory = category;
  }

  onCategoryEdit(category:Category){
    console.log(category, "Selected Category for Update")
    this.categoryService.editCategory(category).subscribe(
      data => {
        const index = this.categories.indexOf(data);
        this.showCategoryActionSnackBar(category.categoryName, 'Updated');
        this.getCategories();
        this.selectedCategory = new Category();
      },

      err => {
        console.log('error has occured while fetching note list');
        this.serverError = err.error.message;
      }
    );
  }

  onCategoryDelete(category:Category) {
    console.log(category, "Selected Category for Delete")
    this.categoryService.removeCategory(category).subscribe(
      data => {
        const index = this.categories.indexOf(data);
        this.getCategories();
        this.showCategoryActionSnackBar(category.categoryName, 'Deleted');
        this.selectedCategory = new Category();
      },

      err => {
        console.log('error has occured while fetching note list');
        this.serverError = err.error.message;
      }
    );
  }

  showCategoryActionSnackBar(noteTitle: string, action: string) {
    this.categoryActionSnackbar.open('Category ' + noteTitle + ' ' + action + ' Successfully ', 'Close', {
      duration: 10000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
