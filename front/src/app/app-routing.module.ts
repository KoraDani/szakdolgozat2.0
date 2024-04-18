import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  {path: 'login', loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule)},
  {path: 'register', loadChildren: () => import('./pages/register/register.module').then(m => m.RegisterModule)},
  {path: 'main', loadChildren: () => import('./pages/main/main.module').then(m => m.MainModule)},
  {path: 'devices', loadChildren: () => import('./pages/devices/devices.module').then(m => m.DevicesModule)/*, canActivate: [AuthenticationGuard]*/},
  {path: 'create-device', loadChildren: () => import('./pages/devices/create-device/create-device.module').then(m => m.CreateDeviceModule)/*, canActivate: [AuthenticationGuard]*/},
  {path: 'view', loadChildren: () => import('./pages/devices/view/view.module').then(m => m.ViewModule)/*, canActivate: [AuthenticationGuard]*/},
  {path: 'user-data', loadChildren: () => import('./pages/user-data/user-data.module').then(m => m.UserDataModule)/*, canActivate: [AuthenticationGuard]*/},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
