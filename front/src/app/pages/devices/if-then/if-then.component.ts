import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {FormControl, FormGroup} from "@angular/forms";
import {FieldService} from "../../../shared/service/field.service";
import {FieldDOT} from "../../../shared/model/dto/FieldDTO";

@Component({
  selector: 'app-if-then',
  templateUrl: './if-then.component.html',
  styleUrls: ['./if-then.component.scss']
})
export class IfThenComponent implements OnInit {
  @Input() mestList: Array<Array<MeasurementDTO>> = [];
  ifThenFormGroup: FormGroup = new FormGroup({});
  devicesByOutputField: FieldDOT[] = [];


  constructor(private fieldServ: FieldService) {
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


}
