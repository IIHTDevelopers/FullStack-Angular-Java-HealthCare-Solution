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
import com.healthcare.controller.DoctorController;
import com.healthcare.dto.DoctorDTO;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.exception.ExceptionResponse;
import com.healthcare.service.DoctorService;
import com.healthcare.service.HospitalService;
import com.healthcare.service.PatientService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(DoctorController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
public class TestDoctorExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorService doctorService;
	@MockBean
	private HospitalService hospitalService;

	@MockBean
	private PatientService patientService;

	@Test
	public void testDataValidationCheckDoctorNameIsAddedInController() throws Exception {
		DoctorDTO dto = MasterData.getDoctorDTO();
		dto.setName("A");

		Mockito.when(doctorService.saveDoctor(dto)).thenReturn(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors").content(MasterData.asJsonString(dto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForDoctor() throws Exception {
		DoctorDTO dto = MasterData.getDoctorDTO();
		dto.setHospitalId(null);
		Mockito.when(doctorService.saveDoctor(dto)).thenReturn(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors").content(MasterData.asJsonString(dto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testExceptionIsThrownAndHandledInCaseOfInvalidDataForDoctor() throws Exception {
		DoctorDTO dto = MasterData.getDoctorDTO();
		dto.setName(null);
		Mockito.when(doctorService.saveDoctor(dto)).thenReturn(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors").content(MasterData.asJsonString(dto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}


	@Test
	void testExceptionIsThrownAndHandledIfDoctorIdIsNotValidWhileDeleting() throws Exception {

		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();

		ExceptionResponse exResponse = new ExceptionResponse("Doctor with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(doctorService.deleteDoctorById(id))
				.thenThrow(new DoctorNotFoundException("Doctor with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/doctors/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfDoctorIdIsNotValidWhileGettingDoctorById() throws Exception {
		Long id = 211L;

		ExceptionResponse exResponse = new ExceptionResponse("Doctor with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(doctorService.getDoctorById(id))
				.thenThrow(new DoctorNotFoundException("Doctor with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);

	}

	@Test
	public void testDataValidationCheckDoctorNameWhileUpdatingInController() throws Exception {
		DoctorDTO dto = MasterData.getDoctorDTO();
		dto.setName("A");

		Mockito.when(doctorService.updateDoctor(dto.getId(), dto)).thenReturn(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/doctors/"+dto.getId()).content(MasterData.asJsonString(dto))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}
	
	@Test
	public void testDeleteDoctorByIdToCheckDoctorNotFoundException() throws Exception {
		Long id= 201l;
		ExceptionResponse exResponse = new ExceptionResponse("Doctor with Id - "+id+" not Found!", System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());

		Mockito.when(this.doctorService.deleteDoctorById(id)).thenThrow(new DoctorNotFoundException("Doctor with Id - "+id+" not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/doctors/"+id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
