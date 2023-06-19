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
import com.healthcare.controller.HospitalController;
import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.exception.ExceptionResponse;
import com.healthcare.exception.HospitalNotFoundException;
import com.healthcare.service.DoctorService;
import com.healthcare.service.HospitalService;
import com.healthcare.service.PatientService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(HospitalController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
public class TestHospitalExceptions {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorService doctorService;
	@MockBean
	private HospitalService hospitalService;

	@MockBean
	private PatientService patientService;

	@Test
	public void testDataValidationCheckHospitalNameIsAddedInController() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		hospitalDTO.setName("A");

		Mockito.when(hospitalService.saveHospital(hospitalDTO)).thenReturn(hospitalDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hospitals").content(MasterData.asJsonString(hospitalDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	public void testAbleToWorkWithCustomExceptionsForSaveHospitals() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		hospitalDTO.setName(null);
		Mockito.when(hospitalService.saveHospital(hospitalDTO)).thenReturn(hospitalDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hospitals").content(MasterData.asJsonString(hospitalDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}

	@Test
	void testExceptionIsThrownAndHandledIfHospitalIdIsNotValidWhileDeleting() throws Exception {

		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();

		ExceptionResponse exResponse = new ExceptionResponse("Hospital with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(hospitalService.deleteHospitalById(id))
				.thenThrow(new HospitalNotFoundException("Hospital with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/hospitals/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);
	}

	@Test
	void testExceptionIsThrownAndHandledIfHospitalIdIsNotValidWhileGettingHospitalById() throws Exception {
		Long id = 2001L;

		ExceptionResponse exResponse = new ExceptionResponse("Hospital with Id - " + id + " not found!",
				System.currentTimeMillis(), HttpStatus.NOT_FOUND.value());
		Mockito.when(hospitalService.getHospitalById(id))
				.thenThrow(new HospitalNotFoundException("Hospital with Id - " + id + " not found!"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? true : false,
				exceptionTestFile);

	}

	@Test
	public void testDataValidationCheckHospitalNameWhileUpdatingInController() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		hospitalDTO.setName("A");
		Long id = hospitalDTO.getId();
		

		Mockito.when(hospitalService.updateHospital(id, hospitalDTO)).thenReturn(hospitalDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/hospitals/"+id).content(MasterData.asJsonString(hospitalDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getStatus() == 400 ? true : false, exceptionTestFile);

	}
	
	@Test
	public void testDeleteHospitalByIdToCheckHospitalNotFoundException() throws Exception {
		Long id= 201l;
		ExceptionResponse exResponse = new ExceptionResponse("Hospital with Id - "+id+" not Found!", System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value());

		Mockito.when(hospitalService.deleteHospitalById(id)).thenThrow(new DoctorNotFoundException("Hospital with Id - "+id+" not Found!"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/hospitals/"+id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);

	}

}
