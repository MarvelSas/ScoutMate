import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserProfile} from "../models/user-profile.model";
import {UserProfileService} from "../services/user-profile.service";
import {environment} from "../../environments/environment";
import {AppliedAttempt} from "../models/applied-attempt.model";
import {ClrDatagridSortOrder} from "@clr/angular";
import {CreatedAttempt} from "../models/created-attempt.model";


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit {

  constructor(
    private userProfileService: UserProfileService
  ) { }
  userDetails?: UserProfile;
  appliedAttempt?: AppliedAttempt;
  appliedAttemptCount?: number;

  createdAttempt?: CreatedAttempt;
  createdAttemptCount?: number;

  scoutInstructorColor!: string | null;
  enviormentUrl!: string;
  descSort = ClrDatagridSortOrder.DESC;
  ngOnInit(): void {
    this.enviormentUrl = environment.backendUrl;

    this.userProfileService.getUserDetails().subscribe(res => {
      if (res) {
        this.userDetails = res;
        if (this.userDetails.data.userProfilDetails.scoutInstructorRank.color){
          if (this.userDetails.data.userProfilDetails.scoutInstructorRank.color === "BLUE") {
            this.scoutInstructorColor = "#1d436c";
          }if (this.userDetails.data.userProfilDetails.scoutInstructorRank.color === "GREEN"){
            this.scoutInstructorColor = "#1d6c29";
          }if (this.userDetails.data.userProfilDetails.scoutInstructorRank.color === "RED"){
            this.scoutInstructorColor = "#6c1d1d";
          }
        }else{
          this.scoutInstructorColor = "#efc006";
        }
        console.log(this.scoutInstructorColor);
        document.documentElement.style.setProperty('--scoutInstructorColor', this.scoutInstructorColor);
        console.log(res);
      } else {
        alert ("Failed to query list")
      }
    })

    this.userProfileService.getAppliedAttempt().subscribe(res => {
      if (res) {
        this.appliedAttempt = res;
        console.log(res);
      } else {
        alert ("Failed to query list")
      }
    })

    this.userProfileService.getCreatedAttempt().subscribe(res => {
      if (res) {
        this.createdAttempt = res;
        for (let attem in this.createdAttempt){
          console.log(attem);
        }
        console.log(res);
      } else {
        alert ("Failed to query list")
      }
    })
  }

}
