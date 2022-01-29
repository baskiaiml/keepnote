export class User {
  id:Number;
    userId: string;
    userPassword: string;
    firstName : string;
	  lastName : string;
	  userRole: string;	
	  userAddedDate: string;
	  emailId: string;
    mobileNumber: string;
  
    constructor(username, password) {
      this.userId = username;
      this.userPassword = password;
    }
  }
  export class UserToken {
    usertoken: string;
    currentUser: string;
    constructor(usertoken, currentUser) {
      this.usertoken = usertoken;
      this.currentUser = currentUser;
    }
  }
