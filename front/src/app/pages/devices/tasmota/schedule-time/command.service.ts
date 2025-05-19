import {Injectable, signal} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TasmotaCommand} from "./TasmotaCommand";

@Injectable({
  providedIn: 'root'
})
export class CommandService {
  private apiUrl = "http://localhost:8080/tasmota";

  tasmotaCommand = signal<TasmotaCommand[]>([]);

  constructor(private http: HttpClient) { }

  public getAllTasmotaCommand(){
    this.http.get<TasmotaCommand[]>(`${this.apiUrl}/command`).subscribe(commands => {
      this.tasmotaCommand.set(commands);
    })
  }
}
