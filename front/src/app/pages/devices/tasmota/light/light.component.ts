import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, signal} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MqttService} from "../../mqtt.service";
import {WebSocketService} from "../../WebSocketService";
import {WebSocketModel} from "../../WebSocketModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {SensorService} from "../../sensor.service";

@Component({
    selector: 'app-light',
    templateUrl: './light.component.html',
    styleUrls: ['./light.component.scss'],
    changeDetection: ChangeDetectionStrategy.Default,
    standalone: false
})
export class LightComponent implements OnInit {

  @Input({required: true}) selectedDevice?: DeviceDTO;

  hex = new FormControl('');

  isColor: boolean = false;
  isHSBColor: boolean = false;
  isWhite: boolean = false;
  isCT: boolean = false;

  color: any = '#ffffff';
  power: boolean | true | false = false;
  ct: number = 0;

  switchStatus!: boolean;
  status!: "ON" | "OFF";
  dimmerValue4: number = 0;
  hexng1: any;

  //TODO light webSocModel befejezése
  webSocModel!: WebSocketModel;
  loading: boolean = true;

  constructor(private cdr: ChangeDetectorRef, private sensorService: SensorService, private mqttService: MqttService, private websocket: WebSocketService) {

  }

  onClick() {
    this.power = !this.power;
    this.webSocModel = {
      destination: '/app/power',
      listen: '/topic/power',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/Power",
        msg: this.power ? "ON" : "OFF"
      }]
    };
    this.websocket.send(this.webSocModel);
  }

  ngOnInit(): void {

    console.log(this.selectedDevice?.sensorId)
    this.sensorService.getByIds(this.selectedDevice?.sensorId).subscribe(sen => {
      sen.forEach(s => {
        this.setFields(s.fieldJSON);
      })
    })
    this.getDeviceStatus();

    this.websocket.listen('/topic/power', message => {
      console.log(message)
      this.setLightStatus(message);
    });

    this.loading = false;

  }


  //console.log("sending message to websocket " + this.selectedDevice?.topic)

  getDeviceStatus() {
    this.webSocModel = {
      destination: '/app/power',
      listen: '/topic/power',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/HSBcolor",
        msg: " "
      }]
    };
    this.websocket.send(this.webSocModel);
  }

  setFields(str: string) {
    for (const e of JSON.parse(str)) {
      switch (e) {
        case 'Color':
          this.isColor = true;
          console.log(this.isColor)
          break;
        case 'HSBColor':
          this.isHSBColor = true;
          console.log(this.isHSBColor)
          break;
        case 'White':
          this.isWhite = true;
          console.log(this.isWhite)
          break;
        case 'CT':
          this.isCT = true;
          console.log(this.isCT)
          break;
      }
    }
    this.cdr.detectChanges();
  }

  setLightStatus(msg: string) {
    for (const msgElement of Object.entries(JSON.parse(msg))) {
      switch (msgElement[0]) {
        case 'POWER':
          this.power = msgElement[1] != "OFF";
          console.log(this.power)
          break;
        case 'Dimmer':
          // @ts-ignore
          this.dimmerValue4 = msgElement[1];
          console.log(msgElement[1])

          break;
        case 'HSBColor':
          this.hexng1 = msgElement[1];
          console.log(msgElement[1])
          break;
        case 'CT':
          this.ct = Number(msgElement[1]);
          console.log(msgElement[1])

          break;
      }
    }
    this.cdr.detectChanges();
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
