import {Sensor} from "./Sensor";
import {Measurement} from "./Measurement";

export interface Devices{
  devicesId: number | undefined;
  deviceName: string;
  location: string;
  userId: number | undefined;
  measurementList: Measurement
  sensor: Sensor,
  topic: string;
  active: number
}
