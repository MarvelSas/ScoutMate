import { Component, OnInit } from '@angular/core';
import {OrganizationService} from "../services/organization.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserDetails} from "../models/AppUserDetails";
import {Organization} from "../models/Organization";

@Component({
  selector: 'app-manage-org-members',
  templateUrl: './manage-org-members.component.html',
  styleUrls: ['./manage-org-members.component.css']
})
export class ManageOrgMembersComponent implements OnInit {
  public staticOrganization=0;
  public allAppUsers:AppUserDetails[]=[];
  public appUserDetails:AppUserDetails=new AppUserDetails();
  public appUserFromOrganization:AppUserDetails=new AppUserDetails();
  public roleName?:string="Harcerz";
  public organization:Organization=new Organization()
  public nameOrganization:string="Pusta"

  public roleNameList : String[]=["Harcerz"];

  public roleNameListZastep : String[]=["Podzastępowy", "Proporcowy", "Apteczkowy"];

  public roleNameListDrozyna : String[]=["Przyboczny", "Kwatermistrz", "Wódz Gromady"];

  public roleNameListHufiec : String[]=["Zastępca Hufcowego", "Członek Rady Hufca", "Sekretarz Hufca", "Szef Kapituły Stopni Harcerskich"];

  public roleNameListChorongiew : String[]=["Zastępca Komendanta Chorągwi",
    "Członek Rady Chorągwi", "Szef Kapituły Stopni Instruktorskich", "Komendant Szkoły Instruktorskiej", "Kapelan"];
  public roleNameListKwateraGlowna : String[]=["Naczelnik", "Zastępca Naczelnika", "Członkowie GKH"];




  public allAppUsersFromOrganization:AppUserDetails[]=[];
  public showErrorValidationModal = false;

  constructor(public organizationService:OrganizationService,private  route:ActivatedRoute,private router: Router) {

    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);

        this.addEvent();

    });
  }

  addEvent(){
    this.organizationService.getUsersFromOutSideOrganization(this.staticOrganization).subscribe((customRespoceUsers:CustomResponse)=>{
      this.allAppUsers=customRespoceUsers.data.getUsersFromOutSideOrganization
      this.allAppUsers.forEach(c =>{
        c.searchParam = c.name+" "+c.surname;
      })
      this.appUserDetails=new AppUserDetails();
    })
    this.organizationService.getAllUsersFromOrganizationWithOutYou(this.staticOrganization).subscribe((customRespoceUsersAll:CustomResponse)=>{
      this.allAppUsersFromOrganization=customRespoceUsersAll.data.getAllUsersFromOrganizationWithOutYou
    })
    this.organizationService.getOrganization(this.staticOrganization).subscribe((customRespoceOrg:CustomResponse)=>{
        this.organization=customRespoceOrg.data.organization
        this.nameOrganization=this.organization.name!
    })

  }


  getAppAllAppUsers(){
    return this.allAppUsers
  }

  getRoleNameList(){
    if (this.organization.organizationType==="ZASTEP"){return this.roleNameListZastep;}
    else if (this.organization.organizationType==="DRUZYNA"){return this.roleNameListDrozyna;}
    else if (this.organization.organizationType==="HUFIEC"){return this.roleNameListHufiec;}
    else if (this.organization.organizationType==="CHORAGIEW"){return this.roleNameListChorongiew;}
    else if (this.organization.organizationType==="KWATERA_GLOWNA"){return this.roleNameListKwateraGlowna;}
    else {return this.roleNameList}
  }
  getAllAppUsersFromOrganization(){
    return this.allAppUsersFromOrganization
  }

  ngOnInit(): void {
  }
  onChangeOrganization(){
  console.log(this.roleName)
  }


  addAppUserToOrganization(){

    if( this.appUserDetails===null || this.appUserDetails===undefined){
      this.showErrorValidationModal=true;
      return;
    }

    if( this.appUserDetails.email===undefined || this.appUserDetails.email===null){
      this.showErrorValidationModal=true;
      return;
    }

    this.organizationService.addAppUsetToOrganization(this.appUserDetails.email!,this.staticOrganization,this.roleName!).subscribe((customRespoce:CustomResponse)=>{
      this.addEvent()
    })
  }

  deleteUserFromOrganization(){
    this.organizationService.deleteUserFromOrganization(this.appUserFromOrganization.email!,this.staticOrganization).subscribe((customRespoce:CustomResponse)=>{
      this.addEvent()
    })
  }

  goToManageScoutProfile(){
    this.router.navigate(['managescoutprofile',{email:this.appUserFromOrganization.email!}]);
  }


}
