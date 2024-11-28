import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FieldDOT} from "../../shared/model/dto/FieldDTO";
import {Sensor} from "../../shared/model/Sensor";

@Injectable({
  providedIn: 'root'
})
export class SensorService {
  private apiUrl = "http://localhost:8080/sensor";
  constructor(private http: HttpClient) { }

  getByName(sensorName: string){
    return this.http.post<Sensor>(this.apiUrl+"/getByName", null, {params:{sensorName}});
  }

  getByIds(sensorId: number[]) {
    return this.http.post<Sensor[]>(this.apiUrl+"/getAllBySensorId", sensorId);
  }

  getByDeviceId(deviceId: number) {
    return this.http.post<Sensor[]>(this.apiUrl+"/getByDeviceId", null, {params:{deviceId}});
  }
}
