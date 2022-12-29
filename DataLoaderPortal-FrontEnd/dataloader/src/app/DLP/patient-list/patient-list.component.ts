import { Component, OnInit } from '@angular/core';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { Patient } from '../patient';
import { PortalService } from '../portal.service';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  patients:Patient[]=[];
  constructor(private portalService:PortalService,public nav:NavbarService) { }

  ngOnInit() {
    this.portalService.getAllPatients().subscribe((data)=>{
      console.log(data)
      this.patients=data
      console.log(data)
    })
    this.nav.show();
  }

}
