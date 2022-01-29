import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { User, UserToken } from '../user';
import { Validators } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userId = new FormControl('', [Validators.required]);
  userPassword = new FormControl('', [Validators.required]);

   userToken: UserToken;
  public currentUser: any;
  public submitMessage: string;

  constructor(private _authService: AuthenticationService,
    private routerService: RouterService, private spinningCircle: MatProgressSpinnerModule) { }

  ngOnInit() {
    
  }

  loginSubmit():void {
    if (this.userId.value === '' || this.userPassword.value === '') {
      this.submitMessage = 'empty values';
      return;
    }
    const user: User = new User(this.userId.value, this.userPassword.value);
    this._authService.authenticateUser(user).subscribe(
      res => {
        
        this.userToken = JSON.parse(res) as UserToken;
        console.log("User Token:"+this.userToken.usertoken);
        console.log("Current User:"+this.userToken.currentUser);
        this._authService.setBearerToken(this.userToken.usertoken);
        this._authService.setCurrentUser(this.userToken.currentUser);
        this.routerService.routeToDashboard();

        console.log(res);
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

  onClickRegister(){
    this.routerService.routeToRegistration();
  }
    getUserNameErrorMessage() {
      return this.userId.hasError('required') ? 'Username is Required' : ' ';
    }
    getPasswordErrorMessage() {
      return this.userPassword.hasError('required') ? 'Password is Required' : ' ';
    }
}
