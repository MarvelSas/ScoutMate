import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {OrganizationService} from "../services/organization.service";
import {CustomResponse} from "../models/CustomResponse";
import {Organization} from "../models/Organization";

@Component({
  selector: 'app-edit-org',
  templateUrl: './edit-org.component.html',
  styleUrls: ['./edit-org.component.css']
})
export class EditOrgComponent implements OnInit {


  public staticOrganization=0;
  public organization : Organization = new Organization()
  public url: any;
  public message : string ="";
  public finalPhoto : any;
  public organizationName:string=""





  constructor(public organizationService :OrganizationService,private  route:ActivatedRoute) {

    this.route.paramMap.subscribe((params:ParamMap)=> {
      this.staticOrganization = parseInt(params.get('organizationId')!);

      organizationService.getOrganization(this.staticOrganization).subscribe((customResponse: CustomResponse) => {

        this.organization = customResponse.data.organization;



        console.log(this.organization)
      })
    })
  }

  ngOnInit(): void {
  }


  changePhoto(event: any) {


    if(!event.target.files[0] || event.target.files[0].length == 0) {
      this.message = 'Musisz wybrać zdjęcie';
      return;
    }


    var mimeType = event.target.files[0].type;

    if (mimeType.match(/image\/*/) == null) {
      this.message = "Możesz wybrać tylko zdjęcie";
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

  submitChangePhoto(){

    if(this.message==="" && this.finalPhoto!==undefined){
      this.organizationService.putOrganizationPhoto(this.finalPhoto,this.organization.id!).subscribe((customRespoce:CustomResponse)=>{

        console.log(customRespoce)

      })
    }

  }
  submitChangeName() {

    this.organizationService.putOrganizationName(this.organization.name!, this.organization.id!).subscribe((customRespoce: CustomResponse) => {
    })


  }
}
