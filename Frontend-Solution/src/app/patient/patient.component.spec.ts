import { ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientComponent } from "./patient.component";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { PatientService } from "../services/patient.service";
import { of } from "rxjs";
import { Patient } from "../models/patient";

describe("PatientComponent", () => {
  let component: PatientComponent;
  let fixture: ComponentFixture<PatientComponent>;
  let patientService: PatientService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PatientComponent],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
      ],
      providers: [PatientService],
    }).compileComponents();

    fixture = TestBed.createComponent(PatientComponent);
    component = fixture.componentInstance;
    patientService = TestBed.inject(PatientService);
    fixture.detectChanges();
  });

  describe("boundary", () => {
    it("should create", () => {
      expect(component).toBeTruthy();
    });

    it("should retrieve the list of patients", () => {
      const patients = [
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
      ];
      jest
        .spyOn(patientService, "getAllPatients")
        .mockReturnValue(of(patients));

      component.getAllPatients();

      expect(component.patients).toEqual(patients);
    });

    it("should create a new patient", () => {
      const newPatient = {
        id: 1,
        name: "New Patient",
        doctor: {
          id: 1,
          name: "Doctor A",
          hospital: { id: 1, name: "Hospital A" },
        },
      };
      jest
        .spyOn(patientService, "createPatient")
        .mockReturnValue(of(newPatient));
      jest.spyOn(component, "getAllPatients");

      component.selectedPatient = newPatient;
      component.createPatient();

      expect(patientService.createPatient).toHaveBeenCalledWith(newPatient);
      expect(component.getAllPatients).toHaveBeenCalled();
    });

    it("should update a patient", () => {
      const selectedPatient = {
        id: 1,
        name: "Selected Patient",
        doctor: {
          id: 1,
          name: "Doctor A",
          hospital: { id: 1, name: "Hospital A" },
        },
      };
      jest
        .spyOn(patientService, "updatePatient")
        .mockReturnValue(of(selectedPatient));
      jest.spyOn(component, "getAllPatients");

      component.selectedPatient = selectedPatient;
      component.updatePatient();

      expect(patientService.updatePatient).toHaveBeenCalledWith(
        selectedPatient.id,
        selectedPatient
      );
      expect(component.getAllPatients).toHaveBeenCalled();
    });

    it("should delete a patient", () => {
      const patientId = 1;
      jest.spyOn(patientService, "deletePatient").mockReturnValue(of(null));
      jest.spyOn(component, "getAllPatients");

      component.deletePatient(patientId);

      expect(patientService.deletePatient).toHaveBeenCalledWith(patientId);
      expect(component.getAllPatients).toHaveBeenCalled();
    });

    it("should search for patients by name", () => {
      const searchName = "Patient A";
      const patients = [
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
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      jest
        .spyOn(patientService, "searchPatientsByName")
        .mockReturnValue(of(patients));

      component.searchName = searchName;
      component.searchPatientsByName();

      expect(patientService.searchPatientsByName).toHaveBeenCalledWith(
        searchName
      );
      expect(component.patients).toEqual(patients);
    });

    it("should search for patients by name", () => {
      const searchName = "Patient A";
      const patients = [
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
          name: "Patient A",
          doctor: {
            id: 1,
            name: "Doctor A",
            hospital: { id: 1, name: "Hospital A" },
          },
        },
      ];
      jest
        .spyOn(patientService, "searchPatientsByName")
        .mockReturnValue(of(patients));

      component.searchName = searchName;
      component.searchPatientsByName();

      expect(patientService.searchPatientsByName).toHaveBeenCalledWith(
        searchName
      );
      expect(component.patients).toEqual(patients);
    });
  });
});
