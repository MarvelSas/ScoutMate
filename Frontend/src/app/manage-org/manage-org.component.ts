import { Component, OnInit } from '@angular/core';
import {OrganizationService} from "../services/organization.service";
import {Router} from "@angular/router";
import {Organization} from "../models/Organization";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserOrganization} from "../models/AppUserOrganization";

@Component({
  selector: 'app-manage-org',
  templateUrl: './manage-org.component.html',
  styleUrls: ['./manage-org.component.css']
})
export class ManageOrgComponent implements OnInit {


  public subOrganizatinExist:boolean=false;
  public showErrorValidationModal:boolean=false;
  public organizationOwned:Organization[]=[];
  public currentOrganization : Organization = new Organization();
  public currentSubOrganization:Organization[]=[];
  public currentOrganizationUsers:AppUserOrganization[]=[];



  constructor(public organizationService:OrganizationService,private router: Router) {

      organizationService.getUserOwnedOrganizations().subscribe((customRespoceMain:CustomResponse)=>{
        this.organizationOwned=customRespoceMain.data.ownedOrganization
        this.currentOrganization = this.organizationOwned.at(0);

        this.subOrganizationRoler(this.currentOrganization.id!);
      })



  }
  ngOnInit(): void {
  }
  getOrganizationOwned():Organization[]{
    return this.organizationOwned;
  }
  getCurrentSubOrganization():Organization[]{
    return this.currentSubOrganization;
  }
  getCurrentOrganizationUsers():AppUserOrganization[]{
    return this.currentOrganizationUsers;
  }


  onChangeOrganization(){
    this.subOrganizationRoler(this.currentOrganization.id!);


  }

  subOrganizationRoler(idOrganization:number){
    this.organizationService.getSubOrganizationBelongToMainOrganization(idOrganization).subscribe((customRespoceSub:CustomResponse)=>{
      this.currentSubOrganization=customRespoceSub.data.getSubOrganizationBelongToMainOrganization;

      console.log(this.currentSubOrganization)
      if (this.currentSubOrganization === undefined || this.currentSubOrganization.length == 0){
        this.subOrganizatinExist=false;
      } else {
        this.subOrganizatinExist=true;
      }

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

  public gotoorderdashboard(){

    this.router.navigate(['orderdashboard',{organizationId:this.currentOrganization.id}]);

  }

  public gotoeditorg(){

    this.router.navigate(['editorg',{organizationId:this.currentOrganization.id}]);

  }
  public gotomanagesuborg(){


    if (this.subOrganizatinExist){
      this.router.navigate(['managesuborg',{organizationId:this.currentOrganization.id}]);
    }
    else {
      this.showErrorValidationModal=true
    }



  }
  public gotocreateorg(){
    this.router.navigate(['createorg',{organizationId:this.currentOrganization.id}]);
  }
  public gotomanageorgmembers(){
    this.router.navigate(['manageorgmembers',{organizationId:this.currentOrganization.id}]);
  }



}
