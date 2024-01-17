import {Measurement} from "../Measurement";

export interface DeviceDTO{
  devicesId: number | undefined;
  deviceName: string;
  deviceType: string;
  location: string;
  topic: string;
  fieldKey: string[];
  fieldType: number[];
  payloadValue: string[];
  // measurement: Measurement | undefined;
}
