import { Prescription } from "./prescription";

export interface Patient{
   
    id:number ;
    name:string ;
    address:string ;
    dob:string ;
    email:string ;
    phone:string ;
    status:string ;
    prescription:Prescription ;
}