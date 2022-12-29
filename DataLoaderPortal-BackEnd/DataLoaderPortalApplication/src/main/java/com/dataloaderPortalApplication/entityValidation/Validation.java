package com.dataloaderPortalApplication.entityValidation;


import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.model.Prescription;

public class Validation {
	
	private static String msgPatientName="Enter correct patient name ";
	
	private Validation() {
		
	}

	public static String validate(Patient patient, Prescription prescription) {

		if (patient.getName().matches("^[A-Za-z\\s]{5,30}$")) {
			if (!patient.getAddress().isBlank()) {
				if (!patient.getDob().isBlank()) {
					if (patient.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
						if (patient.getPhone().matches("^\\d{10}$")) {
							if (prescription.getPatientDrugId().matches("/[0-9a-zA-Z]{6,}/") || !prescription.getPatientDrugId().isEmpty() ) {
								if (!prescription.getPatientDrugName().isBlank()) {
									patient.setStatus("Inducted");
									return "true";

								} else {
									return "Enter correct drug name for" + patient.getName();
								}
							} else {
								return "Enter correct drug id for" + patient.getName();
							}

						} else {
							return "Enter correct contact number for" + patient.getName();
						}
					} else {
						return "Enter correct email for" + patient.getName();
					}
				}

			} else {
				return "Enter correct address for" + patient.getName();
			}
		} else {
			return msgPatientName;
		}

		return "true";

	}

	public static String validatePatient(Patient patient) {

		return validate(patient,patient.getPrescription());
	}

	public static String validatePatientName(String name) {

		if (name.matches("^[A-Za-z\\s]{5,30}$")) {
			return "true";
		} else {
			return msgPatientName;
		}

	}

}
