import {Component, OnInit} from '@angular/core';
import {DeviceService} from "../../shared/service/device.service";
import {Devices} from "../../shared/model/Devices";
import {Measurement} from "../../shared/model/Measurement";
import {TopicService} from "../../shared/service/topic.service";
import {DeviceDTO} from "../../shared/model/dto/DeviceDTO";
import {ShowDeviceAndField} from "../../shared/model/dto/ShowDeviceAndField";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent implements OnInit{
  devicesList: DeviceDTO[] = [];
  measurmentMap: Map<string, string> = new Map<string, string>()
  showDevAndField: ShowDeviceAndField[] = [];

  constructor(private devServ: DeviceService, private topServ: TopicService) {
  }

  ngOnInit(): void {
    //TODO most hogy már megvan minden ami kell hogy teljesen moduláris legyen most már csak a fronton kell megvalósítani

    // @ts-ignore
    this.devServ.getDevices().subscribe((dev: DeviceDTO[])=> {
      console.log(dev);
      this.devicesList = dev;
      // this.convertFieldsAndMeasurment(dev);
      console.log("Devices is loading");
    }, (error: any) => {
      console.error(error);
    });
  }

  //TODO további CRUD műveleteket megvalósítani, Olvasni tud, most már csak törölni, szerkeszteni kell

  // convertFieldsAndMeasurment(dev: DeviceDTO[]){
  //   let fieldKeyArr : string[];
  //   for (let i = 0; i < dev.length; i++){//DeviceDTO listán végig iterálás
  //     let mas = dev[i].measurement?.payload;//Measrument JSON elmentése mindegyik iterációban a rövidebb kód miatt
  //     // @ts-ignore
  //     for (var val in mas){ //Payload JSON-önt való végig iterálás
  //       for(let j = 0; j < dev[i].fieldKey.length; j++){//A devicenak a fieldjein végig iterálás hogy leellenőrizni hogy ugyan az a fieldKey és a JSON key
  //         if(dev[i].fieldKey[j] == val){//Ellenőrzés hogy egyenlő-e
  //           this.showDevAndField[i] = {
  //             // @ts-ignore
  //             devicesId: dev[i].devicesId,
  //             deviceName: dev[i].deviceName,
  //             deviceType: dev[i].deviceType,
  //             location: dev[i].location
  //           }
  //         }
  //       }
  //       // @ts-ignore
  //       this.measurmentMap.set(val, mas[val]);
  //     }
  //   }
  //
  //   for (let i = 0; i < dev.length; i++){
  //
  //   }

  // }

}
