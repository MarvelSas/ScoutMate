import { Component, OnInit } from '@angular/core';
import {AttemptTask} from "../models/AttemptTask";
import {AppUserDetails} from "../models/AppUserDetails";
import {AttemptService} from "../services/attempt.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {UserProfileService} from "../services/user-profile.service";
import {UserProfilDetails, UserProfile} from "../models/user-profile.model";
import {AppUsersDetailsAttempt} from "../models/AppUsersDetailsAttempt";
import {AppUserService} from "../services/app-user.service";
import {AppUserProfileDetails} from "../models/AppUserProfileDetails";
import {OrganizationService} from "../services/organization.service";

@Component({
  selector: 'app-badgesrank-dashboard',
  templateUrl: './badgesrank-dashboard.component.html',
  styleUrls: ['./badgesrank-dashboard.component.css']
})
export class BadgesrankDashboardComponent implements OnInit {

  public appUsersDetailsAttempt:AppUsersDetailsAttempt[]=[];
  public appUserProfileDetails:AppUserProfileDetails = new AppUserProfileDetails();
  public staticOrganization=0;

  constructor(public attemptService:AttemptService,public organizationService:OrganizationService,private router: Router, public appUserService:AppUserService ,private  route:ActivatedRoute) {



    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.staticOrganization =parseInt(params.get('organizationId')!);






   appUserService.getMyProfileDetails().subscribe((customRespoceOuter:CustomResponse)=>{

      this.appUserProfileDetails = customRespoceOuter.data.userProfilDetails;


     organizationService.getAllOrganizationUsersAttempt(this.staticOrganization).subscribe((customRespoceInner:CustomResponse)=>{

        this.appUsersDetailsAttempt=customRespoceInner.data.getAllOrganizationUsersAttempt

      })
   })
  })





  }

  ngOnInit(): void {

  }

  public getAllOrganizationUsers(): AppUsersDetailsAttempt[]{
    return this.appUsersDetailsAttempt
  }

  public checkIfYouCanStartScoutRankAttempt(appUsersDetailsAttempt:AppUsersDetailsAttempt): boolean{

    //If created User don't have scoutRank
    if(this.appUserProfileDetails.scoutRank===null ||this.appUserProfileDetails.scoutRank===undefined){
      return true;
    }

    if(this.appUserProfileDetails.scoutRank.level>appUsersDetailsAttempt.scoutRank!.level){
      return false;
    }

    return true;
  }

  public checkIfYouCanStartScoutRankInstructorAttempt(appUsersDetailsAttempt:AppUsersDetailsAttempt): boolean{

    //If created User don't have scoutInstructorRank
    if(this.appUserProfileDetails.scoutInstructorRank===null ||this.appUserProfileDetails.scoutInstructorRank===undefined){
      return true;
    }

    if(appUsersDetailsAttempt.scoutInstructorRank===null || appUsersDetailsAttempt.scoutInstructorRank===undefined){
      return false;
    }

    if(this.appUserProfileDetails.scoutInstructorRank.level>appUsersDetailsAttempt.scoutInstructorRank.level){
      return false;
    }

    return true;
  }
  public gotoCreateinsturctorattempt(emailScoutGet?:String){

    this.router.navigate(['createinsturctorattempt',{emailScout:emailScoutGet,organizationId:this.staticOrganization}]);

  }
  public gotoCreateScoutAtempt(emailScoutGet?:String){

    this.router.navigate(['createscouttempt',{emailScout:emailScoutGet,organizationId:this.staticOrganization}]);

  }

  public gotocreateMeritbadgeattempt(emailScoutGet?:String){

    this.router.navigate(['createmeritbadgeattempt',{emailScout:emailScoutGet,organizationId:this.staticOrganization}]);

  }










}
