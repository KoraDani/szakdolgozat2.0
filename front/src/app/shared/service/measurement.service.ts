import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Measurement} from "../model/Measurement";
import {MeasurementDTO} from "../model/dto/MeasurementDTO";
import {Fields} from "../model/Fields";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getMeasurementByDeviceId(deviceId: number){
    return this.http.post<MeasurementDTO[]>(this.apiUrl+"/measurement/getDeviceMeasuremenet", null,{params:{deviceId}});
  }

  getMeasurementByFields(devicesId: number | 0, fieldKey: string) {
    return this.http.post<Measurement>(this.apiUrl+"/measurement/getMeasurementByField", null, {params:{devicesId, fieldKey}})
  }

  getMapOfListMeasurment(devicesId: number): Observable<any> {
    return this.http.post<Map<Fields, Measurement[]>>(this.apiUrl+"/measurement/getMapOfListMeasurment", null, {params:{devicesId}})
  }
  getListOfListMeasurment(devicesId: number): Observable<any> {
    return this.http.post<Array<Array<MeasurementDTO>>>(this.apiUrl+"/measurement/getListOfListMeasurment", null, {params:{devicesId}})
  }
}
