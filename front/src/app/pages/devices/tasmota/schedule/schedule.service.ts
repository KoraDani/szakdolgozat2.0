import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ScheduleTaskModel} from "./ScheduleTaskModel";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  url: string = 'http://localhost:8080/scheduleTask';

  constructor(private http: HttpClient) { }

  saveSchedule(schedule: ScheduleTaskModel) {
    this.http.post(this.url+"/saveSchedule", schedule).subscribe(schedule => {
      console.log(schedule);
    })
  }
}
