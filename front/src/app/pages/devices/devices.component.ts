//TODO alap automatizálás megcsinálása

import {Component, effect, OnInit} from '@angular/core';
import {DeviceService} from "./device.service";
import {Router} from "@angular/router";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {MatFabButton, MatButton, MatIconButton} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatCard, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {WebSocketService} from "./WebSocketService";
import {MatMenu, MatMenuModule} from "@angular/material/menu";

@Component({
  selector: 'app-devices',
  standalone: true,
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss'],
  imports: [MatFabButton, MatIconModule, MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatButton, MatMenuModule, MatIconButton]
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

  onEdit(device: DeviceDTO) {
    this.router.navigateByUrl("/create-device", {state: {device: device}});
  }

  onDelete(device: DeviceDTO) {
    if(device.devicesId){
      this.devServ.deleteDevice(device.devicesId);
    }
  }
}
