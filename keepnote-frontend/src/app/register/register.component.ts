import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User, UserToken } from '../user';
import { FormControl } from '@angular/forms';
import { AlertService } from '../services/alert.service';
import { UserService } from '../services/user.service';
import { RouterService } from '../services/router.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
  userId = new FormControl('', [Validators.required]);
  userPassword = new FormControl('', [Validators.required]);
  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  emailId = new FormControl('', [Validators.required]);
  mobileNumber = new FormControl('', [Validators.required]);

    loading = false;
    submitted = false;
    public submitMessage: string;
    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService,
        private routerService: RouterService) { }

    ngOnInit() {

    }

   

    onSubmit() {
    if (this.userId.value === '' || 
    this.userPassword.value === '' ||
    this.firstName.value === '' ||
    this.lastName.value ==='' ||
    this.emailId.value === '' ||
    this.mobileNumber.value === '') {
      this.submitMessage = 'All Fields are Mandatory';
      return;
    }
    const user: User = new User(this.userId.value, this.userPassword.value);
    user.firstName = this.firstName.value ;
    user.lastName = this.lastName.value;
    user.emailId = this.emailId.value;
    user.mobileNumber = this.mobileNumber.value;
    user.userRole = 'ADMIN';
    this.userService.register(user).subscribe(
      res => {
        console.log(res,'Register Console log');
        if(res!=null){
        }
        this.alertService.success('Registration successful', true);
        this.routerService.routeToLogin();
        console.log(res);
      },
      err => {
        if (err.status === 403) {
          this.submitMessage = 'Unauthorized';
        } else if (err.status === 404) {
          this.submitMessage = err.message;
        } 
        else if (err.status === 409) {
          this.submitMessage = err.message;
        }
        else {
          this.submitMessage = err.message;
        }
      }
    );
       
    }

    onClickRegister(){
      this.routerService.routeToLogin();
    }

    getUserNameErrorMessage() {
      return this.userId.hasError('required') ? 'Username is Required' : ' ';
    }
    getPasswordErrorMessage() {
      return this.userPassword.hasError('required') ? 'Password is Required' : ' ';
    }
    getFirstNameErrorMessage() {
      return this.firstName.hasError('required') ? 'First Name is Required' : ' ';
    }
    getLastNameErrorMessage() {
      return this.lastName.hasError('required') ? 'Last Name is Required' : ' ';
    }
    getEmailIdErrorMessage() {
      return this.emailId.hasError('required') ? 'Email ID is Required' : ' ';
    }
    getMobileNumberErrorMessage() {
      return this.mobileNumber.hasError('required') ? 'Mobile Number is Required' : ' ';
    }
}
