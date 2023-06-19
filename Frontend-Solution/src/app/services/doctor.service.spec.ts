import { TestBed } from "@angular/core/testing";

import { DoctorService } from "./doctor.service";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from "@angular/common/http";
import { Doctor } from "../models/doctor";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";

describe("DoctorService", () => {
  let service: DoctorService;
  let httpMock: HttpTestingController;
  let baseUrl = "http://localhost:8081/healthcare/doctors";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, HttpClientTestingModule],
    });
    service = TestBed.inject(DoctorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe("functional", () => {
    it("should be created", () => {
      expect(service).toBeTruthy();
    });

    it("should retrieve a doctor by id", () => {
      const mockDoctor: Doctor = {
        id: 1,
        name: "Doctor A",
        hospital: { id: 1, name: "Hospital A" },
      };

      service.getDoctorById(1).subscribe((doctor: Doctor) => {
        expect(doctor).toEqual(mockDoctor);
      });

      const req = httpMock.expectOne(baseUrl + "/1");
      expect(req.request.method).toBe("GET");
      req.flush(mockDoctor);
    });

    it("should create a new doctor", () => {
      const newDoctor: Doctor = {
        id: 1,
        name: "Doctor B",
        hospital: { id: 2, name: "Hospital B" },
      };

      service.createDoctor(newDoctor).subscribe((doctor: Doctor) => {
        expect(doctor).toEqual(newDoctor);
      });

      const req = httpMock.expectOne(baseUrl);
      expect(req.request.method).toBe("POST");
      expect(req.request.body).toEqual(newDoctor);
      req.flush(newDoctor);
    });

    it("should update an existing doctor", () => {
      const updatedDoctor: Doctor = {
        id: 1,
        name: "Doctor C",
        hospital: { id: 2, name: "Hospital B" },
      };

      service
        .updateDoctor(updatedDoctor.id, updatedDoctor)
        .subscribe((doctor: Doctor) => {
          expect(doctor).toEqual(updatedDoctor);
        });

      const req = httpMock.expectOne(baseUrl + `/${updatedDoctor.id}`);
      expect(req.request.method).toBe("PUT");
      expect(req.request.body).toEqual(updatedDoctor);
      req.flush(updatedDoctor);
    });

    it("should delete a doctor by id", () => {
      const doctorId = 1;

      service.deleteDoctor(doctorId).subscribe();

      const req = httpMock.expectOne(baseUrl + `/${doctorId}`);
      expect(req.request.method).toBe("DELETE");
      req.flush({});
    });

    it("should retrieve all doctors", () => {
      const mockDoctors: Doctor[] = [
        { id: 1, name: "Doctor A", hospital: { id: 1, name: "Hospital A" } },
        { id: 2, name: "Doctor B", hospital: { id: 2, name: "Hospital B" } },
        { id: 3, name: "Doctor C", hospital: { id: 3, name: "Hospital C" } },
      ];

      service.getAllDoctors().subscribe((doctors: Doctor[]) => {
        expect(doctors).toEqual(mockDoctors);
      });

      const req = httpMock.expectOne(baseUrl);
      expect(req.request.method).toBe("GET");
      req.flush(mockDoctors);
    });

    it("should search for doctors", () => {
      const searchName = "Doctor";

      const mockDoctors: Doctor[] = [
        { id: 1, name: "Doctor A", hospital: { id: 1, name: "Hospital A" } },
        { id: 2, name: "Doctor B", hospital: { id: 2, name: "Hospital B" } },
        { id: 3, name: "Doctor C", hospital: { id: 3, name: "Hospital C" } },
      ];

      service.searchDoctorsByName(searchName).subscribe((doctors: Doctor[]) => {
        expect(doctors).toEqual(mockDoctors);
      });

      const req = httpMock.expectOne(baseUrl + `/search?name=${searchName}`);
      expect(req.request.method).toBe("GET");
      req.flush(mockDoctors);
    });
  });
});
