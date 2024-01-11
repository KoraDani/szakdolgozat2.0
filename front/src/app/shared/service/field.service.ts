import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Fields} from "../model/Fields";

@Injectable({
  providedIn: 'root'
})
export class FieldService {
  private apiUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  saveFields(fields: Fields){
    return this.http.post(this.apiUrl+"", fields);
  }
}
