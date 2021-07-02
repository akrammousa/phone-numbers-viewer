import { Component, OnInit } from '@angular/core';
import {CustomersService} from '../services/customers.service';
import {$e} from 'codelyzer/angular/styles/chars';

@Component({
  selector: 'app-customers-listing',
  templateUrl: './customers-listing.component.html',
  styleUrls: ['./customers-listing.component.css']
})
export class CustomersListingComponent implements OnInit {
  customers: any;
  statuses: any;
  selectedStatus: any;
  countries: any;
  selectedCountryName: any;


  constructor(private customersService: CustomersService) { }

  ngOnInit(): void {
    this.selectedCountryName = '';
    this.statuses = [
      true,
      false
    ];
    this.fetchAllCustomers();
  }

  private fetchAllCustomers() {
    this.customersService.fetchAllCustomers().subscribe(res => {
      this.customers = res;
    }, error => {
      console.log(error);
    });
  }

  changeStatus($event) {
    if ($event.value.length === 0) { this.fetchAllCustomers(); return; }
    this.customersService.getByStatus({status: $event.value[0]}).subscribe(res => {
      this.customers = res;
    }, error => {
      console.log(error);
    });
  }

  changeCountryName() {
    if (this.selectedCountryName !== '') {
      this.customersService.getByCountryName({name: this.selectedCountryName}).subscribe(res => {
        this.customers = res;
      }, error => {
        this.customers = [];
      });
    }else{
      this.fetchAllCustomers();
    }
  }
}
