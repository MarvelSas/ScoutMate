import { Component, OnInit } from '@angular/core';
import {OrderService} from "../services/order.service";
import {Order} from "../models/Order";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {OrderOutPut} from "../models/OrderOutPut";

@Component({
  selector: 'app-order-dashboard',
  templateUrl: './order-dashboard.component.html',
  styleUrls: ['./order-dashboard.component.css']
})
export class OrderDashboardComponent implements OnInit {

  public organizationOrders: OrderOutPut[] = []
  public staticOrganization=0;

  constructor(public orderService:OrderService,private router: Router,private  route:ActivatedRoute) {


    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);


      orderService.getOrganizationOrders(this.staticOrganization).subscribe((customResponse: CustomResponse) => {

        this.organizationOrders = customResponse.data.getOrganizationOrders;


      })
    })


  }

  ngOnInit(): void {
  }

  public getOrganizationOrders(){
    return this.organizationOrders;
  }

  public openPDF(orderId : number){
    this.orderService.getRulesPdf(orderId).subscribe((res)=>{
      let blob:Blob=res.body as Blob;
      let url = window.URL.createObjectURL(blob);
      window.open(url);
    })
  }


  public convertDate(data:string):Date{
    return new Date(data);
  }

  public gotocreateorder(){
    this.router.navigate(['createorder',{organizationId:this.staticOrganization}]);
  }


}
