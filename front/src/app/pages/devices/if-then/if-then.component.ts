import {Component, Input, OnInit} from '@angular/core';
import {MeasurementDTO} from "../../../shared/model/dto/MeasurementDTO";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SensorService} from "../sensor.service";
import {FieldDOT} from "../../../shared/model/dto/FieldDTO";
import {IfthenService} from "./ifthen.service";
import {IfThen} from "../../../shared/model/IfThen";
import {IfThenDTO} from "../../../shared/model/dto/IfThenDTO";
import {MatCard, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {MatLabel, MatFormField} from '@angular/material/form-field';
import {MatSelect, MatOption} from '@angular/material/select';
import {MatRadioGroup, MatRadioButton} from '@angular/material/radio';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-if-then',
  standalone: true,

  templateUrl: './if-then.component.html',
  styleUrls: ['./if-then.component.scss'],
  imports: [ MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatLabel, MatFormField, MatSelect, MatOption, MatRadioGroup, MatRadioButton, FormsModule, MatInput, ReactiveFormsModule, MatButton]
})
export class IfThenComponent {
  ifThenDTO?: IfThenDTO;
  ifInput: any;

  selectOutputField(mestList: any) {

  }
}
