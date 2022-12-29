import { Component, OnInit } from '@angular/core';
import { FormControl,FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { Patient } from '../patient';
import { PortalService } from '../portal.service';
import { Updatepatientrequest } from './update.model';


@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {
  errMsg!: string;
  patientForm = new FormControl('');
  patient!: Patient;
  public updateForm : FormGroup | undefined;

  
  Name:string="";
  Address:string="";
  Dob:string="";
  email:string="";
  phone:string="";
  status:string="";

  idval:number= 0;
  nameVal:string= "";
  statusVal:string= "";
  prescriptionVal:string= "";

  constructor(private portalService:PortalService,public nav:NavbarService,private router:Router) { }

  ngOnInit() {
    this.nav.show();
  }

  onSearch(patientName:string){
    console.log(patientName)
    this.portalService.getPatientByName(patientName).subscribe((data: Patient)=>{
      this.patient = data;
      this.patientForm.setValue(this.patient)
      console.log(this.patient)
    })
  }
  
  onUpdate(updatedPatient:Patient){
    console.log("updatedPatient:",updatedPatient);
    console.log("patient:",this.patient);
    alert("Data Updated Successfully")
  
    const idval = this.patient?.id ;
    const nameVal = this.patient?.name;
    const statusVal = this.patient?.status;
    const prescriptionVal = this.patient?.prescription;
   
    const updatePatient: Updatepatientrequest = {
      updatepatientdata :{
        id: idval,
        name:this.patient?.name,
        address: (updatedPatient.address !== "") ? updatedPatient.address: this.patient?.address,
        dob: (updatedPatient.dob !== "") ? updatedPatient.dob: this.patient?.dob,
        email:(updatedPatient.email !== "") ? updatedPatient.email: this.patient?.email,
        phone:(updatedPatient.phone !== "" && updatedPatient.phone !== "undefined") ? updatedPatient.phone: this.patient?.phone,
        status:this.patient?.status,
        prescription:this.patient?.prescription

      }
    }
   
    
    console.log("updatePatient1:",updatePatient);
    console.log("this.updateForm:",this.updateForm);
    this.portalService.updatePatient(updatePatient.updatepatientdata).subscribe((data)=>{
      this.router.navigate(["/patientListPage"]);
      
    })
    
  }

}
