import { Component, OnInit } from '@angular/core';
import {OrganizationService} from "../services/organization.service";
import {Organization} from "../models/Organization";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserOrganization} from "../models/AppUserOrganization";

@Component({
  selector: 'app-manage-suborg',
  templateUrl: './manage-suborg.component.html',
  styleUrls: ['./manage-suborg.component.css']
})
export class ManageSuborgComponent implements OnInit {

  public mainOrganization:Organization= new Organization();
  public organizations:Organization[]=[];
  public staticOrganization=0;
  public currentOrganization : Organization = new Organization();
  public currentSubOrganization:Organization[]=[];
  public currentOrganizationUsers:AppUserOrganization[]=[];

  constructor(public organizationService:OrganizationService,private  route:ActivatedRoute,private router: Router) {


    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);


      organizationService.getOrganization(this.staticOrganization).subscribe((customResponseMainOrg: CustomResponse) => {
        this.mainOrganization=customResponseMainOrg.data.organization;
      })

      organizationService.getSubOrganizationBelongToMainOrganization(this.staticOrganization).subscribe((customResponseMain: CustomResponse) => {
        this.organizations=customResponseMain.data.getSubOrganizationBelongToMainOrganization;
        this.currentOrganization = this.organizations.at(0);
        this.subOrganizationRoler(this.currentOrganization.id!);

      })
    })
  }

  getMainOrganization():Organization[]{
    return this.organizations;
  }
  getCurrentSubOrganization():Organization[]{
    return this.currentSubOrganization;
  }
  getCurrentOrganizationUsers():AppUserOrganization[]{
    return this.currentOrganizationUsers;
  }




  ngOnInit(): void {
  }
  onChangeOrganization(){
    this.subOrganizationRoler(this.currentOrganization.id!);
  }
  subOrganizationRoler(idOrganization:number){
    this.organizationService.getSubOrganizationBelongToMainOrganization(idOrganization).subscribe((customRespoceSub:CustomResponse)=>{
      this.currentSubOrganization=customRespoceSub.data.getSubOrganizationBelongToMainOrganization;
    })
    this.organizationService.getAllUsersFromOrganization(idOrganization).subscribe((customRespoceUser:CustomResponse)=>{
      this.currentOrganizationUsers=customRespoceUser.data.allUsersFromOrganization
    })
  }

  getFullOrganizationName(organization:Organization):String{


    switch (organization.organizationType){
      case 'KWATERA_GLOWNA': {
        return organization.name +" kwatera główna"
        break;
      }

      case 'CHORAGIEW': {
        return organization.name +" chorągiew"
        break;
      }

      case 'HUFIEC': {
        return organization.name +" hufiec"
        break;
      }
      case 'DRUZYNA': {
        return organization.name +" drużyna"
        break;
      }
      case 'ZASTEP': {
        return organization.name +" zastęp"
        break;
      }
      default:{
        return ""
      }

    }

  }
  public gotomanageorg(){

    this.router.navigate(['manageorg']);

  }


  public gotomanageorgcommander(){

    this.router.navigate(['manageorgcommander',{organizationId:this.currentOrganization.id}]);

  }






}
