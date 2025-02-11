import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomResponse} from "../models/CustomResponse";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppUserService {

  constructor(private http: HttpClient) { }

  getMyProfileDetails(): Observable<CustomResponse>{
    return this.http.get<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/getMyUserDetails`);
  }
  getUserProfailDetails(email:String): Observable<CustomResponse>{
    let params = new HttpParams()
      .set('email', email.toString() );
    return this.http.get<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/UserProfailDetails`,{params:params});
  }
  getAllUsersWithOutYou(): Observable<CustomResponse>{
    return this.http.get<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/getAllUsersWithOutYou`);
  }
  getAllUsersWithOutOrganization(): Observable<CustomResponse>{
    return this.http.get<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/getAllUsersWithOutOrganization`);
  }

  putGrantAdminPermision(email : String): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('email', email.toString() )

    return this.http.put<CustomResponse>(`${environment.backendUrl}api/v1/appUser/admin/grantAdminPermission`,{},{params:params});

  }
  getAdmins(): Observable<CustomResponse>{
    return this.http.get<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/admin/getAdmins`);
  }

  deleteAdminPermission(email : String): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('email', email.toString() )
    return this.http.delete<CustomResponse>(`${environment.backendUrl}/api/v1/appUser/admin/deleteAdminPermission`,{params:params});

  }







}
