package com.healthcare.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.dto.DoctorDTO;
import com.healthcare.entity.Doctor;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repo.DoctorDAO;
import com.healthcare.repo.HospitalDAO;

@Service
public class DoctorService {
	private final DoctorDAO doctorDAO;
	private final HospitalDAO hospitalDAO;

	@Autowired
	private ModelMapper modelMapper;

	public DoctorService(DoctorDAO doctorDAO, HospitalDAO hospitalDAO) {
		this.doctorDAO = doctorDAO;
		this.hospitalDAO = hospitalDAO;

	}

	public List<DoctorDTO> getAllDoctors() {
		List<DoctorDTO> dtos = new ArrayList<>();
		List<Doctor> list = doctorDAO.findAll();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public DoctorDTO getDoctorById(Long id) {
		Doctor doctor = doctorDAO.findById(id)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor with Id - " + id + " not found!"));
		DoctorDTO dto = new DoctorDTO();
		modelMapper.map(doctor, dto);
		return dto;
	}

	public DoctorDTO updateDoctor(Long id, DoctorDTO updatedDoctor) {
		DoctorDTO doctorDTO = getDoctorById(id);
		if (doctorDTO != null) {
			Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
			Doctor doctor2 = doctorDAO.save(doctor);
			return modelMapper.map(doctor2, DoctorDTO.class);
		}
		throw new DoctorNotFoundException("Doctor with Id - " + id + " not found!");
	}

	public List<DoctorDTO> getDoctorsByHospitalId(Long hospitalId) {
		List<Doctor> list = doctorDAO.findByHospitalId(hospitalId);
		List<DoctorDTO> dtos = new ArrayList<>();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public DoctorDTO saveDoctor(DoctorDTO doctorDTO) {

		Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
		Doctor doctor2 = doctorDAO.save(doctor);
		return modelMapper.map(doctor2, DoctorDTO.class);

	}

	public Boolean deleteDoctorById(Long id) {
		doctorDAO.deleteById(id);
		return true;
	}

	public List<DoctorDTO> searchDoctorsByName(String name) {
		List<Doctor> list = doctorDAO.findByNameContainingIgnoreCase(name);
		List<DoctorDTO> dtos = new ArrayList<>();
		modelMapper.map(list, dtos);
		return dtos;
	}

	public List<DoctorDTO> searchDoctorsByHospitalIdOrName(Long hospitalId, String name) {
		List<Doctor> list = doctorDAO.findByHospitalIdOrNameContainingIgnoreCase(hospitalId, name);
		List<DoctorDTO> dtos = new ArrayList<>();
		modelMapper.map(list, dtos);
		return dtos;
	}
}