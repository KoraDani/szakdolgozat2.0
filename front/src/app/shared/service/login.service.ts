import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Users} from "../model/Users";
import {UserDTO} from "../model/UserDTO";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { }


  loginUser(userDTO: UserDTO) {
    return this.http.post<Users>(this.apiUrl+"/auth/login", userDTO);
  }
}
