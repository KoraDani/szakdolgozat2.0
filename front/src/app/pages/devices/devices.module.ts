import { NgModule } from '@angular/core';
import { CommonModule} from "@angular/common";

import { DevicesRoutingModule } from './devices-routing.module';
import { CreateDeviceComponent } from './create-device/create-device.component';
import {CdkDrag} from "@angular/cdk/drag-drop";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    CreateDeviceComponent
  ],
  imports: [
    DevicesRoutingModule,
    CommonModule,
    CdkDrag,
    ReactiveFormsModule
  ]
})
export class DevicesModule { }
