import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Users} from "../model/Users";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiServerUrl = '';
  constructor(private http: HttpClient) {
  }
  public login(users: Users){
    return this.http.post<any>(this.apiServerUrl+"/auth/login", users);
  }

}
