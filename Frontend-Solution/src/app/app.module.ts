import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HospitalComponent } from "./hospital/hospital.component";
import { DoctorComponent } from "./doctor/doctor.component";
import { PatientComponent } from "./patient/patient.component";
import { DoctorService } from "./services/doctor.service";
import { HospitalService } from "./services/hospital.service";
import { PatientService } from "./services/patient.service";

@NgModule({
  declarations: [
    AppComponent,
    HospitalComponent,
    DoctorComponent,
    PatientComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [HospitalService, DoctorService, PatientService],
  bootstrap: [AppComponent],
})
export class AppModule {}
