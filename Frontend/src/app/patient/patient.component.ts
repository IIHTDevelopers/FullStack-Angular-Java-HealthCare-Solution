import { Component, OnInit } from "@angular/core";
import { Patient } from "../models/patient";
import { PatientService } from "../services/patient.service";
import { Doctor } from "../models/doctor";
import { DoctorService } from "../services/doctor.service";

@Component({
  selector: "app-patient",
  templateUrl: "./patient.component.html",
  styleUrls: ["./patient.component.css"],
})
export class PatientComponent implements OnInit {
  patients: Patient[] = [];
  doctors: Doctor[] = [];
  selectedPatient: Patient = new Patient();
  editing = false;
  searchName: string = "";

  constructor(
    private patientService: PatientService,
    private doctorService: DoctorService
  ) {}

  ngOnInit(): void {
    this.selectedPatient.doctor = new Doctor();
    this.getAllPatients();
    this.getAllDoctors();
  }

  getAllPatients(): void {
    this.patientService.getAllPatients().subscribe((data: Patient[]) => {
      this.patients = data;
    });
  }

  getAllDoctors(): void {
    this.doctorService.getAllDoctors().subscribe((data: Doctor[]) => {
      this.doctors = data;
    });
  }

  createPatient(): void {
    this.patientService.createPatient(this.selectedPatient!).subscribe(() => {
      this.getAllPatients();
      this.clearSelectedPatient();
    });
  }

  updatePatient(): void {
    this.patientService
      .updatePatient(this.selectedPatient!.id, this.selectedPatient!)
      .subscribe(() => {
        this.getAllPatients();
        this.clearSelectedPatient();
      });
  }

  deletePatient(id: number): void {
    this.patientService.deletePatient(id).subscribe(() => {
      this.getAllPatients();
    });
  }

  editPatient(patient: Patient): void {
    this.selectedPatient = { ...patient };
    this.editing = true;
  }

  clearSelectedPatient(): void {
    this.selectedPatient = new Patient();
    this.selectedPatient.doctor = new Doctor();
    this.editing = false;
  }

  searchPatientsByName(): void {
    if (this.searchName.trim()) {
      this.patientService
        .searchPatientsByName(this.searchName)
        .subscribe((data: Patient[]) => {
          this.patients = data;
        });
    } else {
      this.getAllPatients(); // Load all patients if searchName is empty
    }
  }

  cancelEdit(): void {
    this.selectedPatient = new Patient();
    this.selectedPatient.doctor = new Doctor();
    this.editing = false;
  }
}
