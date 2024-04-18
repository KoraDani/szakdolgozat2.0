import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Fields} from "../model/Fields";
import {FieldDOT} from "../model/dto/FieldDTO";

@Injectable({
  providedIn: 'root'
})
export class FieldService {
  private apiUrl = "http://localhost:8080/fields";
  constructor(private http: HttpClient) { }

  saveFields(fields: Fields){
    return this.http.post(this.apiUrl+"", fields);
  }

  getDevicesFieldsByOutput() {
    return this.http.post<FieldDOT[]>(this.apiUrl+"/getDevicesFieldsByOutput", null);
  }
}
