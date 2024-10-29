import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HospitalComponent } from "./hospital/hospital.component";
import { DoctorComponent } from "./doctor/doctor.component";
import { PatientComponent } from "./patient/patient.component";

const routes: Routes = [
  { path: "", redirectTo: "/", pathMatch: "full" },
  { path: "hospitals", component: HospitalComponent },
  { path: "doctors", component: DoctorComponent },
  { path: "patients", component: PatientComponent },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
