import {Sensor} from "../Sensor";
import {Measurement} from "../Measurement";

export interface DeviceDTO{
  deviceId: number | null;
  deviceName: string | "";
  sensors: Sensor[] | [];
  measurements: Measurement[] | [];
  location: string | "";
  topic: string | "";
  active: number | "";
}
