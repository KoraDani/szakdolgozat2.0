import {Component, Input} from '@angular/core';
import {DeviceService} from "../../../shared/service/device.service";

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})


export class ViewComponent {

  @Input() deviceInput: any;
  number: number[] | undefined;

  constructor(private devServ: DeviceService) {
    console.log("View devices"+this.deviceInput);
  }

  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);
    });
  }

  sendPayloadToDevice(payloadKey: string,topic: string, payload: any){
    this.devServ.sendPayloadToDevice(payloadKey,topic,payload.toString()).subscribe(()=>{
      console.log("sikeresen elkuldve");
    }, error => {
      console.error(error);
    });
  }
}
