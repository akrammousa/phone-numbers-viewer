import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  private url =  'http://localhost:8080/api/customers/';

  constructor(private http: HttpClient) { }

  // tslint:disable-next-line:typedef
  public fetchAllCustomers() {
    return this.http.get(this.url);
  }

  public getByStatus(status) {
    return this.http.get(this.url + 'status', {
      params: status
    });
  }

  public getByCountryName(name) {
    return this.http.get(this.url + 'country-name', {
      params: name
    });
  }
}
