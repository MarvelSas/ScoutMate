import { Component, OnInit } from '@angular/core';
import {Data, UserProfile} from "../models/user-profile.model";
import {UserProfileService} from "../services/user-profile.service";
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { Observable } from 'rxjs';
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {UserProfileEdit} from "../models/user-profile-edit.model";


registerLocaleData(localeFr);
@Component({
  selector: 'app-manage-profile',
  templateUrl: './manage-profile.component.html',
  styleUrls: ['./manage-profile.component.css']
})
export class ManageProfileComponent implements OnInit {

  constructor(
    private userProfileService: UserProfileService,
  ) {}
  userDetails?: UserProfile;
  // public userProfile : UserProfile = new class implements UserProfile {
  //   data: Data;
  //   message: string;
  //   status: string;
  //   statusCode: number;
  //   timeStamp: string;
  // }();
  editedUserDetails?: UserProfileEdit;
  submitted = false;
  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';
  preview = '';
  imageInfos?: Observable<any>;

  isModalVisible = false;
  isModalPhotoVisible = false;
  onChange() { this.submitted = true; }
  ngOnInit(): void {
    this.userProfileService.getUserDetails().subscribe(res => {
      if (res) {
        this.userDetails = res;
        console.log(res);
        if (this.userDetails.data.userProfilDetails.photo){
          this.preview = 'http://localhost:8080/images/appUserPhoto/' + this.userDetails.data.userProfilDetails.photo;
        }
        console.log(this.preview);
      } else {
        alert ("Failed to query list")
      }
    })
  }


  selectFile(event: any): void {
    this.message = '';
    this.preview = '';
    this.progress = 0;
    this.selectedFiles = event.target.files;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.preview = '';
        this.currentFile = file;

        const reader = new FileReader();

        reader.onload = (e: any) => {
          console.log(e.target.result);
          this.preview = e.target.result;
        };

        reader.readAsDataURL(this.currentFile);
      }
    }
  }

  uploadPhoto(): void{
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.userProfileService.putUserProfilePhoto(this.currentFile).subscribe({
          next: (event: any) => {
            if (event) {
              console.log(event)
              this.isModalPhotoVisible = true
            }
          },
          error: (err: any) => {
            console.log(err);
            this.currentFile = undefined;
          },
        });
      }

      this.selectedFiles = undefined;
    }
  }

  changeUserProfileData(): void{
    let params = {
      name: this.userDetails?.data.userProfilDetails.name,
      surname: this.userDetails?.data.userProfilDetails.surname,
      birthday: this.userDetails?.data.userProfilDetails.birthday,
      nickName: this.userDetails?.data.userProfilDetails.nickName,
    }
    console.log(params);
    this.userProfileService.putUserProfileChange(params).subscribe({
      next: (event: any) => {
        if (event) {
          console.log(event);
          this.isModalVisible = true;
        }
      },
      error: (err: any) => {
        console.log(err);
      },
    });

  }
}
