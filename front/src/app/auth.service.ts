import {Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Users} from "./shared/model/Users";
import {UserDTO} from "./shared/model/dto/UserDTO";
import {ResponseDTO} from "./shared/model/dto/ResponseDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';
  currentUser = signal<UserDTO>({id: null, username: "", email: "", token: "", role: ""});

  constructor(private http: HttpClient) {
  }

  loginUser(user: Users) {
    console.log(user);
    return this.http.post<UserDTO>(this.apiUrl + "/auth/login", user).subscribe(user => {
      this.currentUser.set(user);
      localStorage.setItem("token", user.token);
    }, error => {
      console.error(error);
    });
  }

  getUserByToken() {
    const token = localStorage.getItem("token");
    if(token) {
      const header = new HttpHeaders({"Authorization":"Bearer " + token});

      this.http.post<UserDTO>(this.apiUrl + "/auth/currentUser", null, {headers: header}).subscribe(user => {
        console.log(user)
        this.currentUser.set(user);
      }, error => {
        console.error(error);
      });
    }
  }

  saveUser(user: Users) {
    return this.http.post<UserDTO>(this.apiUrl + "/auth/saveUser", user).subscribe(user => {
      localStorage.setItem("token", user.token);
    }, error => {
      if (error.status === 400) {
        console.log("Something is not right");
      }
    });
  }

  changePassword(username: string, oldpwd: string, newpwd1: string, newpwd2: string) {
    return this.http.post<Users>(this.apiUrl + "/auth/changePassword", null, {
      params: {
        username,
        oldpwd,
        newpwd1,
        newpwd2
      }
    })
  }

  logout() {
    localStorage.removeItem("token");
    return this.http.get(this.apiUrl + "logout");
  }
}
