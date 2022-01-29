import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient} from '@angular/common/http';
import { AuthenticationService } from '../services/authentication.service';
import { User } from '../user';
import { Observable } from 'rxjs/Observable';
@Injectable()
export class UserService {
  const_user_url = environment.baseURL + ':8080';
  token: any;
  constructor(private http: HttpClient,
    private authService: AuthenticationService) { }

    register(user: User):Observable<Object> {
      user.id = this.randomInt(1,10000);
     var string =  this.authService.registerUser(user).map((res) => res);
     string.subscribe(data=>{
      console.log(data,'After User Service Call');
      
     });
      return this.http.post(this.const_user_url+'/api/v1/user/', user).map((res)=>res);
  }

  getById(userId: string) {
    return this.http.get(this.const_user_url+'/api/v1/user/' + userId);
}
randomInt(min, max){
  return Math.floor(Math.random() * (max - min + 1)) + min;
}
}
