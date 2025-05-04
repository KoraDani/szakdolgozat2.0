import {Component, effect, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DeviceService} from "../device.service";
import {Router} from "@angular/router";
import {WebSocketService} from "../WebSocketService";
import {DetectedDevice} from "../../../shared/model/DetectedDevice";
import {SensorService} from "../sensor.service";
import {Sensor} from "../../../shared/model/Sensor";
import {DeviceDTO} from "../../../shared/model/dto/DeviceDTO";
import {WebSocketModel} from "../WebSocketModel";
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {NgIf} from '@angular/common';
import {MatStepLabel} from '@angular/material/stepper';
import {MatButton} from '@angular/material/button';
import {AuthService} from "../../../auth.service";
import {UserDTO} from "../../../shared/model/dto/UserDTO";

@Component({
  selector: 'app-create-device',
  standalone: true,
  templateUrl: './create-device.component.html',
  styleUrls: ['./create-device.component.scss'],
  imports: [FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatStepLabel, MatButton]
})
export class CreateDeviceComponent implements OnInit {
  deviceForm: FormGroup = new FormGroup({
    deviceName: new FormControl,
    deviceType: new FormControl,
    location: new FormControl,
    topic: new FormControl
  });

  TDeviceName?: string;

  dynamicKey?: string[];

  temperature: boolean = false;
  rgb: boolean = false;
  switch: boolean = false;

  detectedDevice?: DetectedDevice;
  detectedSensors: Sensor[] = [];
  json?: Map<any, any>;

  webSocModel: WebSocketModel = {
    destination: '/app/request',
    listen: '/topic/response',
    topic: "",
    message: [
      {
        prefix: "cmnd/",
        postfix: "/STATUS",
        msg: ""
      },
      {
        prefix: "cmnd/",
        postfix: "/STATUS",
        msg: "8"
      },
      {
        prefix: "cmnd/",
        postfix: "/GPIO",
        msg: ""
      },
      {
        prefix: "stat/",
        postfix: "/RESULT",
        msg: ""
      },
      {
        prefix: "stat/",
        postfix: "/STATUS",
        msg: ""
      },
      {
        prefix: "stat/",
        postfix: "/STATUS8",
        msg: ""
      }
    ]
  };
  private user: UserDTO = {id: null, username: "", email: "", token: "", role: ""};

  constructor(private deviceService: DeviceService, private fb: FormBuilder, private router: Router, private websocket: WebSocketService, private sensorService: SensorService, private authService: AuthService) {
    this.deviceForm = this.fb.group({
      deviceName: ['', [Validators.required]],
      deviceType: ['', [Validators.required]],
      location: ['', [Validators.required]],
      topic: ['', [Validators.required]]
    })

    effect(() => {
      this.user = this.authService.currentUser();
      console.log(this.authService.currentUser().id)
    })
  }

  jsonMessage: any;

  ngOnInit(): void {
    const token = localStorage.getItem("token");
    if (token != null) {
      this.authService.getUserByToken("Bearer: " +token);
    }

    this.websocket.subscribeToMessages().subscribe(this.webSocModel.listen, (message) =>  {
      console.log(message.body);
      this.detectedDevice = JSON.parse(message.body);
      console.log(this.detectedDevice);
      this.deviceForm.get("deviceName")?.setValue(this.detectedDevice?.deviceName);
      // @ts-ignore
      for (const val of this.detectedDevice?.statusSNS) {
        if (val != null) {

          if (this.detectedDevice?.statusSNS?.includes('PWM')) {
            this.sensorService.getByName("PWM" + this.detectedDevice?.statusSNS?.length + 1).subscribe(s => {
              console.log(s);
              this.detectedSensors?.push(s);
            });
          }
          if (!this.detectedDevice?.statusSNS?.includes('PWM')) {
            this.sensorService.getByName(val).subscribe(s => {
              console.log(s);
              this.detectedSensors?.push(s);
            });
          }
        }
      }
    });

  }

  autoDetect() {
    console.log()
    this.webSocModel.topic = this.deviceForm.get("topic")?.value;
    this.websocket.send(this.webSocModel);
    // this.detectedDevice = JSON.parse("{\"deviceName\":\"Probalkozom\",\"gpio\":[{\"gpio\":\"GPIO10\",\"number\":\"1184\",\"name\":\"DHT11\"},{\"gpio\":\"GPIO11\",\"number\":\"100\",\"name\":\"PWM\"}]}");

  }

  saveDevice() {
    let sensorId: number[] = []
    /*this.detectedSensors.forEach(s => {
      sensorId.push(s.sensorId);
    })*/
    let device: DeviceDTO = {
      devicesId: null,
      deviceName: this.deviceForm.get("deviceName")?.value,
      sensors: this.detectedSensors,
      measurements: [],
      location: this.deviceForm.get("location")?.value,
      topic: this.deviceForm.get("topic")?.value,
      active: 1
    }
    console.log(device);
    // console.log(this.user.id);
    if (this.user.id != undefined){
      this.deviceService.saveDevice(device, this.user.id);
    }
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
    // protected readonly onfocus = onfocus;
  protected readonly Object = Object;


  change(type: string) {
    switch (type) {
      case 'temperature':
        this.temperature = !this.temperature;
        break;
      case 'rgb':
        this.rgb = !this.rgb;
        break;
      case 'switch':
        this.switch = !this.switch;
        break;
    }
  }

  protected readonly Array = Array;
}
