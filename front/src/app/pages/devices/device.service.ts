import {Injectable, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {Devices} from "../../shared/model/Devices";
import {FormArray} from "@angular/forms";
import {argsArgArrayOrObject} from "rxjs/internal/util/argsArgArrayOrObject";
import {Measurement} from "../../shared/model/Measurement";
import {Topic} from "../../shared/model/Topic";
import {DeviceDTO2} from "../../shared/model/dto/DeviceDTO2";

@Injectable({
  providedIn: 'root'
})
export class DeviceService{
  private apiUrl = "http://localhost:8080";
  selectedDevice: any;


  constructor(private http: HttpClient) { }

  getUserDevices(userId: number){
    return this.http.post<DeviceDTO[]>(this.apiUrl+"/device/getUserDevices", null, {params:{userId}});
  }

  saveDevice(deviceDTO: DeviceDTO, userId: number) {
      return this.http.post<Devices>(this.apiUrl+"/device/saveDevice", deviceDTO, {params:{userId}});
  }
  deleteDevice(deviceId: number) {
    return this.http.post(this.apiUrl+"/device/deleteDevice", deviceId);
  }

  sendPayloadToDevice(device: DeviceDTO2, payloadKey: string, payload: any) {
    console.log(device)
    return this.http.post(this.apiUrl+"/device/sendPayloadToDevice",device,{params:{payloadKey,payload}});
  }

  getDeviceDTOById(deviceId: number) {
    return this.http.post<DeviceDTO>(this.apiUrl+"/device/getDeviceDTOById",null,{params:{deviceId}});
  }


  getDeviceById(deviceId: number) {
    return this.http.post<Devices>(this.apiUrl+"/device/getDeviceById",null,{params:{deviceId}});
  }
}
