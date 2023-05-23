import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {OrganizationService} from "../services/organization.service";
import {AppUserOrganization} from "../models/AppUserOrganization";
import {AppUserDetails} from "../models/AppUserDetails";
import {Organization} from "../models/Organization";

@Component({
  selector: 'app-manage-org-commander',
  templateUrl: './manage-org-commander.component.html',
  styleUrls: ['./manage-org-commander.component.css']
})
export class ManageOrgCommanderComponent implements OnInit {


  public staticOrganization=0;
  public mainOrganization:Organization= new Organization();
  public organizationUsers:AppUserOrganization[]=[];
  public organizationOwners: AppUserDetails[]=[];



  constructor(public organizationService:OrganizationService,private  route:ActivatedRoute,private router: Router) {

    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);


      organizationService.getOrganization(this.staticOrganization).subscribe((customResponseMainOrg: CustomResponse) => {
        this.mainOrganization=customResponseMainOrg.data.organization;
      })

      this.organizationService.getAllUsersFromOrganization(this.staticOrganization).subscribe((customRespoceUser:CustomResponse)=>{
        this.organizationUsers=customRespoceUser.data.allUsersFromOrganization
      })

      this.organizationService.getAllOwnersFromOrganization(this.staticOrganization).subscribe((customRespoceOwners:CustomResponse)=>{
        this.organizationOwners = customRespoceOwners.data.getAllOwnerFromOrganization;
      })



    })


  }

  ngOnInit(): void {
  }


  getorganizationUsers():AppUserOrganization[]{
    return this.organizationUsers;
  }


  checkIfUserIsACommander(appUserOrganization:AppUserOrganization):boolean{

    if(this.organizationOwners.find(appUser => appUser.email===appUserOrganization.email)){
      return true
    }
    else {
      return false
    }

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

  addOwnerToOrganization(appUserOrganization: AppUserOrganization){

    this.organizationService.addOwnerToOrganization(this.staticOrganization,appUserOrganization.email!).subscribe((customRespoceOwners:CustomResponse)=>{

      this.organizationService.getAllUsersFromOrganization(this.staticOrganization).subscribe((customRespoceUser:CustomResponse)=>{
        this.organizationUsers=customRespoceUser.data.allUsersFromOrganization
      })

      this.organizationService.getAllOwnersFromOrganization(this.staticOrganization).subscribe((customRespoceOwners:CustomResponse)=>{
        this.organizationOwners = customRespoceOwners.data.getAllOwnerFromOrganization;
      })

    })
  }

  removeOwnerToOrganization(appUserOrganization: AppUserOrganization){

    this.organizationService.removeOwnerToOrganization(this.staticOrganization,appUserOrganization.email!).subscribe((customRespoceOwners:CustomResponse)=>{

      this.organizationService.getAllUsersFromOrganization(this.staticOrganization).subscribe((customRespoceUser:CustomResponse)=>{
        this.organizationUsers=customRespoceUser.data.allUsersFromOrganization
      })

      this.organizationService.getAllOwnersFromOrganization(this.staticOrganization).subscribe((customRespoceOwners:CustomResponse)=>{
        this.organizationOwners = customRespoceOwners.data.getAllOwnerFromOrganization;
      })

    })
  }

}
