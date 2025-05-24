import {Component, effect, EventEmitter, Input, Output} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {MatIcon} from "@angular/material/icon";
import {MatMiniFabButton} from "@angular/material/button";
import {ScheduleService} from "../schedule-time/schedule.service";
import {ScheduleTaskModel} from "../schedule-time/ScheduleTaskModel";

@Component({
  selector: 'app-schedule-table',
    imports: [
        MatCard,
        MatCardContent,
        MatCardHeader,
        MatCardTitle,
        MatIcon,
        MatMiniFabButton
    ],
  templateUrl: './schedule-table.component.html',
  styleUrl: './schedule-table.component.scss'
})
export class ScheduleTableComponent {

  @Input() selectedDevice?: DeviceDTO;

  scheduleTask: ScheduleTaskModel[] = [];

  constructor(private scheduleService: ScheduleService) {
    effect(() => {
      this.scheduleTask = this.scheduleService.scheduleTask();
      console.log(this.scheduleTask);
    });
  }

  onEdit(st: ScheduleTaskModel) {
    this.scheduleService.selectedScheduleTask.set(st);
  }

  onDelete(st: ScheduleTaskModel) {
    this.scheduleService.selectedScheduleTask.set(st);
  }
}
