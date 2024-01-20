import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {Devices} from "../../shared/model/Devices";
import {Measurement} from "../../shared/model/Measurement";
import {TopicService} from "../../shared/service/topic.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {ShowDeviceAndField} from "../../shared/model/dto/ShowDeviceAndField";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit {
  // devicesList: DeviceDTO[] = [];
  devicesList: DeviceDTO[] = [{
    devicesId: 1,
    deviceName: "Proba",
    deviceType: "1",
    location: "Konyha",
    topic: "home/szoba/temp",
    active: 1,
    fieldKey: ["temp", "humi"],
    fieldType: [2, 1],
    payloadValue: ["10", "15"]
  }];
  number: number[] | undefined;

  constructor(private devServ: DeviceService, private topServ: TopicService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.devServ.getDevices().subscribe((dev: DeviceDTO[]) => {
      console.log(dev);
      this.devicesList = dev;
      // this.convertFieldsAndMeasurment(dev);
      console.log("Devices is loading");
    }, (error: any) => {
      console.error(error);
    });
  }

  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);
    });
  }

  sendPayloadToDevice(payloadKey: string,topic: string, payload: any){
    this.devServ.sendPayloadToDevice(payloadKey,topic,payload.toString()).subscribe(()=>{
      console.log("sikeresen elkuldve");
    }, error => {
      console.error(error);
    });
  }

  //TODO szerkesztést meg kell valósítani

}
