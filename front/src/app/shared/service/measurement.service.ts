import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Measurement} from "../model/Measurement";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getMeasurementByDeviceId(deviceId: number){
    return this.http.post<Measurement[]>(this.apiUrl+"/measurement/getAllMeasurementByDeviceId", null,{params:{deviceId}});
  }
}
