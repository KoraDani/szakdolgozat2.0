import {Devices} from "../Devices";

export interface MeasurementDTO{
  measurementId: number;
  sensorType: string;
  value: string;
  time: string;
  device: Devices;
}
