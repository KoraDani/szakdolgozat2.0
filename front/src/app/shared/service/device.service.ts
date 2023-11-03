import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DeviceDTO} from "../model/dto/DeviceDTO";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private apiUrl = "http://localhost:8080";


  constructor(private http: HttpClient) { }

  saveDevice(deviceDTO: DeviceDTO) {
      return this.http.post<DeviceDTO>(this.apiUrl, deviceDTO);
  }
}
