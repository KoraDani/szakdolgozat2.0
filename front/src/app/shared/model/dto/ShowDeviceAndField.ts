export interface ShowDeviceAndField{
  devicesId: number;
  deviceName: string;
  deviceType: string;
  location: string;
  fieldKey: string[];
  fieldType: number[];
  measurment: number[]; //Ez lehet any-nek kellene lennie
}
