import {GPIO} from "./GPIO";

export class DetectedDevice{
  deviceName?: string;
  // jsonMap?: Map<any,any>;
  gpio?: GPIO[];
  statusSNS?: string[];
}
