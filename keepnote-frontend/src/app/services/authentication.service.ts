import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Rx';
import {RouterService} from './router.service';
import { environment } from '../environments/environment';


@Injectable()
export class AuthenticationService {
  const_auth_url=environment.baseURL+':8089'; 
   token: any;
   currentUser: any;
     
  constructor(private httpClient: HttpClient,private routerService:RouterService) { }
  authenticateUser(data):Observable<string> {
    console.info('Bearer:'+data);
    return this.httpClient.post(this.const_auth_url+'/api/v1/auth/login',data,{responseType: 'text'})
      .map((res) => res)
  }

  registerUser(data):Observable<string> {
    console.info('Bearer:'+data);
    return this.httpClient.post(this.const_auth_url+'/api/v1/auth/register',data,{responseType: 'text'})
      .map((res) => res)
  }

  setBearerToken(token) {
    console.info('Bearer:'+token);
    localStorage.setItem('bearerToken', token);
  }

  getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  setCurrentUser(currentUser) {
    console.info('Current User:'+currentUser);
    localStorage.setItem('currentUser', currentUser);
  }

  getCurrentUser() {
    return localStorage.getItem('currentUser');
  }

  logout(){
this.routerService.routeToLogin();
  }
}
