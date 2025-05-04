import {Component, effect, Input, OnDestroy, OnInit} from '@angular/core';
import {MeasurementService} from "../../measurement.service";
import {CanvasJSAngularChartsModule} from '@canvasjs/angular-charts';
import {WebSocketModel} from "../../WebSocketModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {WebSocketService} from "../../WebSocketService";
import {MeasurementDTO} from "../../../../shared/model/dto/MeasurementDTO";

interface DataPoint {
  type: string;
  name: string;
  showInLegend: boolean;
  yValueFormatString: string;
  dataPoints: any[];
}

@Component({
  selector: 'app-temperature',
  standalone: true,
  templateUrl: './temperature.component.html',
  styleUrls: ['./temperature.component.scss'],
  imports: [CanvasJSAngularChartsModule]
})
export class TemperatureComponent implements OnInit, OnDestroy {
  @Input() selectedDevice?: DeviceDTO;

  tempList: { x: number, y: number }[] = []
  humiList: { x: number, y: number }[] = []
  dewList: { x: number, y: number }[] = []

  measurmentSize: number = 0;

  index: number = 1;

  tempStatus: string = "";
  humidityStatus: string = "";
  dewpointStatus: string = "";

  webSocModel!: WebSocketModel;

  statusElementList: string[] = ["Temperature", "Humidity","DewPoint"]

  loading: boolean = false;

  constructor(private measurementService: MeasurementService, private websocket: WebSocketService) {
  }

  ngOnInit() {
    this.webSocModel = {
      destination: '/app/power',
      listen: '/topic/power',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/STATUS",
        msg: "8"
      },
        {
          prefix: "stat/",
          postfix: "/STATUS8",
          msg: " "
        }]
    };

    this.getDeviceStatus(this.webSocModel);

    this.websocket.subscribeToMessages().subscribe(this.webSocModel.listen, (message) => {
      this.tempStatus = JSON.stringify(JSON.parse(message.body)['StatusSNS']['DHT11']['Temperature']);
      this.humidityStatus = JSON.stringify(JSON.parse(message.body)['StatusSNS']['DHT11']['Humidity']);
      this.dewpointStatus = JSON.stringify(JSON.parse(message.body)['StatusSNS']['DHT11']['DewPoint']);

    });


    if (this.selectedDevice?.devicesId) {
      this.statusElementList.forEach(type => {
        // @ts-ignore
        this.measurementService.getMeasurementByDevIdAndType(this.selectedDevice?.devicesId, type, 10).subscribe(measurment => {
          switch (type) {
            case "Temperature":
              this.setTempList(measurment);
              break;
            case "Humidity":
              this.setHumidityStatus(measurment);
              break;
            // case "DewPoint":
            //   this.dewList.set(measurment);
            //   break;
          }

        });
      })

    }
  }

  setTempList(meas: MeasurementDTO[]){
    meas.forEach((meas, index) => {
      this.tempList.push({x: index+1, y: +meas.value});
    })
  }

  setHumidityStatus(meas: MeasurementDTO[]){
    meas.forEach((meas, index) => {
      this.humiList.push({x: index+1, y: +meas.value});
    })
  }


  getDeviceStatus(webSocketModel: WebSocketModel) {
    this.websocket.send(webSocketModel);
  }


  tempOptions = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Temperature"
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
    data: [{
      type: "line",
      name: "Temperature",
      showInLegend: true,
      yValueFormatString: "#,##°C",
      dataPoints: this.tempList
    }
    ]
  }

  humiOptions = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Humidity",
    },
    axisX: {
      valueFormatString: "#",
      interval: 1
    },
    axisY: {
      title: "Humidity",
      suffix: "%"
    },
    toolTip: {
      shared: true
    },
    data: [
      {
        type: "line",
        name: "Humidity",
        showInLegend: true,
        yValueFormatString: "#,##%",
        dataPoints: this.humiList
      }
    ]
  }


  protected readonly JSON = JSON;

  ngOnDestroy(): void {
    this.humiList = [];
    this.tempList = [];
  }
}
