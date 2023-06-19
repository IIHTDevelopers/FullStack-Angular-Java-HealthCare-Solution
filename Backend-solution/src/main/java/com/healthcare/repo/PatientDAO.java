package com.healthcare.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.entity.Patient;

@Repository
public interface PatientDAO extends JpaRepository<Patient, Long> {
	List<Patient> findByDoctorId(Long doctorId);

	List<Patient> findByNameContainingIgnoreCase(String name);

}