import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserProfile} from "../models/user-profile.model";
import {Observable} from "rxjs";
import {AppliedAttempt} from "../models/applied-attempt.model";
import {CreatedAttempt} from "../models/created-attempt.model";
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient ) { }
  getUserDetails(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${environment.backendUrl}/api/v1/appUser/getMyUserDetails`);
  }
  getAppliedAttempt(): Observable<AppliedAttempt> {
    return this.http.get<AppliedAttempt>(`${environment.backendUrl}/api/v1/attempt/getAllAppliedAttempt`);
  }

  getCreatedAttempt(): Observable<CreatedAttempt> {
    return this.http.get<CreatedAttempt>(`${environment.backendUrl}/api/v1/attempt/getAllMyCreatedAttempt`);
  }

  putUserProfilePhoto(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('photo', file);

    return this.http.put(`${environment.backendUrl}/api/v1/appUser/changeMyUserProfilePhoto`,formData);
  }

  putUserProfileChange(profile: any): Observable<any> {

    return this.http.put(`${environment.backendUrl}/api/v1/appUser/changeMyUserDetails`,profile);
  }
}
