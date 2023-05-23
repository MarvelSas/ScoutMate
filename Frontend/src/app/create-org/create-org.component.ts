import { Component, OnInit } from '@angular/core';
import {OrganizationService} from "../services/organization.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Organization} from "../models/Organization";
import {AppUserDetails} from "../models/AppUserDetails";
import {AppUserService} from "../services/app-user.service";
import {CustomResponse} from "../models/CustomResponse";

@Component({
  selector: 'app-create-org',
  templateUrl: './create-org.component.html',
  styleUrls: ['./create-org.component.css']
})
export class CreateOrgComponent implements OnInit {

  public staticOrganization=0;
  public appUserDetails:AppUserDetails=new AppUserDetails();
  public organization:Organization=new Organization();
  public allAppUsers:AppUserDetails[]=[];
  public url: any;
  public message : string ="";
  public finalPhoto : any;
  public newOrganizationName: string = ""
  public showErrorValidationModal:boolean=false;
  public errorMessageList : string[]=[]

  constructor(public organizationService:OrganizationService,public appUserService:AppUserService,private router: Router,private  route:ActivatedRoute) {

    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);

    });

    appUserService.getAllUsersWithOutYou().subscribe((customRespoceUsers:CustomResponse)=>{
      this.allAppUsers=customRespoceUsers.data.getAllUsersWithOutYou
    })

  }

  getAppAllAppUsers(){
    return this.allAppUsers
  }

  ngOnInit(): void {
  }

  getErrorList():string[]{
    return this.errorMessageList;
  }



  changePhoto(event: any) {


    if(!event.target.files[0] || event.target.files[0].length == 0) {
      this.message = 'Musisz wybrać zdjęcie';
      return;
    }


    var mimeType = event.target.files[0].type;

    if (mimeType.match(/image\/*/) == null) {
      this.message = "Podany plik nie jest zdjęciem";
      return;
    }


    var reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);

    reader.onload = (_event) => {
      this.message="";
      this.url = reader.result;
      this.finalPhoto = event.target.files[0];
    }

  }

  submitCreatingOrganization(){

    this.errorMessageList=[];


    if(this.newOrganizationName===""){
      this.errorMessageList.push("Brak nazwy organizacji")
      this.showErrorValidationModal=true
    }


    if(!(this.message==="" && this.finalPhoto!==undefined)){
      this.errorMessageList.push("Brak wybranego zdjęcia lub zdjęcie posiada nie właściwy format")
      this.showErrorValidationModal=true
    }


    if(!this.appUserDetails){
      this.errorMessageList.push("Brak wybranego zarządcy organizacji")
      this.showErrorValidationModal=true
    }

    if(this.showErrorValidationModal===false){

      this.organizationService.createSubOrganization(this.finalPhoto,this.staticOrganization,this.newOrganizationName,this.appUserDetails.email!).subscribe((customRespoce:CustomResponse)=>{
        console.log(customRespoce)
        this.router.navigate(['manageorg']);
      })

    }




  }

}
