import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DevicesComponent} from './pages/devices/devices.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {MainComponent} from './pages/main/main.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {FlexLayoutModule} from "@angular/flex-layout";
import {UserDataComponent} from './pages/user-data/user-data.component';
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldControl, MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatExpansionModule} from "@angular/material/expansion";
import {MenuComponent} from './shared/menu/menu.component';
import {MatListModule} from "@angular/material/list";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {CdkDrag} from "@angular/cdk/drag-drop";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatSelectModule} from "@angular/material/select";
import {MatStepperModule} from "@angular/material/stepper";
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";
import {MatRadioModule} from "@angular/material/radio";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {CreateDeviceComponent} from "./pages/devices/create-device/create-device.component";
import {ViewComponent} from "./pages/devices/view/view.component";
import {IfThenComponent} from "./pages/devices/if-then/if-then.component";
import { TemperatureComponent } from './pages/devices/tasmota/temperature/temperature.component';
import { RgbComponent } from './pages/devices/tasmota/rgb/rgb.component';
import { SwitchComponent } from './pages/devices/tasmota/switch/switch.component';
import { PlugComponent } from './pages/devices/tasmota/plug/plug.component';
import { LightComponent } from './pages/devices/tasmota/light/light.component';
import {MatSliderModule} from "@angular/material/slider";

@NgModule({ declarations: [
        AppComponent,
        DevicesComponent,
        LoginComponent,
        RegisterComponent,
        MainComponent,
        UserDataComponent,
        MenuComponent,
        CreateDeviceComponent,
        ViewComponent,
        IfThenComponent,
        TemperatureComponent,
        RgbComponent,
        SwitchComponent,
        PlugComponent,
        LightComponent,
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        FlexLayoutModule,
        FormsModule,
        ReactiveFormsModule,
        MatMenuModule,
        MatExpansionModule,
        CdkDrag,
        MatButtonModule,
        MatListModule,
        MatGridListModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatStepperModule,
        CanvasJSAngularChartsModule,
        MatSlideToggleModule,
        MatRadioModule,
        MatCheckboxModule,
        MatSliderModule], providers: [provideHttpClient(withInterceptorsFromDi())] })
export class AppModule {
}
