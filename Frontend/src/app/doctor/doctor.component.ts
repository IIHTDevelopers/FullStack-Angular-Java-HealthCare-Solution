import { Component, OnInit } from "@angular/core";
import { Doctor } from "../models/doctor";
import { DoctorService } from "../services/doctor.service";
import { Hospital } from "../models/hospital";
import { HospitalService } from "../services/hospital.service";

@Component({
  selector: "app-doctor",
  templateUrl: "./doctor.component.html",
  styleUrls: ["./doctor.component.css"],
})
export class DoctorComponent implements OnInit {
  doctors: Doctor[] = [];
  selectedDoctor: Doctor = {
    id: 0,
    name: "",
    hospital: {
      id: 0,
      name: "",
    },
  };
  hospitals: Hospital[] = [];
  searchName = "";
  editing = false;

  constructor(
    private doctorService: DoctorService,
    private hospitalService: HospitalService
  ) {}

  ngOnInit(): void {
    this.selectedDoctor.hospital = new Hospital();
    this.getAllDoctors();
    this.getHospitals();
  }

  getAllDoctors(): void {
    this.doctorService.getAllDoctors().subscribe((data: Doctor[]) => {
      this.doctors = data;
    });
  }

  getHospitals(): void {
    this.hospitalService.getAllHospitals().subscribe((data: Hospital[]) => {
      this.hospitals = data;
    });
  }

  saveDoctor(): void {
    if (this.editing) {
      this.updateDoctor();
    } else {
      this.createDoctor();
    }
  }

  createDoctor(): void {
    this.doctorService.createDoctor(this.selectedDoctor).subscribe(() => {
      this.getAllDoctors();
      this.clearForm();
    });
  }

  updateDoctor(): void {
    const hos = this.hospitals.find(
      (hos) => hos.id === this.selectedDoctor.hospital.id * 1
    );
    this.selectedDoctor.hospital = {
      id: hos?.id || 0,
      name: hos?.name || "",
    };
    this.doctorService
      .updateDoctor(this.selectedDoctor.id, this.selectedDoctor)
      .subscribe(() => {
        this.getAllDoctors();
        this.clearForm();
      });
  }

  deleteDoctor(id: number): void {
    this.doctorService.deleteDoctor(id).subscribe(() => {
      this.getAllDoctors();
    });
  }

  editDoctor(doctor: Doctor): void {
    this.selectedDoctor = { ...doctor };
    this.editing = true;
  }

  cancelEdit(): void {
    this.clearForm();
  }

  clearForm(): void {
    this.selectedDoctor = {
      id: 0,
      name: "",
      hospital: {
        id: 0,
        name: "",
      },
    };
    this.editing = false;
  }

  searchDoctorsByName(): void {
    this.doctorService
      .searchDoctorsByName(this.searchName)
      .subscribe((data: Doctor[]) => {
        this.doctors = data;
      });
  }
}
