import {Component, effect, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatTimepickerOption} from "@angular/material/timepicker";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from '@angular/material/button';
import {provideNativeDateAdapter} from '@angular/material/core';
import {NgxMatTimepickerFieldComponent} from "ngx-mat-timepicker";
import {ScheduleService} from "./schedule.service";
import {ScheduleTaskModel} from "./ScheduleTaskModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {DeviceService} from "../../device.service";
import {Frequency} from "./frequency.enum";
import {TasmotaCommand} from "./TasmotaCommand";
import {CommandService} from "./command.service";

@Component({
  selector: 'app-schedule-time',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    MatFormField,
    MatLabel,
    MatOption,
    MatRadioButton,
    MatRadioGroup,
    MatSelect,
    FormsModule,
    MatButtonModule,
    NgxMatTimepickerFieldComponent
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './schedule-time.component.html',
  styleUrl: './schedule-time.component.scss'
})
export class ScheduleTimeComponent implements OnInit {
  weeklyOptions: {key: number, value: string}[] = [
    {key: 1, value:'Monday'},
    {key: 2, value:'Tuesday'},
    {key: 3, value:'Wednesday'},
    {key: 4, value:'Thursday'},
    {key: 5, value:'Friday'},
    {key: 6, value:'Saturday'},
    {key: 7, value:'Sunday'}];


  monthlyOptions: MatTimepickerOption<string>[] = [
    {label: 'January', value: "January"},
    {label: 'February', value: "February"},
    {label: 'March', value: "March"},
    {label: 'April', value: "April"},
    {label: 'May', value: "May"},
    {label: 'June', value: "June"},
    {label: 'July', value: "July"},
    {label: 'August', value: "August"},
    {label: 'September', value: "September"},
    {label: 'October', value: "October"},
    {label: 'November', value: "November"},
    {label: 'December', value: "December"},
  ];

  frequency!: Frequency;
  selectedCommand: number = -1;

  @Input() selectedDevice?: DeviceDTO;
  weekPicker: number = -1;
  monthDayPicker: number = -1;
  timePicker: any = "00:00";

  constructor(private scheduleService: ScheduleService, private deviceService: DeviceService) {
  }

  ngOnInit(): void {
    console.log(this.selectedDevice)
  }

  onSave() {
    console.log(this.weekPicker);
    console.log(this.monthDayPicker);
    console.log(typeof this.timePicker);
    console.log(this.frequency);
    console.log(this.selectedDevice);
    if (this.selectedDevice) {
      const schedule: ScheduleTaskModel = {
        id: -1,
        scheduledTime: this.timePicker,
        frequency: this.frequency,
        targetDeviceId: -1,
        whichValue: "",
        conditionOperator: "",
        whenCondition: null,
        active: true,
        device: this.selectedDevice,
        command: {
          id: this.selectedCommand,
          command: "",
          argument: "",
          description: ""
        }
      }
      console.log(schedule);
      this.scheduleService.saveSchedule(schedule);
    }

  }

  scheduleFormat(frequency: string) {
    switch (frequency) {
      case "daily":
        return this.timePicker;
      case "weekly":
        return this.weekPicker+";"+this.timePicker;
      case "monthly":
        return this.monthDayPicker+";"+this.timePicker;
    }
  }

  onChange() {
    this.weekPicker = -1;
    this.monthDayPicker = -1;
    this.timePicker = "00:00";
  }

  protected readonly Frequency = Frequency;
  protected readonly Object = Object;
}
