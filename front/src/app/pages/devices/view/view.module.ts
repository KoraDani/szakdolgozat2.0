import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ViewRoutingModule } from './view-routing.module';
import {Routes} from "@angular/router";
import {ViewComponent} from "./view.component";

const routes: Routes = [{path: '', component: ViewComponent}];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ViewRoutingModule
  ]
})
export class ViewModule { }
