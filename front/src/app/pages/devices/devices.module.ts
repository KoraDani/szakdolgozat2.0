import { NgModule } from '@angular/core';
import { CommonModule} from "@angular/common";

import { DevicesRoutingModule } from './devices-routing.module';
import { CreateDeviceComponent } from './create-device/create-device.component';
import {CdkDrag} from "@angular/cdk/drag-drop";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";


@NgModule({
  declarations: [
    CreateDeviceComponent
  ],
    imports: [
        DevicesRoutingModule,
        CommonModule,
        CdkDrag,
        ReactiveFormsModule,
        FormsModule,
        MatIconModule
    ]
})
export class DevicesModule { }
