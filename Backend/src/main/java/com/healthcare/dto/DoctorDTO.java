package com.healthcare.dto;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class DoctorDTO {
	private Long id;
	@NotBlank
	@Size(min = 3, max = 100)
	private String name;
	@NotNull
	private Long hospitalId;

	public DoctorDTO() {
		super();
	}

	public DoctorDTO(Long id, String name, Long hospitalId) {
		super();
		this.id = id;
		this.name = name;
		this.hospitalId = hospitalId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hospitalId, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoctorDTO other = (DoctorDTO) obj;
		return Objects.equals(hospitalId, other.hospitalId) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	
	

}