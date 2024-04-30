import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {FormControl, FormGroup} from "@angular/forms";
import {FieldService} from "../../../shared/service/field.service";
import {FieldDOT} from "../../../shared/model/dto/FieldDTO";
import {IfthenService} from "../../../shared/service/ifthen.service";
import {IfThen} from "../../../shared/model/IfThen";
import {DeviceDTO2} from "../../../shared/model/dto/DeviceDTO2";
import {IfThenDTO} from "../../../shared/model/dto/IfThenDTO";

@Component({
  selector: 'app-if-then',
  templateUrl: './if-then.component.html',
  styleUrls: ['./if-then.component.scss']
})
export class IfThenComponent implements OnInit {
  @Input() mestList: Array<Array<MeasurementDTO>> = [];
  ifThenFormGroup: FormGroup = new FormGroup({});
  devicesByOutputField: FieldDOT[] = [];
  // @ts-ignore
  ifThenDTO: IfThenDTO;

  ifInput: any;
  after: any;
  // @ts-ignore
  ifDevice: DeviceDTO2 = JSON.parse(localStorage.getItem("selectedDevice"));
  thenDevice: FieldDOT = {
    fieldId: 0,
    deviceId: 0,
    deviceName: "",
    fieldKey: "",
    fieldType: ""
  };
  thenOutput: any;
  messageFrom = new FormGroup({
    message: new FormControl()
  });


  constructor(private fieldServ: FieldService, private ifThenServ: IfthenService) {
  }

  ngOnInit(): void {
    this.fieldServ.getDevicesFieldsByOutput().subscribe((fieldDOT: FieldDOT[]) => {
      this.devicesByOutputField = fieldDOT;
      console.log(fieldDOT);
    }, error => {
      console.error(error);
    });
    this.mestList.forEach((item: any) => {
      if(item[0].fieldType == '1'){
        this.ifThenFormGroup.addControl(item[0].payloadKey+"If", new FormControl());
        this.ifThenFormGroup.addControl(item[0].payloadKey+"Then", new FormControl());
      }
    });
    this.ifThenServ.getIfThen(this.ifDevice.devicesId).subscribe((i: IfThenDTO) =>{
      this.ifThenDTO = i;
      console.log(this.ifThenDTO);
    },error => {
      console.error(error);
    });
    console.log(this.ifThenFormGroup);

  }

  onClick(){
    console.log(this.ifThenFormGroup.value);
  }

  selectOutputField(arr: Array<Array<MeasurementDTO>>){
    let res: MeasurementDTO[] = [];
    for(let key of arr){
      if(key[0].fieldType == '1'){
        res.push(key[0]);
      }
    }
    return res;
  }

  saveIfThen(){
    let ifthen: IfThen = {
      ifThenId: 0,
      ifDeviceId: this.ifDevice.devicesId,
      thenDeviceId: this.thenDevice.deviceId,
      ifFieldId: this.ifInput.fieldId,
      thenFieldId: this.thenOutput,
      after: this.after,
      message: this.messageFrom.get("message")?.value
    };
    console.log(ifthen);
    // @ts-ignore
    this.ifThenServ.saveIfThen(ifthen).subscribe(() =>{
      console.log("IfThen saved")
    }, error =>{
      console.error(error);
    });
  }


  protected readonly undefined = undefined;
}
