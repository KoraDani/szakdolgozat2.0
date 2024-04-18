import { NgModule } from '@angular/core';
import { CommonModule} from "@angular/common";

import { DevicesRoutingModule } from './devices-routing.module';
import { CreateDeviceComponent } from './create-device/create-device.component';
import {CdkDrag} from "@angular/cdk/drag-drop";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import { ViewComponent } from './view/view.component';
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatGridListModule} from "@angular/material/grid-list";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatStepperModule} from "@angular/material/stepper";
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatCardModule} from "@angular/material/card";
import {MatRadioModule} from "@angular/material/radio";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { IfThenComponent } from './if-then/if-then.component';


@NgModule({
    declarations: [
        CreateDeviceComponent,
        ViewComponent,
        IfThenComponent

    ],
  exports: [
    ViewComponent
  ],
  imports: [
    DevicesRoutingModule,
    CommonModule,
    CdkDrag,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatSidenavModule,
    MatGridListModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatStepperModule,
    CanvasJSAngularChartsModule,
    MatSlideToggleModule,
    MatCardModule,
    MatRadioModule,
    MatCheckboxModule
  ]
})
export class DevicesModule { }
