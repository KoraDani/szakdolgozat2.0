import {Component, effect, Input, OnInit} from '@angular/core';
import {ScheduleService} from "../schedule-time/schedule.service";
import {DeviceService} from "../../device.service";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {CommandService} from "../schedule-time/command.service";
import {TasmotaCommand} from "../schedule-time/TasmotaCommand";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {FormsModule} from "@angular/forms";
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
  ],
  templateUrl: './schedule-device.component.html',
  styleUrl: './schedule-device.component.scss'
})
export class ScheduleDeviceComponent implements OnInit {

  @Input() selectedDevice?: DeviceDTO;

  scheduleId: number = -1;
  userDevices: DeviceDTO[] = [];
  tasmotaCommands: TasmotaCommand[] = [];
  selectedTrigger: string = "";
  targetDeviceId: number = -1;
  operation: string | null = null;
  triggerValue: number | null = null;
  selectedCommand: string = "";
  whichValue: string | null = null;

  scheduleTask?: ScheduleTaskModel[] = [];




  constructor(private scheduleService: ScheduleService, private commandService: CommandService, private deviceService: DeviceService) {

    effect(() => {
      this.userDevices = this.deviceService.devicesDTO();
      console.log(this.userDevices);
      this.tasmotaCommands = this.commandService.tasmotaCommand();
      console.log(this.tasmotaCommands);
      this.scheduleTask = this.scheduleService.scheduleTask();
      console.log(this.scheduleTask);
    });
  }

  ngOnInit(): void {
    if(this.selectedDevice?.devicesId){
      this.scheduleService.deviceId.set(this.selectedDevice.devicesId);
      console.log(this.scheduleService.deviceId())
      this.scheduleService.getAllDeviceSchedule();
    }

    this.commandService.getAllTasmotaCommand();
  }

  onSave(){
    const schedule: ScheduleTaskModel= {
      id: this.scheduleId,
      scheduledTime: "",
      frequency: Frequency.NONE,
      targetDeviceId: this.targetDeviceId,
      whichValue: this.whichValue,
      conditionOperator: this.operation,
      whenCondition: this.triggerValue,
      active: true,
      // @ts-ignore
      device: this.selectedDevice,
      command: {
        id: +this.selectedCommand,
        command: "",
        argument: "",
        description: ""
      }
    }
    console.log(schedule)
    this.scheduleService.saveSchedule(schedule);
  }

  onEdit(st: ScheduleTaskModel) {
    if(st.id && st.whichValue && st.conditionOperator){
      this.scheduleId = st.id;
      this.targetDeviceId = st.targetDeviceId;
      this.whichValue = st.whichValue;
      this.operation = st.conditionOperator;
      this.triggerValue = st.whenCondition;
      this.selectedDevice = st.device;
      this.selectedCommand = st.command.id+"";
    }

  }

  onDelete(st: ScheduleTaskModel) {
    this.scheduleService.deleteSchedule(st);
  }
}
