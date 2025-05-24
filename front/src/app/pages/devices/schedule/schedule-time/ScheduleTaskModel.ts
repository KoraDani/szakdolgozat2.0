import {TasmotaCommand} from "./TasmotaCommand";
import {DeviceDTO} from "../../../../shared/model/dto/DeviceDTO";
import {Frequency} from "./frequency.enum";


export interface ScheduleTaskModel {
  id: number | null;
  scheduledTime: string;
  frequency: Frequency;
  targetDeviceId: number;
  whichValue: string | null;
  conditionOperator: string | null;
  whenCondition: number | null;
  active: boolean;
  device: DeviceDTO;
  command: TasmotaCommand;
}
