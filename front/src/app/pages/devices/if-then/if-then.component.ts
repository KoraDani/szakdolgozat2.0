import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {FormControl, FormGroup} from "@angular/forms";
import {SensorService} from "../sensor.service";
import {FieldDOT} from "../../../shared/model/dto/FieldDTO";
import {IfthenService} from "./ifthen.service";
import {IfThen} from "../../../shared/model/IfThen";
import {DeviceDTO2} from "../../../shared/model/dto/DeviceDTO2";
import {IfThenDTO} from "../../../shared/model/dto/IfThenDTO";

@Component({
    selector: 'app-if-then',
    templateUrl: './if-then.component.html',
    styleUrls: ['./if-then.component.scss'],
    standalone: false
})
export class IfThenComponent  {
  ifThenDTO?: IfThenDTO;
  ifInput: any;

  selectOutputField(mestList: any) {

  }
}
