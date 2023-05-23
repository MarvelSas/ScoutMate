import {Component, OnInit} from '@angular/core';
import {AttemptService} from "../services/attempt.service";
import {Router} from "@angular/router";
import {CustomResponse} from "../models/CustomResponse";
import {Organization} from "../models/Organization";
import {Attempt} from "../models/Attempt";
import {ATTEMPT_STATUS} from "../enums/ATTEMPT_STATUS";
import {OrganizationService} from "../services/organization.service";

@Component({
  selector: 'app-badgesrank-home',
  templateUrl: './badgesrank-home.component.html',
  styleUrls: ['./badgesrank-home.component.css']
})
export class BadgesrankHomeComponent implements OnInit {


  public organizations:Organization[]=[];
  public chosenOrganization: Organization = new Organization()
  public userCreatedAttempts:Attempt[]=[];
  public userCreatedAttemptsFiltr:Attempt[]=[];



  constructor(public attemptService:AttemptService,private organizationService:OrganizationService,private router: Router) {


    organizationService.getUserOwnedOrganizations().subscribe((customRespoce:CustomResponse)=>{

      this.organizations=customRespoce.data.ownedOrganization
      console.log(this.organizations)

      this.chosenOrganization= this.organizations.at(0)
      console.log(this.chosenOrganization.name)

    })


    attemptService.getAllMyCreatedAttempt().subscribe((customRespoce:CustomResponse)=>{
      this.userCreatedAttempts=customRespoce.data.getAllCreatedAttempt
      this.attemptsListFiltr();
    })



  }



  public getAllOrganizations(): Organization[]{
    return this.organizations;
  }
  public userCreatedAttemptForOrganization():Attempt[]{
    return this.userCreatedAttemptsFiltr;
  }

  ngOnInit(): void {
  }


  onChange(){
    this.attemptsListFiltr();
  }

  attemptsListFiltr(){
    this.userCreatedAttemptsFiltr=[];
    this.userCreatedAttemptsFiltr=this.userCreatedAttempts.filter((attempt:Attempt)=>{
      return attempt.organization?.id===this.chosenOrganization.id && attempt.STATUS===ATTEMPT_STATUS.OPEN;
    })
  }

  closeAttempt(attemptId : number, ifAttemptResultPositive : boolean){

    this.attemptService.closeAttempt(attemptId,ifAttemptResultPositive).subscribe((customRespoce:CustomResponse)=>{
      console.log(customRespoce)

      this.attemptService.getAllMyCreatedAttempt().subscribe((customRespoce:CustomResponse)=>{
        this.userCreatedAttempts=customRespoce.data.getAllCreatedAttempt
        this.attemptsListFiltr();
      })

    })

  }

  public gotoBadgesRankDashBoard(){
    this.router.navigate(['badgesrankdashboard',{organizationId:this.chosenOrganization.id}]);
  }




}
