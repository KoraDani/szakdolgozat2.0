import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {WebSocketModel} from "./WebSocketModel";

@Injectable({
  providedIn: 'root'
})
export class MqttService {
  private apiUrl = "http://localhost:8080/mqttMessaging";
  constructor(private http: HttpClient) { }

  sendMessageToDevice(webSocModel: WebSocketModel){
    return this.http.post<boolean>(this.apiUrl+"/sendMessage", webSocModel);
  }
}
