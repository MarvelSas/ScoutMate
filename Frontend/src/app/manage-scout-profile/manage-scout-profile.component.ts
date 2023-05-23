import { Component, OnInit } from '@angular/core';
import {AppUserService} from "../services/app-user.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserProfileDetails} from "../models/AppUserProfileDetails";
import {ScoutRank} from "../models/ScoutRank";
import {ScoutInstructorRank} from "../models/ScoutInstructorRank";
import {AttemptService} from "../services/attempt.service";
import {OrganizationService} from "../services/organization.service";

@Component({
  selector: 'app-manage-scout-profile',
  templateUrl: './manage-scout-profile.component.html',
  styleUrls: ['./manage-scout-profile.component.css']
})
export class ManageScoutProfileComponent implements OnInit {

  public userEmail:string="";
  public appUserProfileDetails:AppUserProfileDetails = new AppUserProfileDetails();
  public scoutRanks:ScoutRank[]=[];
  public scoutInstructorRanks:ScoutInstructorRank[]=[];
  public currentScoutRank?:ScoutRank;
  public currentScoutInstructorRank?:ScoutInstructorRank;







  constructor( public appUserService:AppUserService,public attemptService:AttemptService,private organizationService:OrganizationService,private  route:ActivatedRoute) {}

  getScoutInstructorRanks(): ScoutInstructorRank[]{
    return this.scoutInstructorRanks
  }
  getScoutRanks(): ScoutRank[]{
    return this.scoutRanks
  }


  ngOnInit(): void {




    this.attemptService.getAllScoutRank().subscribe((customResponse: CustomResponse) =>{
      this.scoutRanks=customResponse.data.ScoutRanks
      console.log("tab21")


    })


    this.attemptService.getAllScoutInstructorRank().subscribe((customResponse: CustomResponse) =>{
      this.scoutInstructorRanks=customResponse.data.ScoutInstruktorRanks
      console.log("tab21")

    })



    this.route.paramMap.subscribe((params:ParamMap)=> {


      this.userEmail =params.get('email')!;

      this.appUserService.getUserProfailDetails(this.userEmail).subscribe((customResponse: CustomResponse) => {

        console.log(customResponse.data.getUserProfailDetails.scoutRank===null)
        this.currentScoutRank=customResponse.data.getUserProfailDetails.scoutRank===null?this.currentScoutRank=this.scoutRanks.at(0):this.currentScoutRank=this.scoutRanks.at(customResponse.data.getUserProfailDetails.scoutRank.id-1)


        if(customResponse.data.getUserProfailDetails.scoutInstructorRank!==null){
          this.currentScoutInstructorRank = this.scoutInstructorRanks.at(customResponse.data.getUserProfailDetails.scoutInstructorRank.id-1)
        }

        this.appUserProfileDetails = customResponse.data.getUserProfailDetails;

        console.log(customResponse.data.getUserProfailDetails.scoutRank)


      })




    })





  }
  changeUserDetails(){

    this.appUserProfileDetails.scoutRank = this.currentScoutRank;
    this.appUserProfileDetails.scoutInstructorRank = this.currentScoutInstructorRank;

    this.organizationService.putChangeUserDetails(this.appUserProfileDetails).subscribe((customResponse: CustomResponse) =>{

      console.log(customResponse)

    })

    console.log(this.currentScoutInstructorRank)
  }

}

