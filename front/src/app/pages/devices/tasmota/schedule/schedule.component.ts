import {Component, Input, OnInit} from '@angular/core';
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

@Component({
  selector: 'app-schedule',
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
  templateUrl: './schedule.component.html',
  styleUrl: './schedule.component.scss'
})
export class ScheduleComponent implements OnInit {
  weeklyOptions: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']


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

  frequency: string = "";
  selectedCommand: string = "";

  @Input() selectedDevice?: DeviceDTO;
  weekPicker: string = "";
  monthDayPicker: any;
  timePicker: any = "00:00";


  constructor(private scheduleService: ScheduleService) {
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
        id: null,
        scheduledTime: this.scheduleFormat(this.frequency),
        dailySchedule: this.frequency == 'daily',
        weeklySchedule: this.frequency == 'weekly',
        monthlySchedule: this.frequency == "monthly",
        active: true,
        device: this.selectedDevice,
        command: {
          id: -1,
          command: this.selectedCommand,
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
    this.weekPicker = "";
    this.monthDayPicker = "";
    this.timePicker = "00:00";
  }
}
