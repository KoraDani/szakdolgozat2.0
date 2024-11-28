export interface WebSocketModel{
  destination: string;
  listen: string;
  topic: string;
  message: {prefix: string, postfix: string, msg: string}[];
}
