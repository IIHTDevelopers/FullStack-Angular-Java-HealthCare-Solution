package com.healthcare.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.PatientDTO;
import com.healthcare.service.PatientService;

@RestController
@RequestMapping("/patients")
@CrossOrigin
public class PatientController {
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public List<PatientDTO> getAllPatients() {
		return patientService.getAllPatients();
	}

	@GetMapping("/{id}")
	public PatientDTO getPatientById(@PathVariable Long id) {
		return patientService.getPatientById(id);
	}

	@PutMapping("/{id}")
	public PatientDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO updatedPatient) {
		return patientService.updatePatient(id, updatedPatient);
	}

	@GetMapping("/doctor/{doctorId}")
	public List<PatientDTO> getPatientsByDoctorId(@PathVariable Long doctorId) {
		return patientService.getPatientsByDoctorId(doctorId);
	}

	@PostMapping
	public PatientDTO savePatient(@Valid @RequestBody PatientDTO patient) {
		return patientService.savePatient(patient);
	}

	@DeleteMapping("/{id}")
	public Boolean deletePatientById(@PathVariable Long id) {
		patientService.deletePatientById(id);
		return true;
	}

	@GetMapping("/search")
	public List<PatientDTO> searchPatientsByName(@RequestParam String name) {
		return patientService.searchPatientsByName(name);
	}

	@GetMapping("/searchByDoctor")
	public List<PatientDTO> searchPatientsByDoctorId(@RequestParam Long doctorId) {
		return patientService.searchPatientsByDoctorId(doctorId);
	}
}
