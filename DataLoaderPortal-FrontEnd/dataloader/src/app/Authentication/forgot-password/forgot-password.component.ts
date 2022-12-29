import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  response!:string[];
  errMsg!: string;

  constructor(private authService:AuthService,private router:Router,public nav:NavbarService) { }

  ngOnInit() {
    this.nav.hide();
  }

  onSubmit(username:string) {
    this.authService.forgotPassword(username).subscribe(data=>{
      this.response=data.split(" ")
      console.log(this.response);
      if(this.response[0]=="true"){
          localStorage.setItem("tempToken",this.response[1])
          console.log(this.response[1])
          this.errMsg=""
          this.router.navigate(["/resetPasswordPage"]);
      }else{
        this.errMsg=this.response[1]+" "+this.response[2]
      }
    },_error=>{
    
      this.errMsg=_error.error
    });
  }

  onCancel(){
    this.router.navigate(["/"]);
  }

}
