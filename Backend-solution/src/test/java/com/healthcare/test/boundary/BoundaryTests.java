package com.healthcare.test.boundary;

import java.util.Set;
import static com.healthcare.test.utils.TestUtils.boundaryTestFile;
import static com.healthcare.test.utils.TestUtils.currentTest;
import static com.healthcare.test.utils.TestUtils.yakshaAssert;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.dto.PatientDTO;
import com.healthcare.test.utils.MasterData;

public class BoundaryTests {

	private static Validator validator;

	// ----------------------------------------------------------------------------------------------
	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfDoctorNameIsNotNull() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		doctorDTO.setName("");
		Set<ConstraintViolation<DoctorDTO>> violations = validator.validate(doctorDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}
	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalIdIsNotNullInDoctor() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		doctorDTO.setHospitalId(null);
		Set<ConstraintViolation<DoctorDTO>> violations = validator.validate(doctorDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfDoctorNameIsNotLessThanThreeChars() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		doctorDTO.setName("");
		Set<ConstraintViolation<DoctorDTO>> violations = validator.validate(doctorDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfCourseNameIsNotMoreThanHunderdChars() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		doctorDTO.setName(name);
		Set<ConstraintViolation<DoctorDTO>> violations = validator.validate(doctorDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalNameIsNotNull() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		hospitalDTO.setName("");
		Set<ConstraintViolation<HospitalDTO>> violations = validator.validate(hospitalDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalNameIsNotLessThanThreeChars() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		hospitalDTO.setName("A");
		Set<ConstraintViolation<HospitalDTO>> violations = validator.validate(hospitalDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfHospitalNameIsNotMoreThanHunderedChars() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		hospitalDTO.setName(name);
		Set<ConstraintViolation<HospitalDTO>> violations = validator.validate(hospitalDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfPatientlNameIsNotNull() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setName("");
		Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}
	@Test
	public void testHibernateValidationIsAddedToCheckIfDoctorIdIsNotNullInPatient() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setDoctorId(null);
		Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfPatientNameIsNotLessThanThreeChars() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setName("A");
		Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

	@Test
	public void testHibernateValidationIsAddedToCheckIfPatientNameIsNotMoreThanHunderedChars() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		String name = "";
		for (int i = 0; i < 105; i++) {
			name.concat("A");
		}
		patientDTO.setName(name);
		Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);
		yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
	}

}
