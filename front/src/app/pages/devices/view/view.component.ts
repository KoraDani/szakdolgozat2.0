import {Component, effect, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../device.service";
import {SensorService} from "../sensor.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {TemperatureComponent} from '../tasmota/temperature/temperature.component';
import {PlugComponent} from '../tasmota/plug/plug.component';
import {LightComponent} from '../tasmota/light/light.component';
import {SwitchComponent} from "../tasmota/switch/switch.component";
import {ScheduleComponent} from "../tasmota/schedule/schedule.component";

@Component({
  selector: 'app-view',
  standalone: true,
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss'],
    imports: [TemperatureComponent, PlugComponent, LightComponent, SwitchComponent, ScheduleComponent]
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

  // selectedDevice!: DeviceDTO;

  isLoaded = false;

  constructor(private devServ: DeviceService, private sensorService: SensorService, private route: Router) {
    // @ts-ignore
    this.selectedDevice = this.route.getCurrentNavigation()?.extras.state.device
    if (this.selectedDevice.deviceName != "") {
      this.isLoaded = true;
    }
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

  ngOnInit(): void {
    console.log(this.selectedDevice)
  }


}
