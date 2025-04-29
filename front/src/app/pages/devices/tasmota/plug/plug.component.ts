import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../../shared/model/dto/MeasurementDTO";
import {SensorService} from "../../sensor.service";
import {Sensor} from "../../../../shared/model/Sensor";
import {MeasurementService} from "../../measurement.service";
import {WebSocketService} from "../../WebSocketService";
import {MqttService} from "../../mqtt.service";
import {WebSocketModel} from "../../WebSocketModel";
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";

@Component({
  selector: 'app-plug',
  standalone: true,

  templateUrl: './plug.component.html',
  styleUrls: ['./plug.component.scss'],
  imports: [MatSlideToggle, CanvasJSAngularChartsModule]
})
export class PlugComponent implements OnInit {
  measurementList: MeasurementDTO[] = [];

  measList: { x: number, y: number }[] = []

  @Input() selectedDevice?: DeviceDTO;
  sensor: Sensor[] = [];
  power!: boolean;
  status!: "ON" | "OFF";

  //TODO plug webSocModel befejezése
  webSocModel: WebSocketModel = {
    destination: '/app/power',
    listen: '/topic/power',
    topic: "",
    message: []
  };

  voltage: number = -1;
  current: number = 0;
  activePower: number = 0;
  energyToday: number = 0;
  energyYestarday: number = 0;
  enegyTotal: number = 0;

  constructor(private sensorService: SensorService, private measusermentService: MeasurementService, private websocket: WebSocketService, private mqttService: MqttService) {

  }

  ngOnInit(): void {
    console.log(this.selectedDevice)
    this.getDevicesStatus();

    this.websocket.subscribeToMessages().subscribe(this.webSocModel.listen, (message) => {
      if(message.body != undefined){
        this.setDeviceStatus(message.body);
      }
    })
    if (this.selectedDevice?.deviceId) {
      this.measusermentService.getMeasurementByDevIdAndType(this.selectedDevice?.deviceId, "Power", 10).subscribe(measurement => {
        measurement.forEach((m, index) => {
          this.measList.push({x: index + 1, y: +m.value});
        })
        // console.log(this.measList)
      });

    }
  }

  setDeviceStatus(msg: string) {
      for (let m of Object.entries(JSON.parse(msg)["StatusSNS"]["ENERGY"])) {
        switch (m[0]) {
          case "Voltage":
            console.log(m[1]);
            // @ts-ignore
            this.voltage = +m[1];
            break;
          case "Current":
            // @ts-ignore
            this.current = +m[1];
            break;
          case "Active Power":
            // @ts-ignore
            this.activePower = +m[1];
            break;
          case "Energy Today":
            // @ts-ignore
            this.energyToday = +m[1];
            break;
          case "Energy Yestarday":
            // @ts-ignore
            this.energyYestarday = +m[1];
            break;
          case "Energy Total":
            // @ts-ignore
            this.enegyTotal = +m[1];
            break;
        }
      }

  }

  getDevicesStatus() {
    if (this.selectedDevice?.deviceId != null) {
      this.webSocModel.topic = this.selectedDevice.topic + "";
      this.webSocModel.message =
        [
          {
            prefix: "cmnd/",
            postfix: "/STATUS",
            msg: "8"
          },
          {
            prefix: "cmnd/",
            postfix: "/POWER",
            msg: " "
          },
          {
            prefix: "stat/",
            postfix: "/STATUS8",
            msg: " "
          },
          {
            prefix: "stat/",
            postfix: "/RESULT",
            msg: " "
          }
        ];
      this.websocket.send(this.webSocModel);
    }

  }

  onClick() {
    this.power = !this.power;

    this.webSocModel.message = [{
      prefix: "cmnd/",
      postfix: "/Power",
      msg: this.power ? "ON" : "OFF"
    }]
    this.websocket.send(this.webSocModel);
  }

  powerOption = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Power Usage"
    },
    axisX: {
      valueFormatString: "#",
      interval: 1
    },
    axisY: {
      title: "Power",
      suffix: "W"
    },
    toolTip: {
      shared: true
    },
    data: [{
      type: "line",
      name: "Temperature",
      showInLegend: true,
      yValueFormatString: "#,## W",
      dataPoints: this.measList
    }
    ]
  }


  protected readonly Object = Object;

  protected readonly JSON = JSON;


}
