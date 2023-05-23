import {ORDER_TYPE} from "../enums/ORDER_TYPE";

export class OrderOutPut{





  constructor(

    public id:number,
    public order_type ?:ORDER_TYPE,
    public numberOrder?:number,
    public date?:string,
    public name?:string,
    public surname?:string

  ) {}
}
