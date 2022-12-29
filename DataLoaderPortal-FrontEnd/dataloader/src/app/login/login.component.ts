import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../Authentication/auth.service';
import { NavbarService } from '../NavBar/navbar.service';
import { UserCredential } from './userCredential';
import { UserDetails } from './userDetails';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

token!: string;
errMsg!: string;
user: UserCredential={
"username":"",
"password":""
};

userDetails!:UserDetails;

isShow:boolean=false;
  
  constructor(private authService:AuthService,private router:Router,public nav:NavbarService) { }
  message!: string;

  ngOnInit() {
    this.nav.hide();
  }

  onLogin(userCredential: UserCredential){
    console.log("inside onLogin");
    
    this.user.username=userCredential.username
    console.log("username:", this.user.username);
    localStorage.setItem("name",this.user.username)
    this.authService.login(userCredential).subscribe(data=>{
      this.errMsg='';
      this.userDetails=data as UserDetails;
      localStorage.setItem("token",this.userDetails.authToken);
      this.router.navigate(["/uploadPage"]);
      alert("Login Successful");
    },
    
  // },_error=>{
    
  //   this.errMsg=_error.error
  // })
  // }
    
  error => {
    console.log(error);
    // this.message = "Failed to upload file.Enter the proper file";
    alert("Login Failed : Enter Proper Credentials");
  });
  
}
}
