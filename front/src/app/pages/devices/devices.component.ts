//TODO alap automatizálás megcsinálása

import {Component, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {TopicService} from "../../shared/service/topic.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {ThemePalette} from "@angular/material/core";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {MeasurementService} from "../../shared/service/measurement.service";
import {Measurement} from "../../shared/model/Measurement";
import {THREE} from "@angular/cdk/keycodes";
import {async} from "rxjs";
import {DeviceDTO2} from "../../shared/model/dto/DeviceDTO2";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit, OnDestroy {

  color: ThemePalette = 'primary';
  checked = false;
  disabled = false;


  // devicesList: DeviceDTO[] = [];
  devicesList2: DeviceDTO2[] = [];
  measurment: Measurement[] = [];
  // devicesList: DeviceDTO[] = [{
  //   devicesId: 1,
  //   deviceName: "SzobaTemp",
  //   deviceType: "1",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["Hőmérséklet", "Páratartalom", "Valami", "Levegő minőség", "Linechart"],
  //   fieldType: [1, 1, 1, 4, 5],
  //   payloadKey: ["Hőmérséklet", "Páratartalom", "Levegő minőség"],
  //   payloadValue: ["21", "67", "40"],
  //   status: true
  // }, {
  //   devicesId: 2,
  //   deviceName: "Erkély",
  //   deviceType: "1",
  //   location: "Erkély",
  //   topic: "home/erkely/temp",
  //   active: 1,
  //   fieldKey: ["temp", "humi"],
  //   fieldType: [2, 2],
  //   payloadKey: ["temp", "humi"],
  //   payloadValue: ["10", "15"],
  //   status: false
  // }, {
  //   devicesId: 3,
  //   deviceName: "DaniTermosztát",
  //   deviceType: "2",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["temp", "fokozat", "onoff"],
  //   fieldType: [2, 2, 3],
  //   payloadKey: ["temp", "fokozat", "onoff"],
  //   payloadValue: ["21", "3", "1"],
  //   status: false
  // }, {
  //   devicesId: 3,
  //   deviceName: "DaniLámpa",
  //   deviceType: "3",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["szín", "onoff"],
  //   fieldType: [1, 3],
  //   payloadKey: ["szín", "onoff"],
  //   payloadValue: ["21", "1"],
  //   status: true
  // }, {
  //   devicesId: 3,
  //   deviceName: "DaniLámpa",
  //   deviceType: "3",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["szín", "onoff"],
  //   fieldType: [1, 3],
  //   payloadKey: ["szín", "onoff"],
  //   payloadValue: ["21", "1"],
  //   status: false
  // }, {
  //   devicesId: 3,
  //   deviceName: "DaniLámpa",
  //   deviceType: "3",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["szín", "onoff"],
  //   fieldType: [1, 3],
  //   payloadKey: ["szín", "onoff"],
  //   payloadValue: ["21", "1"],
  //   status: true
  // }, {
  //   devicesId: 1,
  //   deviceName: "SzobaTemp",
  //   deviceType: "1",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["Hőmérséklet", "Páratartalom"],
  //   fieldType: [2, 2],
  //   payloadKey: ["Hőmérséklet", "Páratartalom"],
  //   payloadValue: ["21", "67"],
  //   status: true
  // }, {
  //   devicesId: 1,
  //   deviceName: "SzobaTemp",
  //   deviceType: "1",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["Hőmérséklet", "Páratartalom"],
  //   fieldType: [2, 2],
  //   payloadKey: ["Hőmérséklet", "Páratartalom"],
  //   payloadValue: ["21", "67"],
  //   status: true
  // }, {
  //   devicesId: 1,
  //   deviceName: "SzobaTemp",
  //   deviceType: "1",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["Hőmérséklet", "Páratartalom"],
  //   fieldType: [2, 2],
  //   payloadKey: ["Hőmérséklet", "Páratartalom"],
  //   payloadValue: ["21", "67"],
  //   status: true
  // }];
  number: number[] | undefined;
  chosenDevice: any;

  constructor(private devServ: DeviceService, private topServ: TopicService, private mesServ: MeasurementService, private router: Router) {

  }

  ngOnInit(): void {
    this.chosenDevice = this.devicesList2[0];
    // @ts-ignore
    this.devServ.getDevices2().subscribe((dev: DeviceDTO2[]) => {
      console.log(dev);
      this.devicesList2 = dev;
      console.log("Devices is loading");
    }, (error: any) => {
      console.error(error);
    });

  }

  // async addMeasurementToDevice(){
  //   let paylaodKey: string = "";
  //   let paylaodValue: string = "";
  //   let meas: Measurement;
  //   for (let i = 0; i < this.devicesList2.length; i++) {
  //     for (let j = 0; j < this.devicesList2[i].fieldKey.length; j++) {
  //
  //       // this.mesServ.getMeasurementByFields(this.devicesList[i].devicesId, this.devicesList[i].fieldKey[j]).subscribe((mes: Measurement) => {
  //       //   this.measurment.measurementId = mes.measurementId
  //       //   this.measurment.payloadKey = mes.payloadKey
  //       //   this.measurment.payloadValue = mes.payloadValue
  //       //   this.measurment.time = mes.time
  //       // }, error => {
  //       //   console.error(error);
  //       // });
  //       const data = this.mesServ.getMeasurementByFields(this.devicesList[i].devicesId, this.devicesList[i].fieldKey[j]);
  //       data.subscribe((mes: Measurement) => {
  //         // console.log(mes)
  //         this.devicesList[i].payloadKey.push(mes.payloadKey);
  //         this.devicesList[i].payloadValue.push(mes.payloadValue);
  //       });
  //       // this.devicesList[i].payloadKey[j] = this.measurment.payloadKey;
  //       // this.devicesList[i].payloadValue[j] = this.measurment.payloadValue;
  //       console.log(this.measurment)
  //
  //     }
  //   }

  //TODO szerkesztést meg kell valósítani

  loadDevice(device: any) {
    this.chosenDevice = device;
  }

  goToCreateDevice() {
    this.router.navigateByUrl("/create-device")
  }

  ngOnDestroy(): void {
  }

  selectDevice(device: DeviceDTO2) {
    this.mesServ.getMapOfListMeasurment(device.devicesId).subscribe({
      next: value => {
        localStorage.setItem("deviceMeasurment",JSON.stringify(value));
      },
      error: err => console.error(err),
      complete: () => {
      }
    });
    this.mesServ.getListOfListMeasurment(device.devicesId).subscribe(value => {
      localStorage.setItem("deviceListMeasrument",JSON.stringify(value));
    },error =>{
      console.error(error);
    })
    localStorage.setItem("selectedDevice", JSON.stringify(device));
    // console.log(device);
    this.devServ.setSelectedDevice(device);
    this.chosenDevice = device;
    // this.reload();
    this.router.navigateByUrl("/view");
  }

  // reload(){
  //   this.deviceObjectEmmiter.emit(this.chosenDevice);
  // }
  changeDeviceStatus(deviceId: number) {
    this.devServ.changeDeviceStatus(deviceId).subscribe(() => {
      console.log("Device status has been changed");
    }, error => {
      console.error(error);
    });
  }

}
