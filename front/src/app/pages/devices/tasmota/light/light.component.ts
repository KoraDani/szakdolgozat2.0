import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MqttService} from "../../mqtt.service";
import {WebSocketService} from "../../WebSocketService";
import {WebSocketModel} from "../../WebSocketModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {SensorService} from "../../sensor.service";
import {Sensor} from "../../../../shared/model/Sensor";

@Component({
  selector: 'app-light',
  templateUrl: './light.component.html',
  styleUrls: ['./light.component.scss']
})
export class LightComponent implements OnInit {

  @Input({required: true}) selectedDevice?: DeviceDTO;

  hex = new FormControl('');

  color: any = '#ffffff';
  power: string = 'OFF';
  ct: number = 0;

  switchStatus!: boolean;
  status!: "ON" | "OFF";
  dimmerValue4: number = 0;
  hexng1: any;

  //TODO light webSocModel befejezése
  webSocModel!: WebSocketModel;
  loading: boolean = true;
  constructor(private sensorService: SensorService, private mqttService: MqttService, private websocket: WebSocketService) {

  }

  onClick() {
    this.power = this.power == "OFF" ? this.power = "ON" : this.power = "OFF";
  }

  ngOnInit(): void {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/HSBColor",
        msg: " "
      }]
    };
    this.websocket.send(this.webSocModel);


    console.log("sending message to websocket " + this.selectedDevice?.topic)

    this.websocket.listen('/topic/light', message => {
      this.setLightStatus(message);
    });
  }

  setLightStatus(msg: string) {
    for (const msgElement of Object.entries(JSON.parse(msg))) {
      switch (msgElement[0]) {
        case 'POWER':
          // @ts-ignore
          this.power = msgElement[1] ? "OFF" : msgElement[1];
          console.log(1)
          break;
        case 'Dimmer':
          // @ts-ignore
          this.dimmerValue4 = msgElement[1];
          console.log(2)

          break;
        case 'HSBColor':
          this.hexng1 = msgElement[1];
          console.log(3)

          break;
        case 'CT':
          this.ct = Number(msgElement[1]);
          console.log(4)

          break;
      }
    }
  }

  // //TODO webSocModelként átírni az ez alattlévőket
  // onChange(n: number) {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer", n + "").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val)
  //   });
  //   // this.dimmer = "box-shadow: 5px 10px rgb(255, 230, 117, "+(n/100)+");"
  //   console.log(this.dimmer);
  // }
  // onToggle() {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/POWER", "toggle").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val);
  //   }, error => {
  //     console.error(error);
  //   });
  // }
  //
  // onChange2(n: number) {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer", n + "").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val)
  //   });
  // }
  //
  // mapToWarmColor(value: number) {
  //   // this.red = 255;
  //   // this.green = Math.floor(255 - (value * 0.55));
  //   // this.blue = Math.floor(255 - (value * 2.55));
  //   // this.dimmer2 = "box-shadow: 5px 10px rgb(" + this.red + ", " + this.green + ", " + this.blue + ", " + (this.dimmerValue2 / 100) + ");"
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/CT", value+"").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val);
  //   }, error => {
  //     console.error(error);
  //   });
  // }
  //
  // onChange3(n: number) {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer1", n + "").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val)
  //   });
  // }
  //
  // onChange4(n: number) {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer2", n + "").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val)
  //   });
  // }
  //
  //
  //
  // onColorPick() {
  //   // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Color", this.hexng + "").subscribe(val => {
  //   this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
  //     console.log(val)
  //   });
  //
  // }

  changeDimmer(n: number) {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer2", n + "").subscribe(val => {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/Dimmer2",
        msg: n + ""
      }]
    };
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      console.log(val)
    });
  }

  rgbDimmer(n: number) {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Dimmer1", n + "").subscribe(val => {
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      console.log(val)
    });
  }

  onColorPick() {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Color", this.hexng1 + "").subscribe(val => {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/Color",
        msg: this.hexng1
      }]
    };
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      console.log(val)
    });
  }

  changeCT(value: number) {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/CT", value+"").subscribe(val => {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/CT",
        msg: value + ""
      }]
    };
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      console.log(val);
    }, error => {
      console.error(error);
    });
  }
}
