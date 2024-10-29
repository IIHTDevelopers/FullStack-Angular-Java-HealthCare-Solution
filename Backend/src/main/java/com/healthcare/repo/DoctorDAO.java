package com.healthcare.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.entity.Doctor;

@Repository
public interface DoctorDAO extends JpaRepository<Doctor, Long> {
	List<Doctor> findByHospitalId(Long hospitalId);
	List<Doctor> findByNameContainingIgnoreCase(String name);
	List<Doctor> findByHospitalIdOrNameContainingIgnoreCase(Long hospitalId, String name);
}