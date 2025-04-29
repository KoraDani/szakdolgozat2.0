import {Injectable, OnDestroy} from "@angular/core";
import { Client } from "@stomp/stompjs";
import {WebSocketModel} from "./WebSocketModel";

@Injectable({
  providedIn: "root"
})
export class WebSocketService implements OnDestroy {
  private readonly stompClient: Client;

  constructor() {
    this.stompClient = new Client({brokerURL:'ws://localhost:8080/websocket', reconnectDelay: 5000});
    // this.connection = Stomp.client('ws://localhost:8080/websocket');
    // this.connect();
    if(!this.stompClient.active){
      this.stompClient.onConnect = () => {
        console.log("Connected to Websocket server");
      }

      this.stompClient.activate();
    }

  }


  public subscribeToMessages(): Client {
    return this.stompClient;
  }

  public send(webSocModel: WebSocketModel): void {
    this.stompClient.publish({destination: webSocModel.destination, body: JSON.stringify(webSocModel)});
  }

  disconnect(): void {
    this.stompClient.deactivate();
  }

  ngOnDestroy(): void {

  }
}
