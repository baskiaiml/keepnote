import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/do';
import { AuthenticationService } from '../services/authentication.service';
import { Category } from '../category';
import { environment } from '../environments/environment';
@Injectable()
export class CategoryService {

  public const_category_url=environment.baseURL+':8083'; 
  token: any;
  constructor(private http: HttpClient,
    private authService: AuthenticationService) {
  }


  addCategory(category: Category): Observable<Category> {
    this.token = this.authService.getBearerToken();
     console.log("Category Service:"+(this.token));
     category.categoryCreatedBy=this.authService.getCurrentUser();
    return this.http.post<Category>(this.const_category_url+'/api/v1/category', category);
  }

  getCategories() {
    console.log('Get Notes');
    return  this.http.get<Array<Category>>(this.const_category_url+'/api/v1/category/'+this.authService.getCurrentUser())
  }

  removeCategory(category: Category): Observable<Category>{
    this.token = this.authService.getBearerToken();
    console.log("Category Service:"+(this.token));
    category.categoryCreatedBy=this.authService.getCurrentUser();
   return this.http.delete<Category>(this.const_category_url+'/api/v1/category/'+category.categoryId);
  }

  getCategoryById(categoryId: string): Observable<Category>{
    this.token = this.authService.getBearerToken();
    console.log("Category Service:"+(this.token));
   return this.http.get<Category>(this.const_category_url+'/api/v1/category/id/'+categoryId);
  }

 editCategory(category: Category): Observable<Category>{
    this.token = this.authService.getBearerToken();
    console.log("Category Service:"+(this.token));
    category.categoryCreatedBy=this.authService.getCurrentUser();
   return this.http.put<Category>(this.const_category_url+'/api/v1/category/'+category.categoryId,category);
  }
  
}
