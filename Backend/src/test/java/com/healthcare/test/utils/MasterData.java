package com.healthcare.test.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.dto.PatientDTO;

public class MasterData {

	public static DoctorDTO getDoctorDTO() {
		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setId(1L);
		doctorDTO.setName("Mike");
		doctorDTO.setHospitalId(1L);
		return doctorDTO;

	}

	public static List<DoctorDTO> getDoctorDTOs() {
		List<DoctorDTO> list = new ArrayList<>();
		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setId(1L);
		doctorDTO.setName("Mike");
		doctorDTO.setHospitalId(1L);
		list.add(doctorDTO);

		doctorDTO = new DoctorDTO();
		doctorDTO.setId(2L);
		doctorDTO.setName("Herry");
		doctorDTO.setHospitalId(2L);
		list.add(doctorDTO);
		return list;
	}

	public static HospitalDTO getHospitalDTO() {
		HospitalDTO dto = new HospitalDTO();
		dto.setId(1L);
		dto.setName("Ganga Ram Hospital");
		return dto;

	}

	public static List<HospitalDTO> getHospitalDTOs() {
		List<HospitalDTO> list = new ArrayList<>();
		HospitalDTO dto = new HospitalDTO();
		dto.setId(1L);
		dto.setName("Ganga Ram Hospital");
		list.add(dto);
		dto = new HospitalDTO();
		dto.setId(2L);
		dto.setName("Max");
		list.add(dto);
		return list;
	}

	public static PatientDTO getPatientDTO() {
		PatientDTO dto = new PatientDTO();
		dto.setId(1L);
		dto.setName("Leena");
		dto.setDoctorId(1L);
		return dto;

	}

	public static List<PatientDTO> getPatientDTOs() {
		List<PatientDTO> list = new ArrayList<>();
		PatientDTO dto = new PatientDTO();
		dto.setId(1L);
		dto.setName("Leena");
		dto.setDoctorId(1L);
		list.add(dto);
		dto = new PatientDTO();
		dto.setId(2L);
		dto.setName("Anne");
		dto.setDoctorId(2L);
		list.add(dto);
		return list;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
