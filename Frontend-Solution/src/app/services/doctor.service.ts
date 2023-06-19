import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Doctor } from "../models/doctor";

@Injectable({
  providedIn: "root",
})
export class DoctorService {
  private baseUrl = "http://localhost:8081/healthcare/doctors";

  constructor(private http: HttpClient) {}

  getAllDoctors(): any {
    return this.http.get<Doctor[]>(this.baseUrl);
  }

  getDoctorById(id: number): any {
    return this.http.get<Doctor>(`${this.baseUrl}/${id}`);
  }

  createDoctor(doctor: Doctor): any {
    return this.http.post<Doctor>(this.baseUrl, doctor);
  }

  updateDoctor(id: number, doctor: Doctor): any {
    return this.http.put<Doctor>(`${this.baseUrl}/${id}`, doctor);
  }

  deleteDoctor(id: number): any {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  searchDoctorsByName(name: string): any {
    return this.http.get<Doctor[]>(`${this.baseUrl}/search?name=${name}`);
  }

  searchDoctorsByHospitalIdAndName(hospitalId: number, name: string): any {
    return this.http.get<Doctor[]>(
      `${this.baseUrl}/searchByHospital?hospitalId=${hospitalId}&name=${name}`
    );
  }
}
