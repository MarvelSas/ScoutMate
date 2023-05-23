import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {CustomResponse} from "../models/CustomResponse";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Order} from "../models/Order";



@Injectable({
  providedIn: 'root'
})
export class OrderService {



  constructor(private http: HttpClient) { }

  postViewOrder(organizationId : number, order:Order): Observable<any> {
    const params = new HttpParams().append('organizationId', organizationId);

    return this.http.post(
      `http://localhost:8080/api/v1/Order/view/pdf/generate`,
      order,
      {params : params,observe:'response',responseType:'blob'});
  }
  createOrder (organizationId : number, order:Order): Observable<CustomResponse>{
    const params = new HttpParams().append('organizationId', organizationId);
    return this.http.post<CustomResponse>(`http://localhost:8080/api/v1/Order/addOrder`,order,{params : params});
  }


  getRulesPdf(orderId: number): Observable<any> {
    const params = new HttpParams().append('orderId', orderId);
    return this.http.get(`http://localhost:8080/api/v1/Order/pdf/generate`,{observe:'response',responseType:'blob',params : params});
  }


  getOrganizationOrders(organizationId : number): Observable<CustomResponse>{

    const params = new HttpParams().append('organizationId', organizationId );
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/Order/getOrganizationOrders',{params:params});

  }








}
