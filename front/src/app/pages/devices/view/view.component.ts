import {Component, OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../device.service";
import {DeviceDTO2} from "../../../shared/model/dto/DeviceDTO2";
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {SensorService} from "../sensor.service";
import {ActivatedRoute} from "@angular/router";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {MeasurementService} from "../measurement.service";

@Component({
    selector: 'app-view',
    templateUrl: './view.component.html',
    styleUrls: ['./view.component.scss'],
    standalone: false
})


export class ViewComponent implements OnInit, OnDestroy {

  selectedDevice?: DeviceDTO;
  category: string[] = [];


  number: any[] | undefined;

  constructor(private devServ: DeviceService, private sensorService: SensorService, private route: ActivatedRoute) {

  }

  ngOnDestroy(): void {

  }

  ngOnInit() {
    const deviceId = this.route.snapshot.paramMap.get('deviceID');
    console.log(deviceId);
    if (deviceId != null) {
      this.devServ.getDeviceDTOById(parseInt(deviceId)).subscribe(device => {
        this.selectedDevice = device;
        this.sensorService.getByIds(this.selectedDevice?.sensorId).subscribe(sensor => {
          sensor.forEach(sen => {
            //TODO ha itt a sensor nagyobb mint egy az nem biztos hogy jó lesz így
            this.category.push(sen.category);
            // this.deviceSensor = JSON.parse(sen.fieldJSON);
          })
          console.log(this.category)
        }, error => {
          console.error(error);
        });
      }, error => {
        console.error(error)
      })

    }

  }


  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);
    });
  }

  sendPayloadToDevice(device: DeviceDTO2, payloadKey: string, payload: any) {
    console.log("device id: " + device.devicesId);
    this.devServ.sendPayloadToDevice(device, payloadKey, payload).subscribe(() => {
      console.log("sikeresen elkuldve");
    }, error => {
      console.error(error);
    });

  }



}
