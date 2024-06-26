import {Measurement} from "../Measurement";

export interface DeviceDTO{
  devicesId: number | 0;
  deviceName: string | "";
  deviceType: string | "";
  location: string | "";
  topic: string | "";
  active: number | "";
  fieldKey: string[];
  fieldType: number[];
  payloadKey: string[];
  payloadValue: string[];
  status: boolean;
  // measurement: Measurement | undefined;
}
