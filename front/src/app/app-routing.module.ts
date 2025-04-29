import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {MainComponent} from "./pages/main/main.component";
import {DevicesComponent} from "./pages/devices/devices.component";
import {CreateDeviceComponent} from "./pages/devices/create-device/create-device.component";
import {ViewComponent} from "./pages/devices/view/view.component";
import {UserDataComponent} from "./pages/user-data/user-data.component";
import {LightComponent} from "./pages/devices/tasmota/light/light.component";


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'main',
    component: MainComponent
  },
  {
    path: 'devices',
    component: DevicesComponent
  },
  {
    path: 'create-device',
    component: CreateDeviceComponent
  },
  {
    path: 'view',
    component: ViewComponent
  },
  {
    path: 'user-data',
    component: UserDataComponent
  },
  {
    path: 'light',
    component: LightComponent,
  },
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
