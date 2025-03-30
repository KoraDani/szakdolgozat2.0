//TODO alap automatizálás megcsinálása

import {Component, OnInit} from '@angular/core';
import {DeviceService} from "./device.service";
import {Router} from "@angular/router";
import {MeasurementService} from "./measurement.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {WebSocketService} from "./WebSocketService";
import {WebSocketModel} from "./WebSocketModel";

@Component({
    selector: 'app-devices',
    templateUrl: './devices.component.html',
    styleUrls: ['./devices.component.scss'],
    standalone: false
})
export class DevicesComponent implements OnInit {

  number: number[] | undefined;
  chosenDevice: any;
  deviceList?: DeviceDTO[];
  webSocModel!: WebSocketModel;

  constructor(private devServ: DeviceService, private router: Router, private websocket: WebSocketService) {}

  ngOnInit(): void {
    // @ts-ignore
    const userId: number = +sessionStorage.getItem("userId");
    this.devServ.getUserDevices(userId).subscribe(device => {
      this.deviceList = device;
      console.log(this.deviceList)
    });
  }

  goToCreateDevice() {
    this.router.navigateByUrl("/create-device")
  }

}
