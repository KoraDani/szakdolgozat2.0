import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {Devices} from "../../shared/model/Devices";
import {Measurement} from "../../shared/model/Measurement";
import {TopicService} from "../../shared/service/topic.service";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit{
  devicesList: Devices[] = [];
  measurment: Map<string, string> = new Map<string, string>()

  constructor(private devServ: DeviceService, private topServ: TopicService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.devServ.getDevices().subscribe((dev: Devices[])=> {
      console.log(dev);
      this.devicesList = dev;
      console.log("Devices is loading");
    }, (error: any) => {
      console.error(error);
    });
    //TODO utolsó mérés plusz hogy megjelenjen az adat
    this.topServ.getMeasurment().subscribe((topic : Measurement[]) =>{
      // @ts-ignore
      this.measurment = new Map(Object.entries(JSON.parse(topic[0].payload)));
      console.log(this.measurment);
      console.log(topic);
    }, error => {
      console.error(error);
    });
  }
}
