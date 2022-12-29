import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from './patient';
import { Headers } from 'ng2-file-upload';


@Injectable({
  providedIn: 'root'
})
export class PortalService {

  token!:String|null;
  
  constructor(private httpClient: HttpClient) {
    this.token=localStorage.getItem("token")
    
   }

  onUploadFile(fileToUpload:File) {
    console.log("onuploadfile: ")
    const header={
      headers:{
        'Authorization':`Bearer ${this.token}`
      }
    }

    const formData: FormData = new FormData();
    formData.append('file', fileToUpload);
    console.log("name of uploaded file"+fileToUpload);
    return this.httpClient.post("http://localhost:8081/api/patient/load/patientdata", formData,header);
 }

 

 getAllPatients():Observable<any>{
  const header={
    headers:{
      'Authorization':`Bearer ${this.token}`
    }
  }
  return this.httpClient.get<any>("http://localhost:8081/api/patient/patients",header);
 }

 getPatientByName(patientName:string):Observable<any>{
  const header={
    headers:{
      'Authorization':`Bearer ${this.token}`
    }
  }
  return this.httpClient.get<any>("http://localhost:8081/api/patient/retrieve/"+patientName,header);
  }
 updatePatient(patient:Patient | undefined):Observable<any>{
  const header={
    headers:{
      'Authorization':`Bearer ${this.token}`,
    }
  }
  return this.httpClient.post<any>("http://localhost:8081/api/patient/updatepatient",patient,header);
 }
 processPatient(patient:Patient | undefined):Observable<any>{
  const header={
    headers:{
      'Authorization':`Bearer ${this.token}`
    }
  }
  return this.httpClient.put<any>("http://localhost:8081/api/patient/processpatient",patient,header);
 }
}