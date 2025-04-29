//TODO alap automatizálás megcsinálása

import {Component, effect, OnInit} from '@angular/core';
import {DeviceService} from "./device.service";
import {Router} from "@angular/router";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {MatFabButton, MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatCard, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {WebSocketService} from "./WebSocketService";

@Component({
  selector: 'app-devices',
  standalone: true,
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss'],
  imports: [MatFabButton, MatIcon, MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatButton]
})
export class DevicesComponent implements OnInit {

  number: number[] | undefined;
  deviceList: DeviceDTO[] = [];

  constructor(private devServ: DeviceService, private router: Router, private websoc: WebSocketService) {
    effect(() => {
      this.deviceList = this.devServ.devicesDTO();
    })
  }

  ngOnInit(): void {
    this.devServ.getUserDevices()
  }

  goToCreateDevice() {
    this.router.navigateByUrl("/create-device")
  }

  viewDevice(device: DeviceDTO) {
    // this.devServ.chosenDevice.set(device);
    this.router.navigateByUrl("/view", {state: {device: device}});
  }
}
