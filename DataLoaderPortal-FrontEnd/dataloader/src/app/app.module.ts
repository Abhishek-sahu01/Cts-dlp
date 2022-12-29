import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { ForgotPasswordComponent } from './Authentication/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './Authentication/reset-password/reset-password.component';
import { NavbarComponent } from './NavBar/navbar/navbar.component';
import { UploadComponent } from './DLP/upload/upload.component';
import { PatientListComponent } from './DLP/patient-list/patient-list.component';
import { UpdateComponent } from './DLP/update/update.component';
import { processComponent } from './DLP/process/process.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    NavbarComponent,
    UploadComponent,
    PatientListComponent,
    UpdateComponent,
    processComponent
 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
