import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DeviceDTO} from "../model/dto/DeviceDTO";
import {Devices} from "../model/Devices";
import {FormArray} from "@angular/forms";
import {argsArgArrayOrObject} from "rxjs/internal/util/argsArgArrayOrObject";
import {Measurement} from "../model/Measurement";
import {Topic} from "../model/Topic";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private apiUrl = "http://localhost:8080";


  constructor(private http: HttpClient) { }

  saveDevice(devices: DeviceDTO) {
      return this.http.post<Devices>(this.apiUrl+"/device/saveDevice", devices);
  }

  getDevices() {
    return this.http.get<Devices[]>(this.apiUrl+"/device/getAllUserDevices");
  }

  deleteDevice(deviceId: number) {
    //TODO rest apit megadni
    return this.http.post(this.apiUrl+"/device/deleteDevice", deviceId);
  }
}
