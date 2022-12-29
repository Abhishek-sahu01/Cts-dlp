package com.dataloaderPortalApplication.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@Column(name = "patient_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "patient_name")
	@NotEmpty(message = "Name is mandatory")
	@Pattern(regexp = "^[A-Za-z\\s]{5,30}$")
	private String name;

	@Column(name = "patient_address")
	@NotEmpty(message = "Address is mandatory")
	private String address;

	@Column(name = "patient_DOB")
	@NotEmpty(message = "DOB is mandatory")
	private String dob;

	@Column(name = "patient_email")
	@NotEmpty(message = "Email is mandatory")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;

	@Column(name = "patient_contact_no")
	@NotEmpty(message = "Contact Number is mandatory")
	@Pattern(regexp = "^\\d{10}$")
	private String phone;
	
	@Column(name="pstatus")
	private String status;

//	@ManyToOne
//	private Prescription prescription;
//	
	
	
	@OneToOne(cascade = CascadeType.MERGE)     
	@JoinColumn(name = "drug_id", referencedColumnName = "drugid")     
	private Prescription prescription;
	

	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public String getDob() {
		return dob;
	}





	public void setDob(String dob) {
		this.dob = dob;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getPhone() {
		return phone;
	}





	public void setPhone(String phone) {
		this.phone = phone;
	}





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public Prescription getPrescription() {
		return prescription;
	}





	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}





	public Patient(int patientId,
			@NotEmpty(message = "Name is mandatory") @Pattern(regexp = "^[A-Za-z\\s]{5,30}$") String patientName,
			@NotEmpty(message = "Address is mandatory") String patientAddress,
			@NotEmpty(message = "DOB is mandatory") String patientDateofBirth,
			@NotEmpty(message = "Email is mandatory") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") String patientEmail,
			@NotEmpty(message = "Contact Number is mandatory") @Pattern(regexp = "^\\d{10}$") String patientContactNumber,
			String patientStatus, Prescription prescription) {
		super();
		this.id = patientId;
		this.name = patientName;
		this.address = patientAddress;
		this.dob = patientDateofBirth;
		this.email = patientEmail;
		this.phone = patientContactNumber;
		this.status = patientStatus;
		this.prescription = prescription;
	}





	public Patient() {
		// TODO Auto-generated constructor stub
	}

	
	

}
