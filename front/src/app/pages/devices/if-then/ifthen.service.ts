import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {IfThen} from "../../../shared/model/IfThen";
import {IfThenDTO} from "../../../shared/model/dto/IfThenDTO";

@Injectable({
  providedIn: 'root'
})
export class IfthenService {
  private apiUrl = "http://localhost:8080/ifThen";

  constructor(private http:HttpClient) { }

  saveIfThen(ifthen: IfThen){
    return this.http.post<IfThen>(this.apiUrl+"/saveIfThen", ifthen);
  }

  getIfThen(deviceId: number) {
    return this.http.post<IfThenDTO[]>(this.apiUrl+"/getIfThen",null, {params:{deviceId}})
  }
}
