import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {TopicService} from "../../shared/service/topic.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit {
  // devicesList: DeviceDTO[] = [];
  devicesList: DeviceDTO[] = [{
    devicesId: 1,
    deviceName: "Proba1",
    deviceType: "1",
    location: "Konyha",
    topic: "home/konyha/temp",
    active: 1,
    fieldKey: ["temp", "humi"],
    fieldType: [2, 1],
    payloadValue: ["21", "678"]
  },{
    devicesId: 1,
    deviceName: "Proba2",
    deviceType: "1",
    location: "Nappali",
    topic: "home/nappali/temp",
    active: 1,
    fieldKey: ["temp", "humi","valami2", "valami3"],
    fieldType: [2, 1,2,1],
    payloadValue: ["10", "15","1234","7867"]
  }];
  number: number[] | undefined;
  chosenDevice: any;

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
    this.chosenDevice = this.devicesList[0];
  }



  //TODO szerkesztést meg kell valósítani

  loadDevice(device: any) {
    this.chosenDevice = device;
  }
}
