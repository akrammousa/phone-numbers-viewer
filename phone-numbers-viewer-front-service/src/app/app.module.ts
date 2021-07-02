import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CustomersListingComponent } from './customers-listing/customers-listing.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule, HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {SelectDropDownModule} from 'ngx-select-dropdown';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    CustomersListingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    SelectDropDownModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
