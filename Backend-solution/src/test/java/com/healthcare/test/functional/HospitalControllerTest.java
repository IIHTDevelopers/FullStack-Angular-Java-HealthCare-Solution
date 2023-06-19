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
import com.healthcare.controller.HospitalController;
import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.service.DoctorService;
import com.healthcare.service.HospitalService;
import com.healthcare.test.utils.MasterData;

@WebMvcTest(HospitalController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = HealthCareApplication.class)
class HospitalControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private HospitalService hospitalService;

	/************* Get All Hospitals *******************/

	@Test
	void testRestEndpointForFindAllHospitalsIsExposedAndWorking() throws Exception {
		List<HospitalDTO> list = MasterData.getHospitalDTOs();

		Mockito.when(hospitalService.getAllHospitals()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToDefineAppropriateClassesAndObjectsForAGivenScenarioForHospitals() throws Exception {
		List<HospitalDTO> list = MasterData.getHospitalDTOs();

		Mockito.when(hospitalService.getAllHospitals()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);
	}

	@Test
	void testAbleToConfigureAndConnectToDatabaseForHospitals() throws Exception {
		List<HospitalDTO> list = MasterData.getHospitalDTOs();

		Mockito.when(hospitalService.getAllHospitals()).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? "true" : "false"),
				businessTestFile);

	}

	@Test
	void testRestEndpointForFindAllHospitalsIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<HospitalDTO> list = MasterData.getHospitalDTOs();
		Mockito.when(hospitalService.getAllHospitals()).then(new Answer<List<HospitalDTO>>() {

			@Override
			public List<HospitalDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals")
				.content(MasterData.asJsonString(MasterData.getDoctorDTOs())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/*************** Add Hospitals ***********************/

	@Test
	void testRestEndpointForAddNewHospitalIsExposedAndWorking() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Mockito.when(hospitalService.saveHospital(hospitalDTO)).thenReturn(hospitalDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hospitals")
				.content(MasterData.asJsonString(hospitalDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(hospitalDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForAddNewHospitalIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Mockito.when(hospitalService.saveHospital(hospitalDTO)).then(new Answer<HospitalDTO>() {
			@Override
			public HospitalDTO answer(InvocationOnMock invocation) throws Throwable {
				count[0]++;
				return hospitalDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hospitals")
				.content(MasterData.asJsonString(hospitalDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(count[0]);
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************************ Delete Hospital ****************/

	@Test
	void testRestEndpointForDeletingHospitalIsExposedAndWorking() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		Mockito.when(hospitalService.deleteHospitalById(id)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/hospitals/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contains("true") ? true : false,
				businessTestFile);

	}

	@Test
	void testRestEndpointForDeletingHospitalIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		Mockito.when(hospitalService.deleteHospitalById(id)).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return true;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/hospitals/" + id)
				.content(MasterData.asJsonString(true)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);

	}

	/************* Get Single Hospital ******************/

	@Test
	void testRestEndpointForFindingHospitalByIdIsExposedAndWorking() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		Mockito.when(hospitalService.getHospitalById(id)).thenReturn(hospitalDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals/" + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(asJsonString(hospitalDTO)) ? true : false),
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingHospitalByIdIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		Mockito.when(hospitalService.getHospitalById(id)).then(new Answer<HospitalDTO>() {

			@Override
			public HospitalDTO answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return hospitalDTO;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals/" + id)
				.content(MasterData.asJsonString(hospitalDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	/************* Update Hospitals *************************/

	@Test
	public void testRestEndpointForUpdatingHospitalIsExposedAndWorking() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		hospitalDTO.setName("AIIMS");
		Mockito.when(hospitalService.updateHospital(id, hospitalDTO)).thenReturn(hospitalDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/hospitals/" + id)
				.content(MasterData.asJsonString(hospitalDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(hospitalDTO)) ? true : false,
				businessTestFile);
	}

	@Test
	public void testAbleToUseCorrectAnnotationsForCreatingHospitalUpdateRestEndPoints() throws Exception {
		HospitalDTO hospitalDTO = MasterData.getHospitalDTO();
		Long id = hospitalDTO.getId();
		hospitalDTO.setName("AIIMS");
		Mockito.when(hospitalService.updateHospital(id, hospitalDTO)).thenReturn(hospitalDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/hospitals/" + id)
				.content(MasterData.asJsonString(hospitalDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(hospitalDTO)) ? true : false,
				businessTestFile);
	}

	/************ Search Hospital By Name *********************/

	@Test
	void testRestEndpointForFindingHospitalByNameIsExposedAndWorking() throws Exception {
		List<HospitalDTO> list = MasterData.getHospitalDTOs();
		Mockito.when(hospitalService.searchHospitalsByName("Max")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/hospitals/search?name=Max").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

	@Test
	void testRestEndpointForFindingHospitalByNameIsBeingImplementedUsingMultilayerdArchitecture() throws Exception {
		final int count[] = new int[1];
		List<HospitalDTO> list = MasterData.getHospitalDTOs();
		Mockito.when(hospitalService.searchHospitalsByName("Max")).then(new Answer<List<HospitalDTO>>() {

			@Override
			public List<HospitalDTO> answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				count[0]++;
				return list;
			}
		});
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospitals/search?name=Max")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), count[0] == 1 ? true : false, businessTestFile);
	}

	@Test
	void testAbleToAddCustomQueryMethodsForSerachHospitalByNameToRepository() throws Exception {
		List<HospitalDTO> list = MasterData.getHospitalDTOs();
		Mockito.when(hospitalService.searchHospitalsByName("Max")).thenReturn(list);
		MvcResult result = mockMvc.perform(get("/hospitals/search?name=Max").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(asJsonString(list)) ? true : false,
				businessTestFile);
	}

}
