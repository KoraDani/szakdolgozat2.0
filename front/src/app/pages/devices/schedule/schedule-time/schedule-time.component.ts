import {Component, effect, Input, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButtonModule} from '@angular/material/button';
import {provideNativeDateAdapter} from '@angular/material/core';
import {NgxMatTimepickerFieldComponent} from "ngx-mat-timepicker";
import {ScheduleService} from "./schedule.service";
import {ScheduleTaskModel} from "./ScheduleTaskModel";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {DeviceService} from "../../device.service";
import {Frequency} from "./frequency.enum";

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
    NgxMatTimepickerFieldComponent,
    ReactiveFormsModule
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './schedule-time.component.html',
  styleUrl: './schedule-time.component.scss'
})
export class ScheduleTimeComponent implements OnInit {
  weeklyOptions: { key: number, value: string }[] = [
    {key: 1, value: 'Monday'},
    {key: 2, value: 'Tuesday'},
    {key: 3, value: 'Wednesday'},
    {key: 4, value: 'Thursday'},
    {key: 5, value: 'Friday'},
    {key: 6, value: 'Saturday'},
    {key: 7, value: 'Sunday'}];


  @Input() selectedDevice?: DeviceDTO;

  frequency?: Frequency;

  scheduleId: number | null = -1;


  scheduleTimeForm: FormGroup = new FormGroup({
    frequency: new FormControl<Frequency>(Frequency.NONE, [Validators.required]),
    selectedCommand: new FormControl(null, [Validators.required]),
    scheduledTime: new FormControl(null, [Validators.required]),
    weekPicker: new FormControl(null, [Validators.required]),
    monthDayPicker: new FormControl(null, [Validators.required]),
  });

  constructor(private scheduleService: ScheduleService, private deviceService: DeviceService) {
    effect(() => {
      if (this.scheduleService.selectedScheduleTask() && this.scheduleService.selectedScheduleTask()?.frequency != "NONE") {
        this.onEdit(this.scheduleService.selectedScheduleTask());
      }
    });
  }

  ngOnInit(): void {
    console.log(this.selectedDevice)
  }

  onSave() {


    if (this.scheduleTimeForm.valid && this.selectedDevice) {
      const schedule: ScheduleTaskModel = {
        id: this.scheduleId,
        scheduledTime: this.scheduleTimeForm.get("scheduledTime")?.value,
        frequency: this.scheduleTimeForm.get("frequency")?.value,
        targetDeviceId: -1,
        whichValue: "",
        conditionOperator: null,
        whenCondition: this.whichPeriodisSelected(this.scheduleTimeForm.get("frequency")?.value),
        active: true,
        device: this.selectedDevice,
        command: {
          id: this.scheduleTimeForm.get("selectedCommand")?.value,
          command: "",
          argument: "",
          description: ""
        }
      }
      console.log(schedule);
      this.scheduleService.saveSchedule(schedule);
    }

  }

  whichPeriodisSelected(frequency: Frequency) {
    switch (frequency) {
      case Frequency.DAILY:
        return 0;
      case Frequency.WEEKLY:
        return this.scheduleTimeForm.get("weekPicker")?.value;
      case Frequency.MONTHLY:
        return this.scheduleTimeForm.get("monthDayPicker")?.value;
    }
  }


  onChange() {
    this.scheduleTimeForm.get("weekPicker")?.setValue(-1);
    this.scheduleTimeForm.get("monthDayPicker")?.setValue(-1);
    this.scheduleTimeForm.get("scheduledTime")?.setValue("00:00")
  }

  onEdit(st: ScheduleTaskModel | null) {
    if (st != null) {
      this.scheduleId = st?.id;
      this.scheduleTimeForm.get("frequency")?.setValue(st?.frequency);
      this.scheduleTimeForm.get("selectedCommand")?.setValue(st?.command.id);
      this.scheduleTimeForm.get("scheduledTime")?.setValue(st?.scheduledTime)
      this.scheduleTimeForm.get("weekPicker")?.setValue(st?.whenCondition);
      this.scheduleTimeForm.get("monthDayPicker")?.setValue(st?.whenCondition);
    }
  }

  protected readonly Frequency = Frequency;
  protected readonly Object = Object;
}
