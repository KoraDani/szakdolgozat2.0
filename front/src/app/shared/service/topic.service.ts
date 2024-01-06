import { Injectable } from '@angular/core';
import {Measurement} from "../model/Measurement";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  getMeasurment() {
    return this.http.get<Measurement[]>(this.apiUrl+"/topic/getAllUserMeasurment");
  }
}
