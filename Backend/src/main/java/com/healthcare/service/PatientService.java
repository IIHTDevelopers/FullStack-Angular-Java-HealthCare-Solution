package com.healthcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.healthcare.dto.DoctorDTO;
import com.healthcare.dto.HospitalDTO;
import com.healthcare.dto.PatientDTO;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.Hospital;
import com.healthcare.entity.Patient;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repo.PatientDAO;

@Service
public class PatientService {
	private final PatientDAO patientDAO;
	private final DoctorService doctorService;

	@Autowired
	ModelMapper modelMapper;

	public PatientService(PatientDAO patientDAO, DoctorService doctorService) {
		this.patientDAO = patientDAO;
		this.doctorService = doctorService;
	}

	public List<PatientDTO> getAllPatients() {
		List<Patient> patients = patientDAO.findAll();
		return patients.stream().map(patient -> modelMapper.map(patient, PatientDTO.class))
				.collect(Collectors.toList());
	}

	public PatientDTO getPatientById(Long id) {
		Patient patient = patientDAO.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with Id - " + id + " not found!"));
		return modelMapper.map(patient, PatientDTO.class);
	}

	public List<PatientDTO> getPatientsByDoctorId(Long doctorId) {
		List<Patient> patients = patientDAO.findByDoctorId(doctorId);
		return patients.stream().map(patient -> modelMapper.map(patient, PatientDTO.class))
				.collect(Collectors.toList());
	}

	public PatientDTO updatePatient(Long id, PatientDTO updatedPatient) {
		Patient existingPatent = patientDAO.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
		modelMapper.map(updatedPatient, existingPatent);
		Patient updated = patientDAO.save(existingPatent);
		return modelMapper.map(updated, PatientDTO.class);
	}

	public PatientDTO savePatient(PatientDTO patientDTO) {
		DoctorDTO doctorDTO = doctorService.getDoctorById(patientDTO.getId());
		if (doctorDTO != null) {
			Patient patient = modelMapper.map(patientDTO, Patient.class);
			Patient savedPatient = patientDAO.save(patient);
			return modelMapper.map(savedPatient, PatientDTO.class);
		}
		return null;
	}

	public Boolean deletePatientById(Long id) {

		patientDAO.deleteById(id);
		return true;
	}

	public List<PatientDTO> searchPatientsByName(String name) {
		List<Patient> patients = patientDAO.findByNameContainingIgnoreCase(name);
		return patients.stream().map(patient -> modelMapper.map(patient, PatientDTO.class))
				.collect(Collectors.toList());
	}

	public List<PatientDTO> searchPatientsByDoctorId(Long doctorId) {
		List<Patient> patients = patientDAO.findByDoctorId(doctorId);
		return patients.stream().map(patient -> modelMapper.map(patient, PatientDTO.class))
				.collect(Collectors.toList());

	}
}