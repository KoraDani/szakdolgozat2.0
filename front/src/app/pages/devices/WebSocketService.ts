import {Injectable, OnDestroy} from "@angular/core";
import {CompatClient, Stomp, StompSubscription} from "@stomp/stompjs";
import {WebSocketModel} from "./WebSocketModel";

@Injectable({
  providedIn: "root"
})
export class WebSocketService implements OnDestroy {
  private connection: CompatClient | undefined = undefined;

  private subscription: StompSubscription | undefined;

  constructor() {
    this.connection = Stomp.client('ws://localhost:8080/websocket');
    /*if(this.connection.connected){
      this.connection.connect({}, () => {});
    }*/
  }

  public send(webSocModel: WebSocketModel): void {
    if (this.connection && this.connection.connected) {
      this.connection.send(webSocModel.destination, {}, JSON.stringify(webSocModel));
    }
  }

  public listen(destination: string, fun: (message: string) => void): void {
    if (this.connection) {
      this.connection.connect({}, () => {
        this.subscription = this.connection!.subscribe(destination, message => fun(message.body));
      });
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
