import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserCredential } from '../login/userCredential';
import { ForgotPassword } from './forgot-password/forgotPassword';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url:string;

  constructor(private http: HttpClient) { 
    this.url="http://localhost:8080/api/auth"
  }

  login(userCredential:UserCredential){
    console.log("inside login method in auth service ts");
    return this.http.post("http://localhost:8080/api/auth/login",userCredential);
  }

  forgotPassword(username: string):Observable<any>{
    
    return this.http.post<any>("http://localhost:8080/api/auth/forgot_password",
    username,
    {responseType:'text' as 'json'})

  }

  resetPassword(passwordCredential:ForgotPassword):Observable<any>{
    
    return this.http.post<any>("http://localhost:8080/api/auth/reset_password",
    passwordCredential,
    {responseType:'text' as 'json'})

  }
}
