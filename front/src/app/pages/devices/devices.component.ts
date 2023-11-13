import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {Devices} from "../../shared/model/Devices";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit{
  devicesList: Devices[] = [];

  constructor(private devServ: DeviceService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.devServ.getDevices("1").subscribe((dev: Devices[])=> {
      console.log(dev);
      this.devicesList = dev;
      console.log("Devices is loading");
    }, (error: any) => {
      console.error(error);
    });
  }
}
