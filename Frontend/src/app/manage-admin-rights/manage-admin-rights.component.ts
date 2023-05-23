import { Component, OnInit } from '@angular/core';
import {AppUserService} from "../services/app-user.service";
import {AppUserDetails} from "../models/AppUserDetails";
import {CustomResponse} from "../models/CustomResponse";
import {OrganizationService} from "../services/organization.service";

@Component({
  selector: 'app-manage-admin-rights',
  templateUrl: './manage-admin-rights.component.html',
  styleUrls: ['./manage-admin-rights.component.css']
})
export class ManageAdminRightsComponent implements OnInit {

  public chiefExist?:boolean;
  public appUserChief:AppUserDetails=new AppUserDetails();
  public appUserDetailsList:AppUserDetails[]=[]
  public adminsDetailsList:AppUserDetails[]=[]
  public appUserDetailsAdmin:AppUserDetails=new AppUserDetails();
  public appUserAdminChoosenToRevokePermission:AppUserDetails=new AppUserDetails()
  public showErrorValidationModal:boolean = false;
  public errorMessage: String = "";


  constructor(public appUserService:AppUserService,public organizationService:OrganizationService) {
    this.fetchAppUserDetailsListFromBackend()
    this.fetchChiefOfScout()
  }

  getAppUserDetails():AppUserDetails[]{
    return this.appUserDetailsList
  }

  getAdminsDetailsList():AppUserDetails[]{
  return this.adminsDetailsList
  }


  ngOnInit(): void {
  }


  grandAdminPermission(){
   console.log(this.appUserDetailsAdmin)

    if(this.appUserDetailsAdmin===undefined || this.appUserDetailsAdmin===null){
      this.errorMessage="Brak wybranego użytkownika do nadania uprawnień administratora"
      this.showErrorValidationModal=true;
      return;
    }

    if(this.appUserDetailsAdmin.email===undefined || this.appUserDetailsAdmin.email===null){
      this.errorMessage="Brak wybranego użytkownika do nadania uprawnień administratora"
      this.showErrorValidationModal=true;
      return;
    }

    this.appUserService.putGrantAdminPermision(this.appUserDetailsAdmin.email!).subscribe((customResponse: CustomResponse) => {
      console.log(customResponse.data)
      this.fetchAppUserDetailsListFromBackend()
      this.appUserDetailsAdmin=new AppUserDetails();
    })
  }


  fetchAppUserDetailsListFromBackend(){
    this.appUserService.getAllUsersWithOutOrganization().subscribe((customResponse: CustomResponse) => {
      this.appUserDetailsList=customResponse.data.getAllUsersWithOutOrganization
      this.appUserDetailsList.forEach(c =>{
        c.searchParam = c.name+" "+c.surname;
      })
      console.log(this.appUserDetailsList)

    })
    this.appUserService.getAdmins().subscribe((customResponseAdmins: CustomResponse) => {
    this.adminsDetailsList=customResponseAdmins.data.getAdmins

    })

  }
  fetchChiefOfScout(){
    this.organizationService.getChiefOfScout().subscribe((customResponse: CustomResponse) => {
      if (customResponse.data===false){
        this.chiefExist=false;
      }
      else {
        this.chiefExist=true;
        this.appUserChief=customResponse.data.getChiefOfScout
      }
    },error => {
      this.chiefExist=false;
    })

}




  revokePermissionForAppUser(){
    console.log(this.appUserAdminChoosenToRevokePermission)

    if(this.appUserAdminChoosenToRevokePermission===undefined || this.appUserAdminChoosenToRevokePermission.email===null){
      this.errorMessage="Brak wybranego użytkownika do odebrania uprawnień administratora"
      this.showErrorValidationModal=true;
      return;
    }

    this.appUserService.deleteAdminPermission(this.appUserAdminChoosenToRevokePermission.email!).subscribe((customResponse: CustomResponse) => {
      console.log(customResponse.data)
      this.fetchAppUserDetailsListFromBackend()
      this.appUserDetailsAdmin=new AppUserDetails();
    })

  }

  grantPermissionToChief(){


    if(this.appUserChief===undefined || this.appUserChief===null){
      this.errorMessage="Brak wybranego użytkownika do nadania uprawnień naczelnika"
      this.showErrorValidationModal=true;
      return;
    }

    if(this.appUserChief.email===undefined || this.appUserChief.email===null){
      this.errorMessage="Brak wybranego użytkownika do nadania uprawnień naczelnika"
      this.showErrorValidationModal=true;
      return;
    }

    this.organizationService.grantPermissionToChief(this.appUserChief.email!).subscribe((customResponse: CustomResponse) => {
      console.log(customResponse.data)
      this.fetchAppUserDetailsListFromBackend()
      this.chiefExist=true;

    })
  }
  deleteChiefPermission(){
    this.organizationService.deleteChiefPermission(this.appUserChief.email!).subscribe((customResponse: CustomResponse) => {
      this.fetchAppUserDetailsListFromBackend()
      this.chiefExist=false;
      this.appUserChief=new AppUserDetails();
    })

  }






}
