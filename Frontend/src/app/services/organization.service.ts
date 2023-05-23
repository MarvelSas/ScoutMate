import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomResponse} from "../models/CustomResponse";
import {AppUserProfileDetails} from "../models/AppUserProfileDetails";

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {

  constructor(private http: HttpClient) { }

  getAllOrganizationUsersAttempt(organizationId : number):Observable<CustomResponse>{
    let params = new HttpParams()
      .set('idOrganization', organizationId);
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getAllOrganizationUsersAttempt',{params:params});
  }
  getUserOwnedOrganizations():Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getUserOwnedOrganizations')
  }
  getAllActiveLines(organizationId : number): Observable<CustomResponse> {

    const params = new HttpParams().append('idOrganization', organizationId);

    return this.http.get<CustomResponse>(`http://localhost:8080/api/v1/organization/allActiveLines`,{params : params});
  }

  getOrganizationWhereUserBelong(organizationId : number): Observable<CustomResponse> {

    const params = new HttpParams().append('idOrganization', organizationId);

    return this.http.get<CustomResponse>(`http://localhost:8080/api/v1/organization/getOrganization`,{params : params});
  }
  getSubOrganizationBelongToMainOrganization(organizationId : number): Observable<CustomResponse> {

    const params = new HttpParams().append('idOrganization', organizationId);

    return this.http.get<CustomResponse>(`http://localhost:8080/api/v1/organization/getSubOrganizationBelongToMainOrganization`,{params : params});
  }
  getAllUsersFromOrganization(organizationId : number): Observable<CustomResponse> {

    const params = new HttpParams().append('idOrganization', organizationId);

    return this.http.get<CustomResponse>(`http://localhost:8080/api/v1/organization/allUsersFromOrganization`, {params: params});
  }

  getOrganization(organizationId : number): Observable<CustomResponse>{

    const params = new HttpParams().append('idOrganization', organizationId );
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getOrganization',{params:params});

  }


  putOrganizationPhoto(photo : any,organizationId:number): Observable<CustomResponse>{


    console.log(photo)

    let uploadForm = new FormData();


    uploadForm.append(
      'photo', photo,photo.name
    );



    let params = new HttpParams()
      .set('organizationId', organizationId )
    return this.http.put<CustomResponse>('http://localhost:8080/api/v1/organization/changeOrganizationPhoto',uploadForm,{params:params});

  }

  putOrganizationName(organizationName : string,organizationId:number): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('organizationId', organizationId )
      .set('organizationName', organizationName )
    return this.http.put<CustomResponse>('http://localhost:8080/api/v1/organization/changeOrganizationName',{},{params:params});

  }


  getAllOwnersFromOrganization(organizationId : number): Observable<CustomResponse>{

    const params = new HttpParams().append('idOrganization', organizationId );
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getAllOwnerFromOrganization',{params:params});

  }


  addOwnerToOrganization(organizationId : number,appUserEmail : String): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('idOrganization', organizationId )
      .set('email', appUserEmail.toString() )
    return this.http.patch<CustomResponse>('http://localhost:8080/api/v1/organization/grantOwnerPermission',{},{params:params});

  }

  removeOwnerToOrganization(organizationId : number,appUserEmail : String): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('idOrganization', organizationId )
      .set('email', appUserEmail.toString() )
    return this.http.patch<CustomResponse>('http://localhost:8080/api/v1/organization/removeOwnerPermission',{},{params:params});

  }


  createSubOrganization(photo : any,organizationId:number,organizationName: string,ownerEmail: String): Observable<CustomResponse>{


    console.log(photo)

    let uploadForm = new FormData();


    uploadForm.append(
      'myFile', photo,photo.name
    );



    let params = new HttpParams()
      .set('superiorOrganizationId', organizationId )
      .set('email', ownerEmail.toString() )
      .set('name', organizationName )
    return this.http.post<CustomResponse>('http://localhost:8080/api/v1/organization/suborganization',uploadForm,{params:params});

  }


  getUsersFromOutSideOrganization(organizationId : number): Observable<CustomResponse>{

    const params = new HttpParams().append('organizationId', organizationId );
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getUsersFromOutSideOrganization',{params:params});

  }

  addAppUsetToOrganization(email:String,idOrganization : number,nameRole:string):Observable<CustomResponse>{
    console.log(email)
    console.log(idOrganization)
    console.log(nameRole)

    let params = new HttpParams()
      .set('email', email.toString() )
      .set('idOrganization', idOrganization )
      .set('nameRole', nameRole )
    return this.http.post<CustomResponse>('http://localhost:8080/api/v1/organization/addusertoorganization',{},{params:params});
  }


  getAllUsersFromOrganizationWithOutYou(idOrganization : number): Observable<CustomResponse>{

    const params = new HttpParams().append('idOrganization', idOrganization );
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getAllUsersFromOrganizationWithOutYou',{params:params});

  }

  deleteUserFromOrganization(email : String, idOrganization:number): Observable<CustomResponse>{

    let params = new HttpParams()
      .set('email', email.toString() )
      .set('idOrganization', idOrganization );
    return this.http.delete<CustomResponse>('http://localhost:8080/api/v1/organization/delateFromOrganization',{params:params});

  }

  putChangeUserDetails(appUserProfileDetails:AppUserProfileDetails): Observable<CustomResponse>{

    return this.http.put<CustomResponse>('http://localhost:8080/api/v1/organization/UserProfailDetails',appUserProfileDetails);

  }

  getChiefOfScout(): Observable<CustomResponse>{

    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/organization/getChiefOfScout');

  }

  grantPermissionToChief(email : String):Observable<CustomResponse>{
    console.log(email)


    let params = new HttpParams()
      .set('email', email.toString() )

    return this.http.post<CustomResponse>('http://localhost:8080/api/v1/organization/admin/grantPermissionToChiefOfScout',{},{params:params});
  }

  deleteChiefPermission(email : String):Observable<CustomResponse>{

    let params = new HttpParams()
      .set('email', email.toString() )

    return this.http.delete<CustomResponse>('http://localhost:8080/api/v1/organization/admin/deleteChiefPermission',{params:params});
  }





}
