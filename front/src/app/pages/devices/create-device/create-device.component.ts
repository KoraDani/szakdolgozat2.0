import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceService} from "../../../shared/service/device.service";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";

@Component({
  selector: 'app-create-device',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.scss']
})
export class CreateDeviceComponent {
  deviceForm: FormGroup = new FormGroup({
    deviceName: new FormControl,
    deviceType: new FormControl,
    location: new FormControl
  });

  constructor(private devServ: DeviceService,private fb: FormBuilder) {
    this.deviceForm = this.fb.group({
      deviceName: ['', [Validators.required]],
      deviceType: ['', [Validators.required]],
      location: ['', [Validators.required]]
    })
  }

  saveDevice() {
    if(this.deviceForm.valid){
      let deviceDTO: DeviceDTO = {
        deviceName: this.deviceForm.get("deviceName")?.value,
        deviceType: this.deviceForm.get("deviceType")?.value,
        location: this.deviceForm.get("location")?.value
      }
      this.devServ.saveDevice(deviceDTO).subscribe(()=>{
        console.log("Device saved");
      },error => {
        console.error(error);
      });
    }
  }

  /**
   * Egyik lehetőség: drag&droppal úgy hogy a felhasználónak meg van addva milyen inputokat tud
   *                  használni és az alapján rakja össze
   *
   * Másik lehetőség: mint ahogy MySQL-ben beírja az input nevét, megadja típusát, stb. és így össze állítja
   *                  és így le lesz generálva naki hogy miket szeretne fogadni a saját eszközétől
   *
   * */
}
