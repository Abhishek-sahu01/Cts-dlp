import { Component, OnInit } from '@angular/core';
import * as e from 'cors';
import { FileUploadModule } from 'ng2-file-upload';
import { NavbarService } from 'src/app/NavBar/navbar.service';
import { PortalService } from '../portal.service';



@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {
  errMsg!: string;
  // let blobArr:Blob = [];
  fileToUpload= new File([], "sample.txt", { type: 'application/text' });
  constructor(private portalService:PortalService,public nav:NavbarService) { }
  message!: string;
  ngOnInit() {
    this.nav.show();
  }
  handleFileSelect(event:any) {
    console.log('event%j',event)
    var files = event.target.files; 
    // FileList object
     this.fileToUpload = files[0];
    // this.fileToUpload = event.relatedTarget.files[0];;

}
onUpload(){
  this.portalService.onUploadFile(this.fileToUpload).subscribe(data => {
    console.log('data onUpload',data);
    //console.log('data onUpload',data?.message);
    // this.message = "File Uploaded Successfully";
    if (data.toString().includes("Invalid data for")){
      alert(data.toString());
    }
    else{
      alert("File uploaded successfully");
    }
    
    },
    error => {
        console.log(error);
        console.log("errormesg:",error?.message);
        console.log("errormesg1:",error?.error?.message);
        alert("Failed to upload the file : "+error?.error?.message);
    });

  }
}
  
  





