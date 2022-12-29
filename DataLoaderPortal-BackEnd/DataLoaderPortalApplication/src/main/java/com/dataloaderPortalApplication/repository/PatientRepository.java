package com.dataloaderPortalApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataloaderPortalApplication.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
//	public final static String find_by_name="SELECT a from Patient a where patientId=ALL(SELECT MIN(patientId) from Patient where patientName=?1 AND patientStatus='Inducted')";
//	@Query(find_by_name)
	public Patient findByNameAndStatus(String name,String status);
	
//	@Modifying    
//	@Query("insert into Patient(Name,Address,Dob,Email,Phone,Status,Drug_id) ")
//	@Query("insert into Patient(Name,Address,Dob,Email,Phone,Status,Drug_id) values (:name,:address, :dob,:email,:phone,:status,:drugid)")
//    public void insertPatient(@Param("name")String name, @Param("address")String address, @Param("dob")String dob, 
//    		@Param("email")String email, @Param("phone")String phone, 
//    		@Param("status")String status, @Param("drug_id")String drugid);
//	

}


