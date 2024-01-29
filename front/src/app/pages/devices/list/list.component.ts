import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {DeviceService} from "../../../shared/service/device.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy{

  @Input() devicesObjectInput: Array<DeviceDTO> = [];
  @Output() deviceObjectEmmiter: EventEmitter<any> = new EventEmitter();
  chosenDevice: any = this.devicesObjectInput[0];

  constructor(private devServ: DeviceService, private router: Router) {
  }

  //TODO itt lehetne olyat hogy amikor kilistázzuk akkor csak pár dolog jelenyen meg de ha a nevére rákattint akkor tovább megy a view
  //és onnan meg lehet nézni a többi beállítást is.
  ngOnInit(): void {
    // @ts-ignore
    console.log(this.devicesObjectInput);

  }

  reload(){
    this.deviceObjectEmmiter.emit(this.chosenDevice);
  }

  selectDevice(device: DeviceDTO) {
    // console.log(device);
    this.devServ.setSelectedDevice(device);
    this.chosenDevice = device;
    this.reload();
    this.router.navigateByUrl("/view");
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

  ngOnDestroy(): void {

  }

}
