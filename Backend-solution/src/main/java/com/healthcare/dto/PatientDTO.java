package com.healthcare.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatientDTO {
	private Long id;
	@NotEmpty
	@Size(min = 3, max = 100)
	private String name;
	@NotNull
	private Long doctorId;

	public PatientDTO() {
		super();
	}

	public PatientDTO(Long id, String name, Long doctorId) {
		super();
		this.id = id;
		this.name = name;
		this.doctorId = doctorId;
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

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(doctorId, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientDTO other = (PatientDTO) obj;
		return Objects.equals(doctorId, other.doctorId) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}