import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForgotPasswordComponent } from './Authentication/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './Authentication/reset-password/reset-password.component';
import { PatientListComponent } from './DLP/patient-list/patient-list.component';
import { processComponent } from './DLP/process/process.component';
import { UpdateComponent } from './DLP/update/update.component';
import { UploadComponent } from './DLP/upload/upload.component';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"forgotPasswordPage",component:ForgotPasswordComponent},
  {path:"resetPasswordPage",component:ResetPasswordComponent},
  {path:"uploadPage",component:UploadComponent},
  {path:"patientListPage",component:PatientListComponent},
  {path:"updatePatientPage",component:UpdateComponent},
  {path:"processListPage",component:processComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
