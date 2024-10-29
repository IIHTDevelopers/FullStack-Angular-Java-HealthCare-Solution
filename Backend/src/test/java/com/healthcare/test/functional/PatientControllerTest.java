package com.healthcare.test.functional;

import static com.healthcare.test.utils.TestUtils.asJsonString;
import static com.healthcare.test.utils.TestUtils.businessTestFile;
import static com.healthcare.test.utils.TestUtils.currentTest;
import static com.healthcare.test.utils.TestUtils.yakshaAssert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.healthcare.HealthCareApplication;
import com.healthcare.controller.DoctorController;
import com.healthcare.controller.PatientController;
import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.PatientDTO;
import com.healthcare.service.DoctorService;
import com.healthcare.service.PatientService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PatientService patientService;

	/************* Get All Patients *******************/

	@Test
	void testRestEndpointForFindAllPatientsIsExposedAndWorking() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();

		Mockito.when(patientService.getAllPatients()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForPatientInAGivenScenario() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();

		Mockito.when(patientService.getAllPatients()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabaseForPatients() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();

		Mockito.when(patientService.getAllPatients()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllPatientsIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.getAllPatients()).then(new Answer<List<PatientDTO>>() {

			@Override
			public List<PatientDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Patient ***********************/

	@Test
	void testRestEndpointForAddNewPatientIsExposedAndWorking() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Mockito.when(patientService.savePatient(patientDTO)).thenReturn(patientDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients")
				.content(MasterData.asJsonString(patientDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(patientDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddNewDoctorIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Mockito.when(patientService.savePatient(patientDTO)).then(new Answer<PatientDTO>() {
			@Override
			public PatientDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return patientDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patients")
				.content(MasterData.asJsonString(patientDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Patient ****************/

	@Test
	void testRestEndpointForDeletingPatientIsExposedAndWorking() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		Mockito.when(patientService.deletePatientById(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/patients/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingPatientIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		Mockito.when(patientService.deletePatientById(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/patients/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Patient ******************/

	@Test
	void testRestEndpointForFindingDoctorByIdIsExposedAndWorking() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		Mockito.when(patientService.getPatientById(id)).thenReturn(patientDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(patientDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingPatientByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		Mockito.when(patientService.getPatientById(id)).then(new Answer<PatientDTO>() {

			@Override
			public PatientDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return patientDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients/" + id)
				.content(MasterData.asJsonString(patientDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Patient *************************/

	@Test
	public void testRestEndpointForUpdatingDoctorIsExposedAndWorking() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		patientDTO.setName("Neeraj");
		Mockito.when(patientService.updatePatient(id, patientDTO)).thenReturn(patientDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/patients/" + id)
				.content(MasterData.asJsonString(patientDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(patientDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingPatientRestEndPoints() throws Exception {
		PatientDTO patientDTO = MasterData.getPatientDTO();
		Long id = patientDTO.getId();
		patientDTO.setName("Ram");
		Mockito.when(patientService.updatePatient(id, patientDTO)).thenReturn(patientDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/patients/" + id)
				.content(MasterData.asJsonString(patientDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(patientDTO)) ? true : false,
				businessTestFile);
	}

	/************ Search By Patient Name *********************/

	@Test
	void testRestEndpointForFindingPatientByNameIsExposedAndWorking() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByName("Leena")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/patients/search?name=Leena").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingPatientByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByName("Leena")).then(new Answer<List<PatientDTO>>() {

			@Override
			public List<PatientDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients/search?name=Leena")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	@Test
	void testAbleToAddCustomQueryMethodsForSerachPatientByNameToRepository() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByName("Leena")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/patients/search?name=Leena").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	/************ Search Patient By Doctor Id *********************/

	@Test
	void testRestEndpointForFindingPatientsByDoctorIdIsExposedAndWorking() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByDoctorId(list.get(0).getDoctorId())).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/patients/searchByDoctor?doctorId=" + list.get(0).getDoctorId())
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingPatientsByDoctorIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByDoctorId(list.get(0).getDoctorId()))
				.then(new Answer<List<PatientDTO>>() {

					@Override
					public List<PatientDTO> answer(InvocationOnMock invocation) throws Throwable {
						// TODO Auto-generated method stub

						count[0]++;
						return list;
					}
				});
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/patients/searchByDoctor?doctorId=" + list.get(0).getDoctorId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	@Test
	void testAbleToAddCustomQueryMethodsForPatientsByDoctorIdToRepositoryForSearch() throws Exception {
		List<PatientDTO> list = MasterData.getPatientDTOs();
		Mockito.when(patientService.searchPatientsByDoctorId(list.get(0).getDoctorId())).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/patients/searchByDoctor?doctorId=" + list.get(0).getDoctorId())
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

}
