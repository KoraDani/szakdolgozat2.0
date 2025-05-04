import {Sensor} from "../Sensor";
import {Measurement} from "../Measurement";

export interface DeviceDTO{
  devicesId: number | null;
  deviceName: string | "";
  sensors: Sensor[] | [];
  measurements: Measurement[] | [];
  location: string | "";
  topic: string | "";
  active: number | "";
}
