import {Component, effect, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../device.service";
import {SensorService} from "../sensor.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {TemperatureComponent} from '../tasmota/temperature/temperature.component';
import {PlugComponent} from '../tasmota/plug/plug.component';
import {LightComponent} from '../tasmota/light/light.component';
import {SwitchComponent} from "../tasmota/switch/switch.component";
import {ScheduleTimeComponent} from "../schedule/schedule-time/schedule-time.component";
import {ScheduleDeviceComponent} from "../schedule/schedule-device/schedule-device.component";
import {ScheduleTableComponent} from "../schedule/schedule-table/schedule-table.component";
import {ScheduleTaskModel} from "../schedule/schedule-time/ScheduleTaskModel";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";

@Component({
  selector: 'app-view',
  standalone: true,
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss'],
  imports: [TemperatureComponent, PlugComponent, LightComponent, SwitchComponent, ScheduleTimeComponent, ScheduleDeviceComponent, ScheduleTableComponent, MatSelectModule, MatOptionModule]
})


export class ViewComponent implements OnInit, OnDestroy {

  selectedDevice: DeviceDTO = {
    devicesId: -1,
    deviceName: "",
    sensors: [],
    measurements: [],
    location: "",
    topic: "",
    active: ""
  };

  selectedSchedule!: ScheduleTaskModel;

  // selectedDevice!: DeviceDTO;

  isLoaded = false;

  constructor(private devServ: DeviceService, private sensorService: SensorService, private router: Router) {
    // @ts-ignore
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras?.state?.['device']) {
      this.selectedDevice = nav.extras.state['device'];

      this.isLoaded = true;
    }
  }

  ngOnInit(): void {
    console.log(this.selectedDevice)
  }

  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId)
  }

  sendPayloadToDevice(device: DeviceDTO, payloadKey: string, payload: any) {
    this.devServ.sendPayloadToDevice(device, payloadKey, payload);
  }

  ngOnDestroy(): void {
    this.devServ.chosenDevice.set({
      devicesId: 0,
      deviceName: "",
      sensors: [],
      measurements: [],
      location: "",
      topic: "",
      active: ""
    });
  }




  addSelectedSchedule(st: ScheduleTaskModel) {
    this.selectedSchedule = st;
  }
}
