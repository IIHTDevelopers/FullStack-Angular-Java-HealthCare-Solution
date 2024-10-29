import { Component, OnInit } from "@angular/core";
import { Hospital } from "../models/hospital";
import { HospitalService } from "../services/hospital.service";

@Component({
  selector: "app-hospital",
  templateUrl: "./hospital.component.html",
  styleUrls: ["./hospital.component.css"],
})
export class HospitalComponent implements OnInit {
  hospitals!: Hospital[];
  newHospital: Hospital = new Hospital();
  selectedHospital: Hospital = new Hospital();
  searchName!: string;
  isEditMode: boolean = false;

  constructor(private hospitalService: HospitalService) {}

  ngOnInit(): void {
    this.getAllHospitals();
  }

  createOrUpdateHospital(): void {
    if (this.isEditMode) {
      this.updateHospital();
    } else {
      this.createHospital();
    }
  }

  getAllHospitals(): void {
    this.hospitalService.getAllHospitals().subscribe((data: Hospital[]) => {
      this.hospitals = data;
    });
  }

  createHospital(): void {
    this.hospitalService.createHospital(this.newHospital).subscribe((data: any) => {
      this.getAllHospitals();
      this.resetForm();
    });
  }

  updateHospital(): void {
    this.hospitalService
      .updateHospital(this.selectedHospital.id, this.selectedHospital)
      .subscribe((data: any) => {
        this.getAllHospitals();
        this.resetForm();
      });
  }

  deleteHospital(id: number): void {
    this.hospitalService.deleteHospital(id).subscribe(() => {
      this.getAllHospitals();
    });
  }

  searchHospitalsByName(): void {
    this.hospitalService
      .searchHospitalsByName(this.searchName)
      .subscribe((data: Hospital[]) => {
        this.hospitals = data;
      });
  }

  getHospitalById(id: number): void {
    this.hospitalService.getHospitalById(id).subscribe((data: Hospital) => {
      this.selectedHospital = data;
    });
  }

  editHospital(hospital: Hospital): void {
    this.selectedHospital = Object.assign({}, hospital);
    this.newHospital = Object.assign({}, hospital);
    this.isEditMode = true;
  }

  cancelEdit(): void {
    this.resetForm();
  }

  resetForm(): void {
    this.selectedHospital = new Hospital();
    this.isEditMode = false;
    this.newHospital = new Hospital();
  }

  onKeyPress(event: any) {
    if (this.isEditMode) {
      this.selectedHospital.name = event.target.value;
    }
  }
}
