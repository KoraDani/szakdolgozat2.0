import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {DeviceService} from "../../../shared/service/device.service";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit{

  @Input() devicesObjectInput: Array<DeviceDTO> = [];
  @Output() deviceObjectEmmiter: EventEmitter<any> = new EventEmitter();
  chosenDevice: any = this.devicesObjectInput[0];

  constructor(private devServ: DeviceService, private router: Router) {
  }
  ngOnInit(): void {

  }

  reload(){
    this.deviceObjectEmmiter.emit(this.chosenDevice);
  }

  selectDevice(device: DeviceDTO) {
    this.chosenDevice = device;
    this.reload();
  }
  deleteDevice(deviceId: number) {
    console.log("Eszköz törlés: " + deviceId);
    this.devServ.deleteDevice(deviceId).subscribe(() => {
      console.log("Eszköz sikeresen törölve");
    }, error => {
      console.error(error);
    });
  }

  createNewDevice() {
    this.router.navigateByUrl("/create-device");
  }
}
