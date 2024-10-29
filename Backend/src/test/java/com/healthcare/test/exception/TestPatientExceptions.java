package com.healthcare.test.exception;

import static com.healthcare.test.utils.TestUtils.currentTest;
import static com.healthcare.test.utils.TestUtils.exceptionTestFile;
import static com.healthcare.test.utils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.healthcare.HealthCareApplication;
import com.healthcare.controller.PatientController;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.dto.PatientDTO;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.exception.ExceptionResponse;
import com.healthcare.exception.HospitalNotFoundException;
import com.healthcare.exception.PatientNotFoundException;
import com.healthcare.service.DoctorService;
import com.healthcare.service.HospitalService;
import com.healthcare.service.PatientService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
public class TestPatientExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorService doctorService;
	@MockBean
	private HospitalService hospitalService;

	@MockBean
	private PatientService patientService;

	@Test
	public void testDataValidationCheckPatientNameIsAddedInController() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setName("A");

		Mockito.when(patientService.savePatient(patientDTO)).thenReturn(patientDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients").content(MasterData.asJsonString(patientDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForSavingPatientDetails() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setName(null);

		Mockito.when(patientService.savePatient(patientDTO)).thenReturn(patientDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients").content(MasterData.asJsonString(patientDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);
	}
	
	@Test
	public void testDataValidationCheckDoctorIdIsAddedInController() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setDoctorId(null);
		Mockito.when(patientService.savePatient(patientDTO)).thenReturn(patientDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients").content(MasterData.asJsonString(patientDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfPatientIdIsNotValidWhileDeleting() throws Exception {

		Long id=201l;

		ExceptionResponse exResponse = new ExceptionResponse("Patient with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(patientService.deletePatientById(id))
				.thenThrow(new PatientNotFoundException("Patient with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/patients/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfPatientIdIsNotValidWhileGettingPatientById() throws Exception {
		Long id = 2001L;

		ExceptionResponse exResponse = new ExceptionResponse("Patient with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(patientService.getPatientById(id))
				.thenThrow(new PatientNotFoundException("Patient with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);

	}

	@Test
	public void testDataValidationCheckPatientNameWhileUpdatingInController() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		patientDTO.setName("A");
		Long id = patientDTO.getId();
		

		Mockito.when(patientService.updatePatient(id, patientDTO)).thenReturn(patientDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/patients/"+id).content(MasterData.asJsonString(patientDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}
	
	@Test
	public void testDeletePatientByIdToCheckPatientNotFoundException() throws Exception {
		Long id= 201l;
		ExceptionResponse exResponse = new ExceptionResponse("Patient with Id - "+id+" not Found!", System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());

		Mockito.when(patientService.deletePatientById(id)).thenThrow(new DoctorNotFoundException("Patient with Id - "+id+" not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/patients/"+id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
