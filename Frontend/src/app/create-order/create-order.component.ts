import {Component, OnInit} from '@angular/core';
import {OrderService} from "../services/order.service";
import {ActionQueueLine} from "../models/ActionQueueLine";
import {CustomResponse} from "../models/CustomResponse";
import {ACTION_TYPE} from "../enums/ACTION_TYPE";
import {Organization} from "../models/Organization";
import {NgForm} from "@angular/forms";
import {Order} from "../models/Order";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {OrganizationService} from "../services/organization.service";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']

})
export class CreateOrderComponent implements OnInit {


  public staticOrganization=0;
  public newOrder : Order = new Order();
  public actionQueueLines : ActionQueueLine[] = [];
  public actionQueueLinesGRANTING_MERITBADGE_AND_RANKS :ActionQueueLine[] = [];
  public actionQueueLinesCREATE_ORGANIZATION :ActionQueueLine[] = [];
  public actionQueueLinesGrantingAndRevoking_FUNCTION :ActionQueueLine[] = [];
  public orderOrganization : Organization = new Organization();
  public showConfirmationFormModal = false;
  public showErrorValidationModal = false;

  public finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS:ActionQueueLine[]=[];
  public finalOrderQueueLinesCREATE_ORGANIZATION:ActionQueueLine[]=[];
  public finalOrderQueueLinesGrantingAndRevoking_FUNCTION:ActionQueueLine[]=[];


  constructor(public  orderService:OrderService,public organizationService:OrganizationService,private router: Router,private  route:ActivatedRoute) {


    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.staticOrganization =parseInt(params.get('organizationId')!);




    //Get Action Queue Line information

      organizationService.getAllActiveLines(this.staticOrganization).subscribe( (customResponse : CustomResponse) =>{
      console.log(customResponse)
      this.actionQueueLines=customResponse.data.getActiveTask;
      this.actionQueueLines.forEach(
        actionQueue=>{
         if(actionQueue.action_type===ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS){
           this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS.push(actionQueue);
         }
         else if(actionQueue.action_type===ACTION_TYPE.CREATE_ORGANIZATION){
           this.actionQueueLinesCREATE_ORGANIZATION.push(actionQueue);
         }
         else if(actionQueue.action_type===ACTION_TYPE.GRANTING_FUNCTION ||actionQueue.action_type===ACTION_TYPE.REVOKING_FUNCTION){
           this.actionQueueLinesGrantingAndRevoking_FUNCTION.push(actionQueue);
         }})
      console.log(this.actionQueueLines)
      console.log("merit")
      console.log(this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS)
      console.log("org")
      console.log(this.actionQueueLinesCREATE_ORGANIZATION)
      console.log("gfun")
      console.log(this.actionQueueLinesGrantingAndRevoking_FUNCTION)
      console.log("rfunc")

    });




    //Get Organization information

    organizationService.getOrganizationWhereUserBelong(this.staticOrganization).subscribe((customResponse : CustomResponse) =>{
      this.orderOrganization=customResponse.data.organization;
      console.log(this.orderOrganization)
    })

    })

  }



  //GRANTING_MERITBADGE_AND_RANKS

  public getActionQueueLineGRANTING_MERITBADGE_AND_RANKS() : ActionQueueLine[]{
    return this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS;
  }
  public getFinalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS() : ActionQueueLine[]{
    return this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS;
  }




  public addToFinalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS(id?:number){
    console.log(id)
    if (id)var tres=this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS.find(x=>x.id===id)
    if (tres)this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.push(tres)
    this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS=this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS.filter(x=> x.id!==id);

  }


  public removeFromFinalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS(id?:number){
    console.log(id)
    if (id)var tres=this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.find(x=>x.id===id)
    if (tres)this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS.push(tres)
    this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS=this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.filter(x=> x.id!==id);

  }

  public resetFinalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS(){

    this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.forEach(actionQueueLine =>{
      this.actionQueueLinesGRANTING_MERITBADGE_AND_RANKS.push(actionQueueLine)
    })
    this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS = [];

  }

  //CREATE_ORGANIZATION


  public getActionQueueLineCREATE_ORGANIZATION() : ActionQueueLine[]{
    return this.actionQueueLinesCREATE_ORGANIZATION;
  }
  public getFinalOrderQueueLinesCREATE_ORGANIZATION() : ActionQueueLine[]{
    return this.finalOrderQueueLinesCREATE_ORGANIZATION;
  }




  public addToFinalOrderQueueLinesCREATE_ORGANIZATION(id?:number){
    console.log(id)
    if (id)var tres=this.actionQueueLinesCREATE_ORGANIZATION.find(x=>x.id===id)
    if (tres)this.finalOrderQueueLinesCREATE_ORGANIZATION.push(tres)
    this.actionQueueLinesCREATE_ORGANIZATION=this.actionQueueLinesCREATE_ORGANIZATION.filter(x=> x.id!==id);

  }


  public removeFromFinalOrderQueueLinesCREATE_ORGANIZATION(id?:number){
    console.log(id)
    if (id)var tres=this.finalOrderQueueLinesCREATE_ORGANIZATION.find(x=>x.id===id)
    if (tres)this.actionQueueLinesCREATE_ORGANIZATION.push(tres)
    this.finalOrderQueueLinesCREATE_ORGANIZATION=this.finalOrderQueueLinesCREATE_ORGANIZATION.filter(x=> x.id!==id);

  }

  public resetFinalOrderQueueLinesCREATE_ORGANIZATION(){

    this.finalOrderQueueLinesCREATE_ORGANIZATION.forEach(actionQueueLine =>{
      this.actionQueueLinesCREATE_ORGANIZATION.push(actionQueueLine)
    })
    this.finalOrderQueueLinesCREATE_ORGANIZATION = [];

  }


  //GrantingAndRevoking_FUNCTION

  public getActionQueueLineGrantingAndRevoking_FUNCTION() : ActionQueueLine[]{
    return this.actionQueueLinesGrantingAndRevoking_FUNCTION;
  }
  public getFinalOrderQueueLinesGrantingAndRevoking_FUNCTION() : ActionQueueLine[]{
    return this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION;
  }




  public addToFinalOrderQueueLinesGrantingAndRevoking_FUNCTION(id?:number){
    console.log(id)
    if (id)var tres=this.actionQueueLinesGrantingAndRevoking_FUNCTION.find(x=>x.id===id)
    if (tres)this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.push(tres)
    this.actionQueueLinesGrantingAndRevoking_FUNCTION=this.actionQueueLinesGrantingAndRevoking_FUNCTION.filter(x=> x.id!==id);

  }


  public removeFromFinalOrderQueueLinesGrantingAndRevoking_FUNCTION(id?:number){
    console.log(id)
    if (id)var tres=this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.find(x=>x.id===id)
    if (tres)this.actionQueueLinesGrantingAndRevoking_FUNCTION.push(tres)
    this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION=this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.filter(x=> x.id!==id);

  }

  public resetFinalOrderQueueLinesGrantingAndRevoking_FUNCTION(){

    this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.forEach(actionQueueLine =>{
      this.actionQueueLinesGrantingAndRevoking_FUNCTION.push(actionQueueLine)
    })
    this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION = [];

  }



  public viewOrder(form: NgForm){


    if(form.valid) {

    this.newOrder.activeQueueLineDTOList = [];

    this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.forEach( GRANTING_MERITBADGE_AND_RANKSLine =>{
      this.newOrder.activeQueueLineDTOList?.push(GRANTING_MERITBADGE_AND_RANKSLine)
    })

    this.finalOrderQueueLinesCREATE_ORGANIZATION.forEach(CREATE_ORGANIZATIONLine =>{
      this.newOrder.activeQueueLineDTOList?.push(CREATE_ORGANIZATIONLine)
    })

    this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.forEach(GrantingAndRevoking_FUNCTION =>{
      this.newOrder.activeQueueLineDTOList?.push(GrantingAndRevoking_FUNCTION)
    } )

    console.log(this.newOrder);


    this.orderService.postViewOrder(this.staticOrganization, this.newOrder).subscribe((res)=>{
      let blob:Blob=res.body as Blob;
      let url = window.URL.createObjectURL(blob);
      window.open(url);
    })

    this.newOrder.activeQueueLineDTOList = [];

    }else {
      this.showErrorValidationModal=true;
    }


  }


  createOrderFirstStep(form: NgForm){
    if(form.valid) {

      this.newOrder.activeQueueLineDTOList = [];

      this.finalOrderQueueLinesGRANTING_MERITBADGE_AND_RANKS.forEach( GRANTING_MERITBADGE_AND_RANKSLine =>{
        this.newOrder.activeQueueLineDTOList?.push(GRANTING_MERITBADGE_AND_RANKSLine)
      })

      this.finalOrderQueueLinesCREATE_ORGANIZATION.forEach(CREATE_ORGANIZATIONLine =>{
        this.newOrder.activeQueueLineDTOList?.push(CREATE_ORGANIZATIONLine)
      })

      this.finalOrderQueueLinesGrantingAndRevoking_FUNCTION.forEach(GrantingAndRevoking_FUNCTION =>{
        this.newOrder.activeQueueLineDTOList?.push(GrantingAndRevoking_FUNCTION)
      } )


      this.showConfirmationFormModal=true;


    }else {
      this.showErrorValidationModal=true;
    }

  }

  creteOrderSecondStep(form: NgForm){

    this.orderService.createOrder(this.staticOrganization, this.newOrder).subscribe((res)=>
    {

      if (res.data.addOrder){
        console.log(res.data.addOrder)
        form.resetForm();
        this.newOrder.activeQueueLineDTOList = [];
        this.router.navigate(['orderdashboard',{organizationId:this.staticOrganization}]);
      }


    })




  }




  ngOnInit(): void {
  }

}
