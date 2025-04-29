import {Component, Input, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {WebSocketService} from "../../WebSocketService";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {MatSlideToggle} from "@angular/material/slide-toggle";

@Component({
  selector: 'app-switch',
  standalone: true,
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss'],
  imports: [FormsModule, MatSlideToggle]
})
export class SwitchComponent implements OnInit {
  @Input({required: true}) selectedDevice?: DeviceDTO;

  power1: boolean = false;
  power2: boolean = false;
  power3: boolean = false;

  constructor(private websocket: WebSocketService) {
  }

  ngOnInit(): void {
    const webSocModel = {
      destination: '/app/power',
      listen: '/topic/power',
      topic: this.selectedDevice?.topic + "",
      message: [{
        prefix: "stat/",
        postfix: "/Power1",
        msg: ""
      }, {
        prefix: "stat/",
        postfix: "/Power2",
        msg: ""
      }, {
        prefix: "stat/",
        postfix: "/Power3",
        msg: ""
      },{
        prefix: "cmnd/",
        postfix: "/backlog",
        msg: "power1; power2; power3;"
      }]
    };
    this.websocket.send(webSocModel);


    this.websocket.subscribeToMessages().subscribe(webSocModel.listen, (message) => {
      console.log('Received:', message.body);
      this.setSwitchStatus(message.body);
    });
  }

  setSwitchStatus(message: string) {
    console.log(message);
    for (const e of Object.entries(JSON.parse(message))) {
      console.log(e)
      if(e[1] == "ON"){
        this.power1 = true;
      }else if(e[1] == "ON"){
        this.power2 = true;
      }else if(e[1] == "ON"){
        this.power3 = true;
      }
    }
  }

  onClick(power: string) {
    console.log(power)
  }
}
