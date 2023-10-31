import {Injectable} from '@angular/core';
import {Users} from "../model/Users";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient) {
  }

  saveUser(user: Users) {
    return this.http.post<Users>(this.apiUrl + "/register/saveUser", user);
  }
}
