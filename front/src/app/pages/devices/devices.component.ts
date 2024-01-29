//TODO alap automatizálás megcsinálása
//TODO view részben esetleg külömbőző kimutatások implemetálása
//TODO SCSS alakítása meg elég szarul néz ki ahogy most van
//TODO egy alap eszköz konfigurációs oldalt meg kellene csinálni

import {Component, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {TopicService} from "../../shared/service/topic.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {ThemePalette} from "@angular/material/core";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";

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
  devicesList: DeviceDTO[] = [{
    devicesId: 1,
    deviceName: "SzobaTemp",
    deviceType: "1",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["Hőmérséklet", "Páratartalom", "Levegő minőség"],
    fieldType: [2, 2,2],
    payloadKey: ["Hőmérséklet", "Páratartalom", "Levegő minőség"],
    payloadValue: ["21", "67", "40"]
  }, {
    devicesId: 2,
    deviceName: "Erkély",
    deviceType: "1",
    location: "Erkély",
    topic: "home/erkely/temp",
    active: 1,
    fieldKey: ["temp", "humi"],
    fieldType: [2, 2],
    payloadKey: ["temp", "humi"],
    payloadValue: ["10", "15"]
  }, {
    devicesId: 3,
    deviceName: "DaniTermosztát",
    deviceType: "2",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["temp", "fokozat", "onoff"],
    fieldType: [2, 2, 3],
    payloadKey: ["temp", "fokozat", "onoff"],
    payloadValue: ["21", "3", "1"]
  }, {
    devicesId: 3,
    deviceName: "DaniLámpa",
    deviceType: "3",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["szín", "onoff"],
    fieldType: [1, 3],
    payloadKey: ["szín", "onoff"],
    payloadValue: ["21", "1"]
  },{
    devicesId: 3,
    deviceName: "DaniLámpa",
    deviceType: "3",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["szín", "onoff"],
    fieldType: [1, 3],
    payloadKey: ["szín", "onoff"],
    payloadValue: ["21", "1"]
  },{
    devicesId: 3,
    deviceName: "DaniLámpa",
    deviceType: "3",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["szín", "onoff"],
    fieldType: [1, 3],
    payloadKey: ["szín", "onoff"],
    payloadValue: ["21", "1"]
  },{
    devicesId: 1,
    deviceName: "SzobaTemp",
    deviceType: "1",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["Hőmérséklet", "Páratartalom"],
    fieldType: [2, 2],
    payloadKey: ["Hőmérséklet", "Páratartalom"],
    payloadValue: ["21", "67"]
  },{
    devicesId: 1,
    deviceName: "SzobaTemp",
    deviceType: "1",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["Hőmérséklet", "Páratartalom"],
    fieldType: [2, 2],
    payloadKey: ["Hőmérséklet", "Páratartalom"],
    payloadValue: ["21", "67"]
  },{
    devicesId: 1,
    deviceName: "SzobaTemp",
    deviceType: "1",
    location: "DaniSzoba",
    topic: "home/haloszoba/temp",
    active: 1,
    fieldKey: ["Hőmérséklet", "Páratartalom"],
    fieldType: [2, 2],
    payloadKey: ["Hőmérséklet", "Páratartalom"],
    payloadValue: ["21", "67"]
  }];
  number: number[] | undefined;
  chosenDevice: any;

  constructor(private devServ: DeviceService, private topServ: TopicService, private router: Router) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.devServ.getDevices().subscribe((dev: DeviceDTO[]) => {
      // console.log(dev);
      this.devicesList = dev;
      // this.convertFieldsAndMeasurment(dev);
      console.log("Devices is loading");
      // console.log(this.devicesList);
    }, (error: any) => {
      console.error(error);
    });
    this.chosenDevice = this.devicesList[0];


  }

  //TODO szerkesztést meg kell valósítani

  loadDevice(device: any) {
    this.chosenDevice = device;
  }

  goToCreateDevice(){
    this.router.navigateByUrl("/create-device")
  }

  ngOnDestroy(): void {
  }

}
