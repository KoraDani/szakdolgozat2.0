import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../../shared/model/dto/MeasurementDTO";
import {MeasurementService} from "../../measurement.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

interface DataPoint {
  type: string;
  name: string;
  showInLegend: boolean;
  yValueFormatString: string;
  dataPoints: any[];
}

@Component({
    selector: 'app-temperature',
    templateUrl: './temperature.component.html',
    styleUrls: ['./temperature.component.scss'],
    standalone: false
})
export class TemperatureComponent implements OnInit {
  @Input({required: true}) deviceId?: number;

  tempList: { x: number, y: number }[] = []
  humiList: { x: number, y: number }[] = []
  dewList: { x: number, y: number }[] = []
  measurmentSize: number = 0;
  index: number = 1;

  constructor(private measurementService: MeasurementService) {
  }

  ngOnInit() {
    if (this.deviceId){
      this.measurementService.getMeasurementByDeviceId(this.deviceId, 10).subscribe(meas => {
        this.measurmentSize = meas.length;
        meas.forEach(v => {
          switch (v.sensorType) {
            case "Temperature":
              this.tempList.push({x: this.index, y: +v.value});
              this.index++;
              break;
            case "Humidity":
              this.humiList.push({x: this.index - 10, y: +v.value});
              this.index++;
              break;
            case "DewPoint":
              this.dewList.push({x: this.index - 20, y: +v.value});
              this.index++;
              break;
          }
        })
      }, error=> {
        console.error(error);
      })
    }



  }


  chartOptions = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "New York Climate - 2021"
    },
    axisX: {
      valueFormatString: "#",
      interval: 1
    },
    axisY: {
      title: "Temperature",
      suffix: "°C"
    },
    toolTip: {
      shared: true
    },
    legend: {
      cursor: "pointer",
      itemclick: function (e: any) {
        if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
          e.dataSeries.visible = false;
        } else {
          e.dataSeries.visible = true;
        }
        e.chart.render();
      }
    },
    data: [{
      type: "line",
      name: "Temperature",
      showInLegend: true,
      yValueFormatString: "#,###°C",
      dataPoints: this.tempList
    },
      {
        type: "line",
        name: "Humidity",
        showInLegend: true,
        yValueFormatString: "#,##%",
        dataPoints: this.humiList
      },
      {
        type: "line",
        name: "DewPoint",
        showInLegend: true,
        yValueFormatString: "#,###°C",
        dataPoints: this.dewList
      }
    ]
  }


}
