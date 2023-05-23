import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AppUserService} from "../services/app-user.service";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserProfileDetails} from "../models/AppUserProfileDetails";
import {AttemptService} from "../services/attempt.service";
import {Attempt} from "../models/Attempt";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public appUserProfileDetails:AppUserProfileDetails = new AppUserProfileDetails()
  public organizationOwnerPermission: boolean = false;
  public adminPermission: boolean = false;
  public dateIsFetched : boolean = false;
  public attempts:Attempt[]=[];
  constructor(
    private router: Router, private http: HttpClient, private appUserService:AppUserService,public attemptService:AttemptService) {

    this.attemptService.getMyAllAppliedAttemptOpen().subscribe((customResponse: CustomResponse) => {
      this.attempts=customResponse.data.getMyAllAppliedAttemptOpen
    })

  }


  getAttempts():Attempt[]{
    return this.attempts
}

getAttemptForWhat(id:number):String{
    if(this.attempts.at(id).TYPE==="SCOUT_RANK") {
      return this.attempts.at(id).scoutRankId!.name
    }
    if(this.attempts.at(id).TYPE==="SCOUT_INSTRUCTOR_RANK") {
      return this.attempts.at(id).scoutInstructorRankId!.name
    }
    if(this.attempts.at(id).TYPE==="MERITBADGE") {
      return this.attempts.at(id).meritBadgeId!.name!
    }



  return "Brak"
}





  ngOnInit(): void {
    this.http.get<any>('/api/v1/appUser/getMyUserDetails').subscribe(res => {
      if (res) {
        console.log('List', res);
      } else {
        alert ("Failed to query list")
      }
    })


    this.organizationOwnerPermission=false;
    this.adminPermission= false;

    this.appUserService.getMyProfileDetails().subscribe((customResponse:CustomResponse)=>{

        this.appUserProfileDetails = customResponse.data.userProfilDetails
        console.log(this.appUserProfileDetails)

        this.appUserProfileDetails.roles?.forEach(role=>{
          if(
            role.name==="NACZELNIK"||
            role.name==="KOMENDANT CHORAGWI"||
            role.name==="KOMENDANT HUFCA"||
            role.name==="DRUŻYNOWY"||
            role.name==="ZASTĘPOWY"
          ){
            this.organizationOwnerPermission=true;
          }


          if(
            role.name==="ADMIN"
          ){
            this.adminPermission=true;
          }
        })


        this.dateIsFetched=true;
      }
    )


  }
  
  gotomanageprofile(){
    this.router.navigate(['manageprofile']);
  }
  gotobadgesrankhome(){
    this.router.navigate(['badgesrankhome']);
  }
  gotomanageorg(){
    this.router.navigate(['manageorg']);
  }
  gotouserprofile(){
    this.router.navigate(['userprofile']);
  }
  gotomanageadminrights(){
    this.router.navigate(['manageadminrights']);
  }
}
