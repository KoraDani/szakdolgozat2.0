import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Measurement} from "../../shared/model/Measurement";
import {MeasurementDTO} from "../../shared/model/dto/MeasurementDTO";
import {Observable} from "rxjs";
import {Sensor} from "../../shared/model/Sensor";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getMeasurementByDeviceId(deviceId: number, peagable: number){
    return this.http.post<MeasurementDTO[]>(this.apiUrl+"/measurement/getMeasurementByDevId", null,{params:{deviceId, peagable}});
  }

  getMeasurementByFields(devicesId: number | 0, fieldKey: string) {
    return this.http.post<Measurement>(this.apiUrl+"/measurement/getMeasurementByField", null, {params:{devicesId, fieldKey}})
  }

  getMapOfListMeasurment(devicesId: number): Observable<any> {
    return this.http.post<Map<Sensor, Measurement[]>>(this.apiUrl+"/measurement/getMapOfListMeasurment", null, {params:{devicesId}})
  }
  getListOfListMeasurment(devicesId: number): Observable<any> {
    return this.http.post<Array<Array<MeasurementDTO>>>(this.apiUrl+"/measurement/getListOfListMeasurment", null, {params:{devicesId}})
  }
}
