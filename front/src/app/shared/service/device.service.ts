import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DeviceDTO} from "../model/dto/DeviceDTO";
import {Devices} from "../model/Devices";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private apiUrl = "http://localhost:8080";


  constructor(private http: HttpClient) { }

  saveDevice(device: Devices) {
      return this.http.post<Devices>(this.apiUrl+"/device/saveDevice", device);
  }
}
