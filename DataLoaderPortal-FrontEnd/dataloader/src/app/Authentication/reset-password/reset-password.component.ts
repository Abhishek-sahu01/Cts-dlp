import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { AuthService } from '../auth.service';
import { ForgotPassword } from '../forgot-password/forgotPassword';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  errMsg!: string;
isMatch:boolean=false;
passwordCredential: ForgotPassword={
  "token":"",
  "password":""
}
response!: string[];

  constructor(private authService:AuthService,private router:Router,public nav:NavbarService) { }

  ngOnInit() {
    this.nav.hide();
  }

  checkPasswordMatch(fieldConfirmPassword:NgForm){
    if(fieldConfirmPassword.value.password==fieldConfirmPassword.value.checkPassword){
      this.isMatch = true;
    }else{
      this.isMatch=false;
    }
  }

  onCancel(){
    this.router.navigate(['/']);
  }

  onSubmit(password:String){
    this.passwordCredential.password=password;
    this.passwordCredential.token=localStorage.getItem("tempToken")
    this.authService.resetPassword(this.passwordCredential).subscribe(data=>{
      console.log(data)
        this.router.navigate(["/"]);
    
    },_error=>{
    
      this.errMsg=_error.error
    })
  }

}
