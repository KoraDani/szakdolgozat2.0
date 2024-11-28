import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Users} from "./shared/model/Users";
import {RegisterDTO} from "./shared/model/dto/RegisterDTO";
import {UserDTO} from "./shared/model/dto/UserDTO";
import {ResponseDTO} from "./shared/model/dto/ResponseDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';
  constructor(private http: HttpClient) {
  }
  loginUser(userDTO: UserDTO) {
    return this.http.post<ResponseDTO>(this.apiUrl+"/auth/login", userDTO);
  }
  getUserById(username: string){
    return this.http.post<Users>(this.apiUrl+"/auth/getUserByUsername", username);
  }

  saveUser(user: RegisterDTO) {
    return this.http.post<Users>(this.apiUrl + "/auth/saveUser", user);
  }

  changePassword(username:string,oldpwd:string, newpwd1:string, newpwd2:string) {
    return this.http.post<Users>(this.apiUrl+"/auth/changePassword", null,{params:{username, oldpwd, newpwd1,newpwd2}})
  }

  logout(){
    return this.http.get(this.apiUrl+"logout");
  }
}
