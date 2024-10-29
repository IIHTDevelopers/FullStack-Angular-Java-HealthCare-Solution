import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Patient } from "../models/patient";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  private baseUrl = "http://localhost:8081/healthcare/patients";

  constructor(private http: HttpClient) {}

  getAllPatients(): any {
    return this.http.get<Patient[]>(this.baseUrl);
  }

  getPatientById(id: number): any {
    return this.http.get<Patient>(`${this.baseUrl}/${id}`);
  }

  createPatient(patient: Patient): any {
    return this.http.post<Patient>(this.baseUrl, patient);
  }

  updatePatient(id: number, patient: Patient): any {
    return this.http.put<Patient>(`${this.baseUrl}/${id}`, patient);
  }

  deletePatient(id: number): any {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  searchPatientsByName(name: string): any {
    return this.http.get<Patient[]>(`${this.baseUrl}/search?name=${name}`);
  }

  searchPatientsByDoctorName(name: string): any {
    return this.http.get<Patient[]>(
      `${this.baseUrl}/searchByDoctor?name=${name}`
    );
  }
}
