import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthenticationGuard} from "./authentication.guard";


const routes: Routes = [
  { path: 'login', loadChildren: () => import('./pages/login/login.module').then(m => m.LoginModule)},
  {path: 'register', loadChildren: () => import('./pages/register/register.module').then(m => m.RegisterModule)},
  {path: 'main', loadChildren: () => import('./pages/main/main.module').then(m => m.MainModule)},
  {path: 'devices', loadChildren: () => import('./pages/devices/devices.module').then(m => m.DevicesModule), /*canActivate: [AuthenticationGuard]*/},
  {path: 'user-data', loadChildren: () => import('./pages/user-data/user-data.module').then(m => m.UserDataModule), /*canActivate: [AuthenticationGuard]*/},
  { path: '', redirectTo:'/login', pathMatch:'full'},
  { path: '**', redirectTo:'/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }