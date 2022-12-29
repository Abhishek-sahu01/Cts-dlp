package com.dataloaderPortalApplication.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dataloaderPortalApplication.Service.PrescriptionService;
import com.dataloaderPortalApplication.entityValidation.Validation;
import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.model.Prescription;

@Component
@Configurable
public class ExcelHelper {

	private static final Logger log = LoggerFactory.getLogger(ExcelHelper.class);

	@Autowired
	private PrescriptionService prescriptionService;

	private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Patient Name", "Address", "DOB", "Email ID", "Phone Number", "Drug Id", "Drug Name" };
	static String SHEET = "PatientDetails";

	public static boolean hasExcelFormat(MultipartFile file) {
		log.info("START");
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		log.info("END");
		return true;
	}

	public List<?> excelToPatient(InputStream is) throws IOException {
		log.info("START");
		try {
			Workbook workbook = WorkbookFactory.create(is);
			log.debug("Workbook {}", workbook);
			Sheet sheet = workbook.getSheetAt(0);
			log.debug("Sheet {}", sheet);
			Iterator<Row> rows = sheet.iterator();
			log.debug("Rows {}", rows);
			List<Patient> patients = new ArrayList<>();
			List<String> messages = new ArrayList<>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Patient patient = new Patient();
				Prescription prescription = new Prescription();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {

					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					
					case 0:
						log.info("Patient Name");
						patient.setName(currentCell.getStringCellValue());
						break;
					case 1:
						log.info("Patient Address");
						patient.setAddress(currentCell.getStringCellValue());
						break;
					case 2:
						log.info("Patient DOB ");
						patient.setDob(currentCell.getStringCellValue());
						break;
					case 3:
						log.info("Patient Email");
						patient.setEmail(currentCell.getStringCellValue());
						break;
					case 4:
						log.debug("Patient Contact number ");
						patient.setPhone(String.valueOf((long) currentCell.getNumericCellValue()));
						break;
					case 5:
						log.info("Patient Drug id");
						prescription.setPatientDrugId(String.valueOf((long)currentCell.getNumericCellValue()));
						break;
					case 6:
						log.info("Patient Drug name");
						prescription.setPatientDrugName(currentCell.getStringCellValue());
						break;
					default:
						log.info("Default");
						break;
					}
					cellIdx++;
				}
				patient.setStatus("INDUCTED");
				String message = Validation.validate(patient, prescription);
				if (message.equalsIgnoreCase("true")) {
					log.info("Saving prescription");
					prescriptionService.save(prescription);
					log.info("Setting prescription");
					patient.setPrescription(prescription);
					log.info("Adding patient");
					patients.add(patient);
				} else {
					messages.add(message);
				}
				log.debug("Patient {}", patient);
			}
			workbook.close();
			log.info("Patients {}", patients);
			log.info("END");

			List<List<?>> result = new ArrayList<>();
			result.add(patients);
			result.add(messages);
			return result;
		} catch (IOException e) {
			throw new IOException("fail to parse Excel file: " + e.getMessage());
		}
	}

}
