import { ComponentFixture, TestBed } from "@angular/core/testing";
import { FormsModule } from "@angular/forms";
import { HospitalComponent } from "./hospital.component";
import { HospitalService } from "../services/hospital.service";
import { of } from "rxjs";
import { Hospital } from "./../models/hospital";

describe("HospitalComponent", () => {
  let component: HospitalComponent;
  let fixture: ComponentFixture<HospitalComponent>;
  let mockHospitalService: Partial<HospitalService>;

  beforeEach(async () => {
    mockHospitalService = {
      getAllHospitals: jest.fn().mockReturnValue(of([])),
      createHospital: jest.fn().mockReturnValue(of({})),
      updateHospital: jest.fn().mockReturnValue(of({})),
      deleteHospital: jest.fn().mockReturnValue(of({})),
    };

    await TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [HospitalComponent],
      providers: [{ provide: HospitalService, useValue: mockHospitalService }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe("boundary", () => {
    it("should create the component", () => {
      expect(component).toBeTruthy();
    });

    it("should initialize hospitals", () => {
      const mockHospitals: Hospital[] = [
        { id: 1, name: "Hospital A" },
        { id: 2, name: "Hospital B" },
      ];
      jest
        .spyOn(mockHospitalService, "getAllHospitals")
        .mockReturnValue(of(mockHospitals));

      component.ngOnInit();

      expect(component.hospitals).toEqual(mockHospitals);
    });

    it("should create a new hospital", () => {
      const mockNewHospital: Hospital = {
        name: "Hospital C",
        id: 0,
      };
      jest
        .spyOn(mockHospitalService, "createHospital")
        .mockReturnValue(of(mockNewHospital));

      component.newHospital = mockNewHospital;
      component.createOrUpdateHospital();

      expect(mockHospitalService.createHospital).toHaveBeenCalledWith(
        mockNewHospital
      );
      expect(component.newHospital).toEqual(new Hospital());
    });

    it("should update a hospital", () => {
      const mockSelectedHospital: Hospital = {
        id: 1,
        name: "Hospital A",
      };
      jest
        .spyOn(mockHospitalService, "updateHospital")
        .mockReturnValue(of(mockSelectedHospital));

      component.selectedHospital = mockSelectedHospital;
      component.isEditMode = true;
      component.createOrUpdateHospital();

      expect(mockHospitalService.updateHospital).toHaveBeenCalledWith(
        mockSelectedHospital.id,
        mockSelectedHospital
      );
      expect(component.selectedHospital).toEqual(new Hospital());
    });

    it("should delete a hospital", () => {
      const mockHospitalId = 1;
      jest.spyOn(mockHospitalService, "deleteHospital").mockReturnValue(of({}));

      component.deleteHospital(mockHospitalId);

      expect(mockHospitalService.deleteHospital).toHaveBeenCalledWith(
        mockHospitalId
      );
    });
  });
});
