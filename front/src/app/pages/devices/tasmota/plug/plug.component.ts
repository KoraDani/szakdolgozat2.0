import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../../shared/model/dto/MeasurementDTO";
import {SensorService} from "../../sensor.service";
import {Sensor} from "../../../../shared/model/Sensor";
import {MeasurementService} from "../../measurement.service";
import {WebSocketService} from "../../WebSocketService";
import {MqttService} from "../../mqtt.service";
import {WebSocketModel} from "../../WebSocketModel";

@Component({
  selector: 'app-plug',
  templateUrl: './plug.component.html',
  styleUrls: ['./plug.component.scss']
})
export class PlugComponent implements OnInit{
  measurementList: MeasurementDTO[] = [];
  @Input({required: true}) deviceId?: number;
  @Input({required: true}) topic!: string;
  sensor: Sensor[] = [];
  switchStatus!: boolean;
  status!: "ON" |"OFF";

  //TODO plug webSocModel befejezése
  webSocModel: WebSocketModel = {
    destination: '/app/switch',
    listen: '/topic/switch',
    topic: this.topic,
    message: []
  };

  constructor(private sensorService: SensorService, private measusermentService: MeasurementService, private websocket: WebSocketService, private  mqttService: MqttService) {

  }

  ngOnInit(): void {
    if (this.deviceId != null) {
      this.measusermentService.getMeasurementByDeviceId(this.deviceId, 1).subscribe(meas => {
        this.measurementList = meas;
        console.log(this.measurementList)
      }, error => {
        console.error(error);
      })
      this.sensorService.getByDeviceId(this.deviceId).subscribe(sen => {
        this.sensor = sen;
        console.log(this.sensor)
      }, error => {
        console.error(error);
      });
      if (this.topic != null) {
        console.log("sending message to websocket " + this.topic)
        this.websocket.send(this.webSocModel);
      }
      this.websocket.listen(this.webSocModel.listen, message => {
        this.switchStatus = message.includes("ON");
        console.log(this.switchStatus)
      });
    }
  }

  onToggle() {
    // this.mqttService.sendMessageToDevice("cmnd/"+this.topic+"/POWER", "toggle").subscribe(val => {
    this.mqttService.sendMessageToDevice(this.webSocModel).subscribe(val => {
      console.log(val);
    }, error => {
      console.error(error);
    });
  }


  protected readonly Object = Object;

  protected readonly JSON = JSON;
}
