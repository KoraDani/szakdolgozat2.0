import {Injectable, signal} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ScheduleTaskModel} from "./ScheduleTaskModel";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  scheduleTask = signal<ScheduleTaskModel[]>([]);
  deviceId = signal(-1);

  url: string = 'http://localhost:8080/scheduleTask';

  constructor(private http: HttpClient) {
  }

  saveSchedule(schedule: ScheduleTaskModel) {
    this.http.post(this.url+"/saveSchedule", schedule).subscribe(schedule => {
      console.log(schedule);
      this.getAllDeviceSchedule();
    })
  }

  getAllDeviceSchedule() {
    this.http.post<ScheduleTaskModel[]>(this.url+"/getAllDeviceSchedule", null, {params:{deviceId: this.deviceId()}}).subscribe(scheduleTask => {
      this.scheduleTask.set(scheduleTask);
    });
  }

  deleteSchedule(st: ScheduleTaskModel) {
    if(st.id){
      const id: number = st.id;
      this.http.delete<ScheduleTaskModel>(this.url+"/delteSchedule?id="+st.id).subscribe(() => {
        this.getAllDeviceSchedule();
      })
    }
  }
}


