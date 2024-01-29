import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {DeviceService} from "../../../shared/service/device.service";
import {MeasurementService} from "../../../shared/service/measurement.service";
import {Measurement} from "../../../shared/model/Measurement";
import {CanvasJS} from "@canvasjs/angular-charts";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";


@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})

//TODO hőmérőként megjelenítani a hőmérsékletett


export class ViewComponent implements OnInit, OnDestroy {


  // @ts-ignore
  deviceInput: DeviceDTO = this.devServ.getSelectedDevice();
  // deviceInput: DeviceDTO = {
  //   devicesId: 1,
  //   deviceName: "SzobaTemp",
  //   deviceType: "1",
  //   location: "DaniSzoba",
  //   topic: "home/haloszoba/temp",
  //   active: 1,
  //   fieldKey: ["Hőmérséklet", "Páratartalom"],
  //   fieldType: [2, 2],
  //   payloadKey: ["Hőmérséklet", "Páratartalom"],
  //   payloadValue: ["21", "67"]
  // };
  number: number[] | undefined;
  // mesList: Measurement[] = [];
  mesList: Measurement[] = [
    {measurementId: '1', payloadKey: 'Hőmérséklet', payloadValue: '20.29999924', time: '2024-01-17T20:30:40'},
    {measurementId: '2', payloadKey: 'Páratartalom', payloadValue: '40', time: '2024-01-17T20:30:40'},
    {measurementId: '3', payloadKey: 'Hőmérséklet', payloadValue: '21.29999924', time: '2024-01-17T20:32:40'},
    {measurementId: '4', payloadKey: 'Páratartalom', payloadValue: '45', time: '2024-01-17T20:32:40'},
    {measurementId: '5', payloadKey: 'Hőmérséklet', payloadValue: '22.29999924', time: '2024-01-17T20:33:40'},
    {measurementId: '6', payloadKey: 'Páratartalom', payloadValue: '50', time: '2024-01-17T20:33:40'},
    {measurementId: '7', payloadKey: 'Hőmérséklet', payloadValue: '23.29999924', time: '2024-01-17T20:34:40'},
    {measurementId: '8', payloadKey: 'Páratartalom', payloadValue: '55', time: '2024-01-17T20:34:40'},
    {measurementId: '9', payloadKey: 'Hőmérséklet', payloadValue: '22.29999924', time: '2024-01-17T20:35:40'},
    {measurementId: '10', payloadKey: 'Páratartalom', payloadValue: '50', time: '2024-01-17T20:35:40'},
    {measurementId: '11', payloadKey: 'Hőmérséklet', payloadValue: '21.29999924', time: '2024-01-17T20:36:40'},
    {measurementId: '12', payloadKey: 'Páratartalom', payloadValue: '45', time: '2024-01-17T20:36:40'}

  ];
  dataP: any = this.convertMeasurementIntoDataPoints();

  constructor(private devServ: DeviceService, private mesServ: MeasurementService) {
    // console.log("View devices" + this.deviceInput);
    console.log("létre lettem hozva: "+ this.deviceInput.payloadValue[0]);
  }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    // localStorage.setItem('device', this.devServ.getSelectedDevice());
    // // @ts-ignore
    // this.deviceInput = localStorage.getItem('device');
    // console.log(this.deviceInput.devicesId);
// @ts-ignore
//     this.devServ.getDeviceById(localStorage.getItem('device')).subscribe((dev: DeviceDTO) => {
//       this.deviceInput = dev;
//       console.log(dev);
//     }, error => {
//       console.error(error);
//     });

    // this.mesServ.getMeasurementByDeviceId(this.deviceInput.devicesId).subscribe((mes: Measurement[]) => {
    //   this.mesList = mes;
    //   console.log(this.mesList);
    //
    // }, error => {
    //   console.error(error);
    // });
  }

  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);
    });
  }

  sendPayloadToDevice(payloadKey: string, topic: string, payload: any) {
    this.devServ.sendPayloadToDevice(payloadKey, topic, payload).subscribe(() => {
      console.log("sikeresen elkuldve");
    }, error => {
      console.error(error);
    });

  }

  chartOptions = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: this.deviceInput.deviceName
    },
    axisX: {
      valueFormatString: "#"
    },
    axisY: {
      title: "Temperature",
      suffix: "°C"
    },
    toolTip: {
      shared: true
    },
    data: this.dataP
  }

  convertMeasurementIntoDataPoints() {
    let data: any = [];

    // @ts-ignore
    // console.log(this.deviceInput.fieldKey[0]);

    for (let i = 0; i < this.deviceInput.fieldKey.length; i++) {
      data[i] = {
        type: "line",
        name: this.deviceInput.fieldKey[i],
        showInLegend: true,
        yValueFormatString: "#,#########°C",
        dataPoints: []
      };
      for (let j = this.deviceInput.payloadKey.length-50; j < this.deviceInput.payloadKey.length; j++) {
        if (this.deviceInput.fieldKey[i] == this.deviceInput.payloadKey[j]) {
          // data[i].dataPoints.push({x: new Date(this.mesList[j].time), y: parseFloat(this.mesList[j].payloadValue)})
          data[i].dataPoints.push({y: parseFloat(this.deviceInput.payloadValue[j])})
        }
      }
    }

    console.log(data);

    return data;
  }


}
