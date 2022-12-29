import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { Patient } from '../patient';
import { PortalService } from '../portal.service';
import { Prescription } from '../prescription';
import { Updatepatientrequest } from '../update/update.model';
import { processpatientrequest } from './process.model';
@Component({
  selector: 'app-update',
  templateUrl: './process.component.html',
  styleUrls: ['./process.component.css']
})

export class processComponent implements OnInit {
  errMsg!: string;
  patientForm = new FormControl('');
  patient!: Patient;
  prescription!:Prescription;
  // public updateForm : FormGroup | undefined;

  
  Name:string="";
  Address:string="";
  Dob:string="";
  email:string="";
  phone:string="";
  status:string="";
  patientDrugId:number=0;
  patientDrugName:string="";

  idval:number= 0;
  nameVal:string= "";
  statusVal:string= "";
  prescriptionVal:string= "";

  constructor(private portalService:PortalService,public nav:NavbarService,private router:Router) { }

  message!: string;
  ngOnInit() {
    this.nav.show();
  }
  onSearch(patientName:string){
    console.log(patientName)
    this.portalService.getPatientByName(patientName).subscribe((data: Patient)=>{
      this.patient = data;
      this.patientForm.setValue(this.patient)
      console.log("onsearch:",this.patient);
    },
    error => {
      console.log(error);
      // this.message = "Data is already processed or else data is not present ";
      alert("Data is already processed or else data is not present");
    });

  }

onprocess(updatedPatient:Patient){
  console.log("updatedPatient:",updatedPatient);
  console.log("patient:",this.patient);
  alert("Data Processed Successfully")
  const idval = this.patient?.id ;
  const nameVal = this.patient?.name;
  const statusVal = this.patient?.status;
  const prescriptionVal = this.patient?.prescription;
 
  const updatePatient: Updatepatientrequest = {
    updatepatientdata :{
      id:idval,
      name:this.patient?.name,
      address:this.patient?.address,
      dob:this.patient?.dob,
      email:this.patient?.email,
      phone:this.patient?.phone,
      status:this.patient?.status,
      prescription:this.patient?.prescription,
    }
  }
  console.log("updatePatient1:",updatePatient);
  this.portalService.processPatient(updatePatient.updatepatientdata).subscribe((data)=>{
    this.router.navigate(["/patientListPage"]);
    
  })
}
}
