import {TasmotaCommand} from "./TasmotaCommand";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";


export interface ScheduleTaskModel {
  id: number | null;
  scheduledTime: string;
  dailySchedule: boolean;
  weeklySchedule: boolean;
  monthlySchedule: boolean;
  active: boolean;
  device: DeviceDTO;
  command: TasmotaCommand;
}
