package com.healthcare.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.healthcare.dto.HospitalDTO;
import com.healthcare.entity.Hospital;
import com.healthcare.service.HospitalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospitals")
@CrossOrigin
public class HospitalController {
	private final HospitalService hospitalService;

	public HospitalController(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@GetMapping
	public List<HospitalDTO> getAllHospitals() {
		return hospitalService.getAllHospitals();
	}

	@GetMapping("/{id}")
	public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) {
		HospitalDTO hostpitalDTO = hospitalService.getHospitalById(id);
		return new ResponseEntity<>(hostpitalDTO, HttpStatus.OK);
	}

	@PostMapping
	public HospitalDTO saveHospital(@Valid @RequestBody HospitalDTO hospital) {
		return hospitalService.saveHospital(hospital);
	}

	@PutMapping("/{id}")
	public HospitalDTO updateHospital(@PathVariable Long id, @Valid @RequestBody HospitalDTO updatedHospital) {
		return hospitalService.updateHospital(id, updatedHospital);
	}

	@DeleteMapping("/{id}")
	public Boolean deleteHospitalById(@PathVariable Long id) {
		hospitalService.deleteHospitalById(id);
		return true;
	}

	@GetMapping("/search")
	public List<HospitalDTO> searchHospitalsByName(@RequestParam String name) {
		return hospitalService.searchHospitalsByName(name);
	}
}