package com.healthcare.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class HospitalDTO {
	private Long id;
	@NotBlank
	@Size(min = 3, max = 100)
	private String name;

	public HospitalDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public HospitalDTO() {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospitalDTO other = (HospitalDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	

}