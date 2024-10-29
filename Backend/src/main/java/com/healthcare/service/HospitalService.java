package com.healthcare.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.healthcare.dto.HospitalDTO;
import com.healthcare.entity.Hospital;
import com.healthcare.exception.HospitalNotFoundException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repo.HospitalDAO;

@Service
public class HospitalService {
	private final HospitalDAO hospitalDAO;

	@Autowired
	private ModelMapper modelMapper;

	public HospitalService(HospitalDAO hospitalDAO) {
		this.hospitalDAO = hospitalDAO;
	}

	public List<HospitalDTO> getAllHospitals() {
		List<Hospital> hospitals = hospitalDAO.findAll();
		return hospitals.stream().map(hospital -> modelMapper.map(hospital, HospitalDTO.class))
				.collect(Collectors.toList());
	}

	public HospitalDTO getHospitalById(Long id) {
		Hospital hospital = hospitalDAO.findById(id).orElseThrow(
				() -> new HospitalNotFoundException("Hospital with Id - " + id + " not found!"));
		return modelMapper.map(hospital, HospitalDTO.class);
	}

	public HospitalDTO updateHospital(Long id, HospitalDTO updatedHospital) {
		Hospital existingHospital = hospitalDAO.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hosptal not found with id: " + id));
		modelMapper.map(updatedHospital, existingHospital);
		Hospital updated = hospitalDAO.save(existingHospital);
		return modelMapper.map(updated, HospitalDTO.class);
	}

	public HospitalDTO saveHospital(HospitalDTO hospitalDTO) {
		Hospital hospital = modelMapper.map(hospitalDTO, Hospital.class);
		Hospital savedHosptal = hospitalDAO.save(hospital);
		return modelMapper.map(savedHosptal, HospitalDTO.class);
	}

	public Boolean deleteHospitalById(Long id) {
		if (!hospitalDAO.existsById(id)) {
			throw new HospitalNotFoundException("Hospital with Id - " + id + " not found!");
		}
		hospitalDAO.deleteById(id);
		return true;
	}

	public List<HospitalDTO> searchHospitalsByName(String name) {
		List<Hospital> hospitals = hospitalDAO.findByNameContainingIgnoreCase(name);
		return hospitals.stream().map(hospital -> modelMapper.map(hospital, HospitalDTO.class))
				.collect(Collectors.toList());
	}
}