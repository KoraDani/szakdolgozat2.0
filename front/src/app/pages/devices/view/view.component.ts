import {Component,  OnDestroy, OnInit} from '@angular/core';
import {DeviceService} from "../../../shared/service/device.service";
import {DeviceDTO2} from "../../../shared/model/dto/DeviceDTO2";
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {FieldDOT} from "../../../shared/model/dto/FieldDTO";
import {FieldService} from "../../../shared/service/field.service";



@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})

//TODO hőmérőként megjelenítani a hőmérsékletett


export class ViewComponent implements OnInit, OnDestroy {

  colorPicker: any;

  // @ts-ignore
  deviceInput = JSON.parse(localStorage.getItem("selectedDevice"));

  number: any[] | undefined;
  // @ts-ignore
  measirmentList: Array<Array<MeasurementDTO>> = JSON.parse(localStorage.getItem("deviceListMeasrument"));

  dataP: any = this.convertMeasurementIntoDataPoints();


  devicesByOutputField: FieldDOT[] = [];

  constructor(private devServ: DeviceService, private fieldServ: FieldService) {
  }

  ngOnDestroy(): void {

  }

  ngOnInit() {
    console.log(this.dataP);
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


  chartOptions = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: this.deviceInput.deviceName
    },
    axisX: {
      valueFormatString: "#,##"
    },
    axisY: {
      title: "Valami"
    },
    toolTip: {
      shared: true
    },
    data: this.dataP
  }

  convertMeasurementIntoDataPoints() {
    let d: any = [];

    let i: number = 0;
    for (let val of this.measirmentList) {
      let fieldName = val[0].payloadKey

      if(val[0].fieldType == '1'){
        d[i] = {
          type: "line",
          name: fieldName,
          showInLegend: true,
          yValueFormatString: "#,##",
          dataPoints: []
        };
        // @ts-ignore
        for (let k of val) {
          if(k.fieldType == '1'){
            fieldName = k.payloadKey
            d[i].dataPoints.push({y: parseFloat(k.payloadValue)});
          }
        }
      }
      i++;
    }

    console.log(d);

    return d;
  }


}
