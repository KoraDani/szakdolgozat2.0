import {Component, effect, Input, OnInit} from '@angular/core';
import {ScheduleService} from "../schedule-time/schedule.service";
import {DeviceService} from "../../device.service";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {CommandService} from "../schedule-time/command.service";
import {TasmotaCommand} from "../schedule-time/TasmotaCommand";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {ScheduleTaskModel} from "../schedule-time/ScheduleTaskModel";
import {MatCardModule} from "@angular/material/card";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {Frequency} from "../schedule-time/frequency.enum";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

@Component({
  selector: 'app-schedule-device',
  imports: [
    MatFormFieldModule,
    MatLabel,
    MatSelectModule,
    MatRadioGroup,
    MatRadioButton,
    FormsModule,
    MatInput,
    MatButton,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './schedule-device.component.html',
  styleUrl: './schedule-device.component.scss'
})
export class ScheduleDeviceComponent implements OnInit {

  @Input() selectedDevice?: DeviceDTO;

  scheduleId: number | null = -1;
  userDevices: DeviceDTO[] = [];
  tasmotaCommands: TasmotaCommand[] = [];

  scheduleDeviceForm: FormGroup = new FormGroup({
    targetDeviceId: new FormControl(0, [Validators.required]),
    whichValue: new FormControl("", [Validators.required]),
    operation: new FormControl("", [Validators.required]),
    triggerValue: new FormControl(0, [Validators.required]),
    selectedCommand: new FormControl("", [Validators.required])
  });


  scheduleTask?: ScheduleTaskModel[] = [];


  constructor(private scheduleService: ScheduleService, private commandService: CommandService, private deviceService: DeviceService) {

    effect(() => {
      this.userDevices = this.deviceService.devicesDTO();
      console.log(this.userDevices);
      this.tasmotaCommands = this.commandService.tasmotaCommand();
      console.log(this.tasmotaCommands);
      if(this.scheduleService.selectedScheduleTask() && this.scheduleService.selectedScheduleTask()?.frequency == "NONE") {
        this.onEdit(this.scheduleService.selectedScheduleTask());
      }
    });
  }

  ngOnInit(): void {
    if (this.selectedDevice?.devicesId) {
      this.scheduleService.deviceId.set(this.selectedDevice.devicesId);
      console.log(this.scheduleService.deviceId())
      this.scheduleService.getAllDeviceSchedule();
    }

    this.commandService.getAllTasmotaCommand();
  }

  onSave() {
    console.log(this.selectedDevice);
    if (this.scheduleDeviceForm.valid) {
      const schedule: ScheduleTaskModel = {
        id: this.scheduleId,
        scheduledTime: "",
        frequency: Frequency.NONE,
        targetDeviceId: this.scheduleDeviceForm.get("targetDeviceId")?.value,
        whichValue: this.scheduleDeviceForm.get("whichValue")?.value,
        conditionOperator: this.scheduleDeviceForm.get("operation")?.value,
        whenCondition: this.scheduleDeviceForm.get("triggerValue")?.value,
        active: true,
        // @ts-ignore
        device: this.selectedDevice,
        command: {
          id: +this.scheduleDeviceForm.get("targetDeviceId")?.value,
          command: "",
          argument: "",
          description: ""
        }
      }
      console.log("save " + schedule.device)
      this.scheduleService.saveSchedule(schedule);
    }

  }

  onEdit(st: ScheduleTaskModel | null) {
    if (st != null) {
      this.scheduleId = st?.id;
      this.scheduleDeviceForm.get("targetDeviceId")?.setValue(st?.targetDeviceId)
      this.scheduleDeviceForm.get("whichValue")?.setValue(st?.whichValue)
      this.scheduleDeviceForm.get("operation")?.setValue(st?.conditionOperator)
      this.scheduleDeviceForm.get("triggerValue")?.setValue(st?.whenCondition)
      this.scheduleDeviceForm.get("selectedCommand")?.setValue(+st?.command.id)
    }
    console.log(this.selectedDevice)
  }

  onDelete(st: ScheduleTaskModel) {
    this.scheduleService.deleteSchedule(st);
  }
}
