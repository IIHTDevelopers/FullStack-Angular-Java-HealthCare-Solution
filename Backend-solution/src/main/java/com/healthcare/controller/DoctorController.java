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

import com.healthcare.dto.DoctorDTO;
import com.healthcare.service.DoctorService;

@RestController
@RequestMapping("/doctors")
//@CrossOrigin
public class DoctorController {
	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@GetMapping
	public List<DoctorDTO> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	@GetMapping("/{id}")
	public DoctorDTO getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id);
	}

	@GetMapping("/hospital/{hospitalId}")
	public List<DoctorDTO> getDoctorsByHospitalId(@PathVariable Long hospitalId) {
		return doctorService.getDoctorsByHospitalId(hospitalId);
	}

	@PostMapping
	public DoctorDTO saveDoctor(@Valid @RequestBody DoctorDTO doctor) {
		return doctorService.saveDoctor(doctor);
	}

	@DeleteMapping("/{id}")
	public Boolean deleteDoctorById(@PathVariable Long id) {
		Boolean status = doctorService.deleteDoctorById(id);
		return status;
	}

	@GetMapping("/search")
	public List<DoctorDTO> searchDoctorsByName(@RequestParam String name) {
		return doctorService.searchDoctorsByName(name);
	}

	@PutMapping("/{id}")
	public DoctorDTO updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO updatedDoctor) {
		return doctorService.updateDoctor(id, updatedDoctor);
	}

	@GetMapping("/searchByHospital")
	public List<DoctorDTO> searchDoctorsByHospitalIdAndName(@RequestParam Long hospitalId, @RequestParam String name) {
		return doctorService.searchDoctorsByHospitalIdOrName(hospitalId, name);
	}
}