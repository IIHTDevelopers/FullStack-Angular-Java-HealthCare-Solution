import { TestBed, ComponentFixture } from "@angular/core/testing";
import { AppComponent } from "./app.component";
import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { RouterTestingModule } from "@angular/router/testing";

describe("AppComponent", () => {
  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [RouterModule, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
  });

  describe("boundary", () => {
    it("should create the app", () => {
      expect(component).toBeTruthy();
    });

    it(`should have a title 'Hospital Management System'`, () => {
      expect(component.title).toEqual("Hospital Management System");
    });

    it("should have Hospitals link", () => {
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelectorAll("a")[0].textContent).toContain(
        "Hospitals"
      );
    });

    it("should have /hospitals routerLink on Hospital anchor tag", () => {
      const anchorElement: HTMLAnchorElement =
        fixture.nativeElement.querySelectorAll("a")[0];
      expect(anchorElement.getAttribute("routerLink")).toBe("/hospitals");
    });

    it("should have /doctors routerLink on Doctor anchor tag", () => {
      const anchorElement: HTMLAnchorElement =
        fixture.nativeElement.querySelectorAll("a")[1];
      expect(anchorElement.getAttribute("routerLink")).toBe("/doctors");
    });

    it("should have /patients routerLink on Patient anchor tag", () => {
      const anchorElement: HTMLAnchorElement =
        fixture.nativeElement.querySelectorAll("a")[2];
      expect(anchorElement.getAttribute("routerLink")).toBe("/patients");
    });

    it("should have Doctors link", () => {
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelectorAll("a")[1].textContent).toContain(
        "Doctors"
      );
    });

    it("should have Patients link", () => {
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelectorAll("a")[2].textContent).toContain(
        "Patients"
      );
    });
  });
});
