import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceService} from "../../../shared/service/device.service";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-device',
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.scss']
})
export class CreateDeviceComponent {
  deviceForm: FormGroup = new FormGroup({
    deviceName: new FormControl,
    deviceType: new FormControl,
    location: new FormControl,
    topic: new FormControl
  });
  dynamicInputForm: FormGroup;
  dynamicCheckBoxForm: FormGroup;

  constructor(private devServ: DeviceService,private fb: FormBuilder,private router: Router) {
    this.deviceForm = this.fb.group({
      deviceName: ['', [Validators.required]],
      deviceType: ['', [Validators.required]],
      location: ['', [Validators.required]],
      topic: ['', [Validators.required]]
    })
    this.dynamicInputForm = this.fb.group({
      inputFields: this.fb.array([this.createInputControl()]),
    });
    this.dynamicCheckBoxForm = this.fb.group({
      checkBoxFields: this.fb.array([this.createCheckBoxControl()]),
    });
  }

  saveDevice() {
    if(this.deviceForm.valid){
      let dynamicCheckBoxArr : string[] = this.dynamicCheckBoxForm.get("checkBoxFields")?.value;
      let dynamicInputArr : string[] = this.dynamicInputForm.get("inputFields")?.value;
      let map = new Map<string, number>;

      dynamicCheckBoxArr.forEach((arr) =>{map.set(arr, 1);});
      dynamicInputArr.forEach((arr) =>{map.set(arr, 2);});

      let devices: DeviceDTO = {
        deviceName: this.deviceForm.get("deviceName")?.value,
        deviceType: this.deviceForm.get("deviceType")?.value,
        location: this.deviceForm.get("location")?.value,
        topic: this.deviceForm.get("topic")?.value,
        fieldKey: Array.from(map.keys()),
        fieldType: Array.from(map.values())
        //TODO localstoraget meg kell nézni hogy ide elmentem ez az userId-t
      }

      this.devServ.saveDevice(devices).subscribe(()=>{
        console.log("Device saved");
        this.router.navigateByUrl("/devices");
      },error => {
        if(error.status === 400){
          console.log("Valamit nem töltöttél ki");
          // console.log(this.fields.value)
        }
      });
    }
  }

  createInputControl() {
    return new FormControl('', Validators.required);
  }

  addInputField() {
    this.inputFields.push(this.createInputControl());
  }

  removeInputField(index: number){
    this.inputFields.removeAt(index);
  }
  createCheckBoxControl() {
    return new FormControl('', Validators.required);
  }

  addCheckBoxField() {
    this.checkBoxFields.push(this.createInputControl());
  }

  removeCheckBoxField(index: number){
    this.checkBoxFields.removeAt(index);
  }

  get inputFields() {
    return this.dynamicInputForm.get('inputFields') as FormArray;
  }
  get checkBoxFields() {
    return this.dynamicCheckBoxForm.get('checkBoxFields') as FormArray;
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
  // saveInputData() {
  //   console.log(this.fields.value);
  // }
}
