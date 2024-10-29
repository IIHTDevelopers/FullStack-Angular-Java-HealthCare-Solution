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
import com.healthcare.dto.DoctorDTO;
import com.healthcare.service.DoctorService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(DoctorController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
class DoctorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DoctorService doctorService;

	/************* Get All Doctors *******************/

	@Test
	void testRestEndpointForFindAllDoctorsIsExposedAndWorking() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();

		Mockito.when(doctorService.getAllDoctors()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForAGivenScenario() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();

		Mockito.when(doctorService.getAllDoctors()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabase() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();

		Mockito.when(doctorService.getAllDoctors()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllDoctorsIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.getAllDoctors()).then(new Answer<List<DoctorDTO>>() {

			@Override
			public List<DoctorDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Doctor ***********************/

	@Test
	void testRestEndpointForAddNewDoctorIsExposedAndWorking() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Mockito.when(doctorService.saveDoctor(doctorDTO)).thenReturn(doctorDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors")
				.content(MasterData.asJsonString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(doctorDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddNewDoctorIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Mockito.when(doctorService.saveDoctor(doctorDTO)).then(new Answer<DoctorDTO>() {
			@Override
			public DoctorDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return doctorDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctors")
				.content(MasterData.asJsonString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Doctor ****************/

	@Test
	void testRestEndpointForDeletingDoctorIsExposedAndWorking() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		Mockito.when(doctorService.deleteDoctorById(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/doctors/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingDoctorIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		Mockito.when(doctorService.deleteDoctorById(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/doctors/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Doctor ******************/

	@Test
	void testRestEndpointForFindingDoctorByIdIsExposedAndWorking() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		Mockito.when(doctorService.getDoctorById(id)).thenReturn(doctorDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(doctorDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingDoctorByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		Mockito.when(doctorService.getDoctorById(id)).then(new Answer<DoctorDTO>() {

			@Override
			public DoctorDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return doctorDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/" + id)
				.content(MasterData.asJsonString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Doctor *************************/

	@Test
	public void testRestEndpointForUpdatingDoctorIsExposedAndWorking() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		doctorDTO.setName("Ravi");
		Mockito.when(doctorService.updateDoctor(id, doctorDTO)).thenReturn(doctorDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/doctors/" + id)
				.content(MasterData.asJsonString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(doctorDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingRestEndPoints() throws Exception {
		DoctorDTO doctorDTO = MasterData.getDoctorDTO();
		Long id = doctorDTO.getId();
		doctorDTO.setName("Ravi");
		Mockito.when(doctorService.updateDoctor(id, doctorDTO)).thenReturn(doctorDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/doctors/" + id)
				.content(MasterData.asJsonString(doctorDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(doctorDTO)) ? true : false,
				businessTestFile);
	}

	/************ Search By Doctor Name *********************/

	@Test
	void testRestEndpointForFindingDoctorByNameIsExposedAndWorking() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByName("Mike")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/doctors/search?name=Mike").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingDoctorByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByName("Mike")).then(new Answer<List<DoctorDTO>>() {

			@Override
			public List<DoctorDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/search?name=Mike")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	@Test
	void testAbleToAddCustomQueryMethodsForSerachDoctorByNameToRepository() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByName("Mike")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/doctors/search?name=Mike").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	/************ Search Doctors By Hospital *********************/

	@Test
	void testRestEndpointForFindingDoctorsByHospitalIdOrNameIsExposedAndWorking() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByHospitalIdOrName(1L, "Mike")).thenReturn(list);
		MvcResult result = mockMvc
				.perform(
						get("/doctors/searchByHospital?hospitalId=1&name=Mike").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingDoctorsByHospitalIdOrNameIsBeingImplementedUsingMultilayerdArchitecture()
			throws Exception {
		final int count[] = new int[1];
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByHospitalIdOrName(1L, "Mike")).then(new Answer<List<DoctorDTO>>() {

			@Override
			public List<DoctorDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors/searchByHospital?hospitalId=1&name=Mike")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	@Test
	void testAbleToAddCustomQueryMethodsForDoctorsByHospitalIdOrNameToRepositoryForSearch() throws Exception {
		List<DoctorDTO> list = MasterData.getDoctorDTOs();
		Mockito.when(doctorService.searchDoctorsByHospitalIdOrName(1L, "Mike")).thenReturn(list);
		MvcResult result = mockMvc
				.perform(
						get("/doctors/searchByHospital?hospitalId=1&name=Mike").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

}
