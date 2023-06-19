import { TestBed, ComponentFixture } from "@angular/core/testing";
import { DoctorComponent } from "./doctor.component";
import { Doctor } from "../models/doctor";
import { Hospital } from "../models/hospital";
import { DoctorService } from "../services/doctor.service";
import { of } from "rxjs";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from "@angular/common/http";
import { HospitalService } from "../services/hospital.service";
import { FormsModule } from "@angular/forms";

describe("DoctorComponent", () => {
  let fixture: ComponentFixture<DoctorComponent>;
  let component: DoctorComponent;
  let doctorService: DoctorService;
  let hospitalService: HospitalService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DoctorComponent],
      providers: [DoctorService, HospitalService],
      imports: [RouterTestingModule, HttpClientModule, FormsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(DoctorComponent);
    component = fixture.componentInstance;
    doctorService = TestBed.inject(DoctorService);
    hospitalService = TestBed.inject(HospitalService);
  });

  describe("functional", () => {
    it("should create the component", () => {
      expect(component).toBeTruthy();
    });

    it("should retrieve the list of doctors", () => {
      const mockDoctors: Doctor[] = [
        { id: 1, name: "Doctor A", hospital: { id: 1, name: "Hospital A" } },
        { id: 2, name: "Doctor B", hospital: { id: 2, name: "Hospital B" } },
      ];

      // Spy on the service method
      const getDoctorsSpy = jest
        .spyOn(doctorService, "getAllDoctors")
        .mockReturnValue(of(mockDoctors));

      // Call the component method
      component.getAllDoctors();

      // Expectations
      expect(getDoctorsSpy).toHaveBeenCalledTimes(1);
      expect(component.doctors).toEqual(mockDoctors);
    });

    it("should retrieve the list of hospitals", () => {
      const mockHospitals: Hospital[] = [
        { id: 1, name: "Hospital A" },
        { id: 2, name: "Hospital B" },
      ];

      // Spy on the service method
      const getHospitalsSpy = jest
        .spyOn(hospitalService, "getAllHospitals")
        .mockReturnValue(of(mockHospitals));

      // Call the component method
      component.getHospitals();

      // Expectations
      expect(getHospitalsSpy).toHaveBeenCalledTimes(1);
      expect(component.hospitals).toEqual(mockHospitals);
    });

    it("should create a doctor if editing is false", () => {
      const newDoctor: Doctor = {
        name: "New Doctor",
        hospital: { id: 1, name: "Hospital A" },
        id: 0,
      };

      // Set editing to false
      component.editing = false;

      // Spy on the service method
      const createDoctorSpy = jest
        .spyOn(doctorService, "createDoctor")
        .mockReturnValue(of(newDoctor));

      // Call the component method
      component.saveDoctor();

      // Expectations
      expect(createDoctorSpy).toHaveBeenCalledTimes(1);
      expect(createDoctorSpy).toHaveBeenCalledWith(component.selectedDoctor);
    });

    it("should update an existing doctor if editing is true", () => {
      // Set up test data
      const doctorId = 1;
      const updatedDoctor: Doctor = {
        id: doctorId,
        name: "Updated Doctor",
        hospital: { id: 1, name: "Hospital A" },
      };

      // Set the component's editing flag to true
      component.editing = true;

      // Spy on the service method
      const updateDoctorSpy = jest
        .spyOn(doctorService, "updateDoctor")
        .mockReturnValue(of(updatedDoctor));

      component.selectedDoctor = updatedDoctor;
      // Call the component method
      component.saveDoctor();

      // Expectations
      // expect(updateDoctorSpy).toHaveBeenCalledTimes(1);
      expect(updateDoctorSpy).toHaveBeenCalledWith(doctorId, updatedDoctor);
      expect(updateDoctorSpy).toHaveBeenCalled();
    });

    it("should delete a doctor", () => {
      const doctorId = 1;

      // Spy on the service method
      const deleteDoctorSpy = jest
        .spyOn(doctorService, "deleteDoctor")
        .mockReturnValue(of(null));

      // Call the component method
      component.deleteDoctor(doctorId);

      // Expectations
      expect(deleteDoctorSpy).toHaveBeenCalledTimes(1);
      expect(deleteDoctorSpy).toHaveBeenCalledWith(doctorId);
    });

    it("should set the selectedDoctor and editing flag when editing a doctor", () => {
      const doctor: Doctor = {
        id: 1,
        name: "Doctor A",
        hospital: { id: 1, name: "Hospital A" },
      };

      component.editDoctor(doctor);

      expect(component.selectedDoctor).toEqual(doctor);
      expect(component.editing).toBeTruthy();
    });

    it("should clear the form and reset the editing flag", () => {
      component.selectedDoctor = {
        id: 1,
        name: "Doctor A",
        hospital: { id: 1, name: "Hospital A" },
      };
      component.editing = true;

      component.cancelEdit();

      expect(component.selectedDoctor).toEqual({
        id: 0,
        name: "",
        hospital: { id: 0, name: "" },
      });
      expect(component.editing).toBeFalsy();
    });

    it("should clear the form and reset the editing flag", () => {
      component.selectedDoctor = {
        id: 1,
        name: "Doctor A",
        hospital: { id: 1, name: "Hospital A" },
      };
      component.editing = true;

      component.clearForm();

      expect(component.selectedDoctor).toEqual({
        id: 0,
        name: "",
        hospital: { id: 0, name: "" },
      });
      expect(component.editing).toBeFalsy();
    });

    it("should search doctors by name", () => {
      const mockDoctors: Doctor[] = [
        { id: 1, name: "Doctor A", hospital: { id: 1, name: "Hospital A" } },
        { id: 2, name: "Doctor B", hospital: { id: 1, name: "Hospital A" } },
        { id: 3, name: "Doctor C", hospital: { id: 2, name: "Hospital B" } },
      ];

      // Spy on the service method
      const searchDoctorsByNameSpy = jest
        .spyOn(doctorService, "searchDoctorsByName")
        .mockReturnValue(of(mockDoctors));

      // Set the search name
      component.searchName = "Doctor";

      // Trigger the search
      component.searchDoctorsByName();

      // Expectations
      expect(searchDoctorsByNameSpy).toHaveBeenCalledTimes(1);
      expect(searchDoctorsByNameSpy).toHaveBeenCalledWith("Doctor");
      expect(component.doctors).toEqual(mockDoctors);
    });
  });
});
