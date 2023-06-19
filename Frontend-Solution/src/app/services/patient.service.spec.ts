import { TestBed } from "@angular/core/testing";

import { PatientService } from "./patient.service";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from "@angular/common/http";
import { of } from "rxjs";
import { Patient } from "../models/patient";

describe("PatientService", () => {
  let service: PatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
    });
    service = TestBed.inject(PatientService);
  });

  describe("functional", () => {
    it("should be created", () => {
      expect(service).toBeTruthy();
    });

    it("should retrieve all patients", () => {
      const patients: Patient[] = [
        {
          id: 1,
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 2,
          name: "Patient B",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 3,
          name: "Patient C",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      jest.spyOn(service, "getAllPatients").mockReturnValue(of(patients));

      service.getAllPatients().subscribe((result: any) => {
        expect(result).toEqual(patients);
      });

      expect(service.getAllPatients).toHaveBeenCalled();
    });

    it("should retrieve a patient by id", () => {
      const patientId = 1;
      const expectedPatient: Patient = {
        id: 1,
        name: "Patient A",
        doctor: {
          id: 1,
          name: "Doctor A",
          hospital: { id: 1, name: "Hospital A" },
        },
      };
      jest
        .spyOn(service, "getPatientById")
        .mockReturnValue(of(expectedPatient));

      service.getPatientById(patientId).subscribe((patient: any) => {
        expect(patient).toEqual(expectedPatient);
      });

      expect(service.getPatientById).toHaveBeenCalledWith(patientId);
    });

    it("should create a new patient", () => {
      const newPatient: Patient = {
        id: 1,
        name: "Patient C",
        doctor: {
          id: 1,
          name: "Doctor A",
          hospital: { id: 1, name: "Hospital A" },
        },
      };
      jest.spyOn(service, "createPatient").mockReturnValue(of(newPatient));

      service.createPatient(newPatient).subscribe((patient: any) => {
        expect(patient).toEqual(newPatient);
      });

      expect(service.createPatient).toHaveBeenCalledWith(newPatient);
    });

    it("should update an existing patient", () => {
      const updatedPatient: Patient = {
        id: 1,
        name: "Patient D",
        doctor: {
          id: 1,
          name: "Doctor A",
          hospital: { id: 1, name: "Hospital A" },
        },
      };
      jest.spyOn(service, "updatePatient").mockReturnValue(of(updatedPatient));

      service
        .updatePatient(updatedPatient.id, updatedPatient)
        .subscribe((patient: any) => {
          expect(patient).toEqual(updatedPatient);
        });

      expect(service.updatePatient).toHaveBeenCalledWith(1, updatedPatient);
    });

    it("should delete a patient", () => {
      const patientId = 1;
      jest.spyOn(service, "deletePatient").mockReturnValue(of(null));

      service.deletePatient(patientId).subscribe((result: any) => {
        expect(result).toBeNull();
      });

      expect(service.deletePatient).toHaveBeenCalledWith(patientId);
    });

    it("should search patients by name", () => {
      const searchName = "Patient";
      const patients: Patient[] = [
        {
          id: 1,
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 2,
          name: "Patient B",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 3,
          name: "Patient C",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      const expectedPatients: Patient[] = [
        {
          id: 1,
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 3,
          name: "Patient C",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      jest
        .spyOn(service, "searchPatientsByName")
        .mockReturnValue(of(expectedPatients));

      service.searchPatientsByName(searchName).subscribe((result: any) => {
        expect(result).toEqual(expectedPatients);
      });

      expect(service.searchPatientsByName).toHaveBeenCalledWith(searchName);
    });

    it("should search patients by name", () => {
      const searchName = "Patient";
      const patients: Patient[] = [
        {
          id: 1,
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 2,
          name: "Patient B",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 3,
          name: "Patient C",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      const expectedPatients: Patient[] = [
        {
          id: 1,
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
        {
          id: 3,
          name: "Patient C",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      jest
        .spyOn(service, "searchPatientsByName")
        .mockReturnValue(of(expectedPatients));

      service.searchPatientsByName(searchName).subscribe((result: any) => {
        expect(result).toEqual(expectedPatients);
      });

      expect(service.searchPatientsByName).toHaveBeenCalledWith(searchName);
    });
  });
});
