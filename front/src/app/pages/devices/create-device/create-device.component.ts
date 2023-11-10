import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceService} from "../../../shared/service/device.service";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {Devices} from "../../../shared/model/Devices";

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
  dynamicForm: FormGroup;

  constructor(private devServ: DeviceService,private fb: FormBuilder) {
    // this.deviceForm = this.fb.group({
    //   deviceName: ['', [Validators.required]],
    //   deviceType: ['', [Validators.required]],
    //   location: ['', [Validators.required]]
    // })
    this.dynamicForm = this.fb.group({
      fields: this.fb.array([this.createInputControl()]),
    });
  }

  saveDevice() {
    // if(this.deviceForm.valid){
      let devices: Devices = {
        devicesId: null,
        deviceName: this.deviceForm.get("deviceName")?.value,
        deviceType: this.deviceForm.get("deviceType")?.value,
        location: this.deviceForm.get("location")?.value,
        userId:1
      }
      console.log(devices);
      this.devServ.saveDevice(devices, this.fields.value as string[]).subscribe(()=>{
        console.log("Device saved");
      },error => {
        if(error.status === 400){
          console.log("Valamit nem töltöttél ki");
          console.log(this.fields.value)
        }
      });
    // }
  }

  createInputControl() {
    return new FormControl('', Validators.required);
  }

  addInputField() {
    this.fields.push(this.createInputControl());
  }

  get fields() {
    return this.dynamicForm.get('fields') as FormArray;
  }

  /**
   * Első lehetőség: drag&droppal úgy hogy a felhasználónak meg van addva milyen inputokat tud
   *                  használni és az alapján rakja össze
   *
   * Második lehetőség: mint ahogy MySQL-ben beírja az input nevét, megadja típusát, stb. és így össze állítja
   *                  és így le lesz generálva naki hogy miket szeretne fogadni a saját eszközétől
   *
   * Harmadik lehetőség: JSON-ként eltárolom azt hogy a felhasználó miket akar megjeleníteni és az alapján fogom
   *                  legenerálni a monitorozást.
   *
   *
   *    Mind a háromban ez az egy funkció közös: Miután ez megtörént és a monitor oldalon van akkor a felhasználó funkciókat adhat hozzá hogy ha
   *                  felkapcsolja a lámpát történjen, ez meg az, vagy a szoba hőmérséklete elér egy pontott akkor történyen más dolog
   * */
  saveInputData() {
    console.log(this.fields.value);
  }
}
