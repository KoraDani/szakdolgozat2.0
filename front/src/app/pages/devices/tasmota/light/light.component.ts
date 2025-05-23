import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MqttService} from "../../mqtt.service";
import {WebSocketService} from "../../WebSocketService";
import {WebSocketModel} from "../../WebSocketModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {MatCardModule, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {MatSlider, MatSliderThumb} from '@angular/material/slider';
import convert from "color-convert";
import {ScheduleTimeComponent} from "../../schedule/schedule-time/schedule-time.component";
import {MatLabel} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";


@Component({
  selector: 'app-light',
  standalone: true,
  templateUrl: './light.component.html',
  styleUrls: ['./light.component.scss'],
  changeDetection: ChangeDetectionStrategy.Default,
  imports: [MatCardModule, MatCardHeader, MatCardTitle, MatCardContent, MatSlideToggle, MatSlider, MatSliderThumb, FormsModule, MatButtonModule]
})
export class LightComponent implements OnInit, OnDestroy {

  @Input({required: true}) selectedDevice?: DeviceDTO;

  isColor: boolean = false;
  isHSBColor: boolean = false;
  isWhite: boolean = false;
  isCT: boolean = false;

  power: boolean | true | false = false;
  ct: number = 0;

  dimmer: number = 0;
  rgb: any;

  //TODO light webSocModel befejezése
  webSocModel!: WebSocketModel;
  loading: boolean = true;

  constructor(private cdr: ChangeDetectorRef, private mqttService: MqttService, private websocket: WebSocketService) {

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
    if (this.selectedDevice) {
      this.webSocModel = {
        destination: '/app/power',
        listen: '/topic/power',
        topic: this.selectedDevice?.topic,
        message: [{
          prefix: "cmnd/",
          postfix: "/HSBColor",
          msg: " "
        }, {
          prefix: "stat/",
          postfix: "/RESULT",
          msg: " "
        }]
      };
    }
    this.getDeviceStatus(this.webSocModel);
    this.selectedDevice?.sensors.forEach(sensor => {
      this.setFields(sensor.fieldJSON);
    })
    this.websocket.subscribeToMessages()
      .subscribe(
        this.webSocModel.listen,
        (message) => {
          this.setLightStatus(message.body);
          this.loading = false;
        });
  }


  getDeviceStatus(webSocketModel: WebSocketModel) {
    this.websocket.send(webSocketModel);
  }

  setFields(str: string) {
    for (const e of JSON.parse(str)) {
      switch (e) {
        case 'Color':
          this.isColor = true;
          break;
        case 'HSBColor':
          this.isHSBColor = true;
          break;
        case 'White':
          this.isWhite = true;
          break;
        case 'CT':
          this.isCT = true;
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
          break;
        case 'Dimmer':
          // @ts-ignore
          this.dimmer = msgElement[1];
          break;
        case 'HSBColor':
          // @ts-ignore
          this.rgb = this.convertHSBtoHex(msgElement[1].split(','))
          break;
        case 'CT':
          this.ct = Number(msgElement[1]);
          break;
      }
    }
    this.cdr.detectChanges();
  }

  convertHSBtoHex(hsb: string[]) {
    return "#" + convert.hsv.hex(+hsb[0], +hsb[1], +hsb[2]).toString();
  }

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
      // console.log(val)
    });
  }

  onColorPick() {
    // console.log(this.rgb)
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/Color", this.hexng1 + "").subscribe(val => {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/Color",
        msg: this.rgb
      },
        {
          prefix: "cmnd/",
          postfix: "/Dimmer",
          msg: this.dimmer
        }]
    };
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      // console.log(val)
    });
  }

  changeToWhite() {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/CT", value+"").subscribe(val => {
    this.webSocModel = {
      destination: '/app/light',
      listen: '/topic/light',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "cmnd/",
        postfix: "/White",
        msg: this.dimmer + ""
      }]
    };
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      // console.log(val);
    }, error => {
      console.error(error);
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
      // console.log(val);
    }, error => {
      console.error(error);
    });
  }

  ngOnDestroy(): void {
    // this.websocket.disconnect();
  }
}
