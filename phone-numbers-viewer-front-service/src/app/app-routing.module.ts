import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CustomersListingComponent} from './customers-listing/customers-listing.component';

export const routes: Routes = [
  {
    path: 'customers',
    component: CustomersListingComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
