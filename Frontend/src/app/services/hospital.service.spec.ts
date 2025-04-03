import { TestBed } from "@angular/core/testing";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { HospitalService } from "./hospital.service";
import { Hospital } from "../models/hospital";

describe("HospitalService", () => {
  let service: HospitalService;
  let httpMock: HttpTestingController;
  let baseUrl = "http://localhost:8081/healthcare/hospitals";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HospitalService],
    });
    service = TestBed.inject(HospitalService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe("functional", () => {
    it("should be created", () => {
      expect(service).toBeTruthy();
    });

    it("should retrieve all hospitals", () => {
      const mockHospitals: Hospital[] = [
        {
          id: 1,
          name: "Hospital A",
        },
        {
          id: 2,
          name: "Hospital B",
        },
      ];

      service.getAllHospitals().subscribe((hospitals: any) => {
        expect(hospitals).toEqual(mockHospitals);
      });

      const req = httpMock.expectOne(baseUrl);
      expect(req.request.method).toBe("GET");
      req.flush(mockHospitals);
    });

    it("should create a hospital", () => {
      const mockHospital: Hospital = {
        name: "Hospital C",
        id: 0,
      };

      service.createHospital(mockHospital).subscribe((hospital: any) => {
        expect(hospital).toEqual(mockHospital);
      });

      const req = httpMock.expectOne(baseUrl);
      expect(req.request.method).toBe("POST");
      expect(req.request.body).toEqual(mockHospital);
      req.flush(mockHospital);
    });

    it("should update a hospital", () => {
      const mockHospital: Hospital = {
        id: 1,
        name: "Hospital A",
      };

      service
        .updateHospital(mockHospital.id, mockHospital)
        .subscribe((hospital: any) => {
          expect(hospital).toEqual(mockHospital);
        });

      const req = httpMock.expectOne(baseUrl + `/${mockHospital.id}`);
      expect(req.request.method).toBe("PUT");
      expect(req.request.body).toEqual(mockHospital);
      req.flush(mockHospital);
    });

    it("should delete a hospital", () => {
      const mockHospitalId = 1;

      service.deleteHospital(mockHospitalId).subscribe((response: any) => {
        expect(response).toEqual({});
      });

      const req = httpMock.expectOne(baseUrl + `/${mockHospitalId}`);
      expect(req.request.method).toBe("DELETE");
      req.flush({});
    });

    it("should retrieve a hospital by ID", () => {
      const mockHospitalId = 1;
      const mockHospital: Hospital = {
        id: mockHospitalId,
        name: "Hospital A",
      };

      service.getHospitalById(mockHospitalId).subscribe((hospital: any) => {
        expect(hospital).toEqual(mockHospital);
      });

      const req = httpMock.expectOne(baseUrl + `/${mockHospitalId}`);
      expect(req.request.method).toBe("GET");
      req.flush(mockHospital);
    });

    it("should search hospitals by name", () => {
      const searchTerm = "Hospital";

      service.searchHospitalsByName(searchTerm).subscribe((hospitals: any) => {
        expect(hospitals.length).toBe(2);
        expect(hospitals[0].name).toContain(searchTerm);
        expect(hospitals[1].name).toContain(searchTerm);
      });

      const req = httpMock.expectOne(baseUrl + `/search?name=${searchTerm}`);
      expect(req.request.method).toBe("GET");

      const mockHospitals: Hospital[] = [
        {
          id: 1,
          name: "Hospital A",
        },
        {
          id: 2,
          name: "Hospital B",
        },
      ];
      req.flush(mockHospitals);
    });
  });
});
