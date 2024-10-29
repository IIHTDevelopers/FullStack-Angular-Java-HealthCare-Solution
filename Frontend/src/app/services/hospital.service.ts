import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Hospital } from "../models/hospital";

@Injectable({
  providedIn: "root",
})
export class HospitalService {
  private baseUrl = "http://localhost:8081/healthcare/hospitals";

  constructor(private http: HttpClient) {}

  getAllHospitals(): any {
    return this.http.get<Hospital[]>(this.baseUrl);
  }

  getHospitalById(id: number): any {
    return this.http.get<Hospital>(`${this.baseUrl}/${id}`);
  }

  createHospital(hospital: Hospital): any {
    return this.http.post<Hospital>(this.baseUrl, hospital);
  }

  updateHospital(id: number, hospital: Hospital): any {
    return this.http.put<Hospital>(`${this.baseUrl}/${id}`, hospital);
  }

  deleteHospital(id: number): any {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  searchHospitalsByName(name: string): any {
    return this.http.get<Hospital[]>(`${this.baseUrl}/search?name=${name}`);
  }
}
