import {Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {Devices} from "../../shared/model/Devices";
import {AuthService} from "../../auth.service";


@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  private apiUrl = "http://localhost:8080";

  devicesDTO = signal<DeviceDTO[]>([]);
  chosenDevice= signal<DeviceDTO>({devicesId: null, deviceName: "", sensors: [], measurements: [], location: "", topic: "", active:""});
  errorMessage = signal<number>(0);

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getUserDevices() {
    const userId = 1;
    this.http.post<DeviceDTO[]>(this.apiUrl + "/device/getUserDevices", null, {params: {userId}}).subscribe(device => {
      console.log(device)
      this.devicesDTO.set(device)
      console.log(this.devicesDTO())
    });
  }

  saveDevice(deviceDTO: DeviceDTO, userId: number) {
    console.log(userId)
    if (userId != null) {
      const header = new HttpHeaders();
      header.set("Authorization", "Bearer: " + localStorage.getItem("token"))
      this.http.post<Devices>(this.apiUrl + "/device/saveDevice", deviceDTO, {
        headers: header,
        params: {userId}
      }).subscribe((device) => {
        console.log(device);
        this.http.post(this.apiUrl + "/mqttMessaging/subscribeToTopic", deviceDTO).subscribe(topic => {
          console.log(topic);
        })
      }, error => {
        console.log(error.status);
        this.errorMessage.set(error.status);
      });
    }
  }

  deleteDevice(devicesId: number) {
    this.http.post(this.apiUrl + "/device/deleteDevice", devicesId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);

    });
  }

  sendPayloadToDevice(device: DeviceDTO, payloadKey: string, payload: any) {
    console.log(device)
    this.http.post(this.apiUrl + "/device/sendPayloadToDevice", device, {params: {payloadKey, payload}}).subscribe(() => {
      console.log("sikeresen elkuldve");
    }, error => {
      console.error(error);
    });
  }

  //TODO ez itt valszeg nem fog kelleni mert megváltoztattam a deviceDTO-t
  getDeviceDTOById(deviceId: number) {
    this.http.post<DeviceDTO>(this.apiUrl + "/device/getDeviceDTOById", null, {params: {deviceId}}).subscribe(device => {

    });
  }

  updateDevice(deviceDTO: DeviceDTO, userId: number) {
    const header = new HttpHeaders();
    header.set("Authorization", "Bearer: " + localStorage.getItem("token"))
    this.http.post<Devices>(this.apiUrl + "/device/updateDevice", deviceDTO, {
      headers: header,
      params: {userId}
    }).subscribe(device => {
      console.log(device)
    })
  }
}
