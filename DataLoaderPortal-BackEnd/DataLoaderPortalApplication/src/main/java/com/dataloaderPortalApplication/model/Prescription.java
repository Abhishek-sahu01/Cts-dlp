package com.dataloaderPortalApplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "prescription")
public class Prescription {

	@Id
	@Column(name = "Drugid")
	@NotEmpty(message = "Drug Id is mandatory")
	@Pattern(regexp = "^\\(?\\d{5}\\)?[-]?\\d{4}[-]?\\d{2}$")
	private String drugid;

	@Column(name = "Drugname")
	@NotEmpty(message = "Drug Name is mandatory")
	private String drugname;

	public String getPatientDrugId() {
		return drugid;
	}

	public void setPatientDrugId(String patientDrugId) {
		this.drugid = patientDrugId;
	}

	public String getPatientDrugName() {
		return drugname;
	}

	public void setPatientDrugName(String patientDrugName) {
		this.drugname = patientDrugName;
	}

	public Prescription(String patientDrugId, String patientDrugName) {
		super();
		this.drugid = patientDrugId;
		this.drugname = patientDrugName;
	}

	public Prescription() {
		super();
	}

	@Override
	public String toString() {
		return "Prescription [patientDrugId=" + drugid + ", patientDrugName=" + drugname + "]";
	}

}
