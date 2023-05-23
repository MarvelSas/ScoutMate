import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomResponse} from "../models/CustomResponse";
import {Attempt} from "../models/Attempt";

@Injectable({
  providedIn: 'root'
})
export class AttemptService {

  constructor(private http: HttpClient) { }



  getAllScoutInstructorRankAvailableForAppUserAttempt(emailScout:string): Observable<CustomResponse>{

    const params = new HttpParams().append('emailScout', emailScout);
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/allScoutInstructorRankAvailableForAppUserAttempt',{params:params});

  }

  getRulesPdf(): Observable<any> {
    return this.http.get(`http://localhost:8080/pdf/Regulamin_gwiazdek_zuchowych_i_stopni_harcerskich.pdf`,{observe:'response',responseType:'blob'});
  }


  createAttemptScoutInstructorRank(emailScout : string,organizationId : number, attempt:Attempt): Observable<CustomResponse>{
    let params = new HttpParams()
      .set('emailScout', emailScout)
      .set('organizationId', organizationId);

    return this.http.post<CustomResponse>(`http://localhost:8080/api/v1/attempt/addAttemptScoutInstructorRank`,attempt,{params : params});
  }


  getAllScoutRankAvailable(emailScout:string):Observable<CustomResponse>{
    const params = new HttpParams().append('emailScout', emailScout);
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/allScoutRankAvailableForAppUserAttempt',{params:params});
  }

  createAttemptScoutRank(emailScout : string,organizationId : number, attempt:Attempt): Observable<CustomResponse>{
    let params = new HttpParams()
      .set('emailScout', emailScout)
      .set('organizationId', organizationId);

    return this.http.post<CustomResponse>(`http://localhost:8080/api/v1/attempt/addAttemptScoutRank`,attempt,{params : params});
  }

  getAllMeritBadgesAvailable(emailScout:string):Observable<CustomResponse>{
    const params = new HttpParams().append('emailScout', emailScout);
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/getAllMeritBadgeForAppUserAttempt',{params:params});
  }

  createAttemptMeritBadge(emailScout : string,organizationId : number, attempt:Attempt): Observable<CustomResponse>{
    let params = new HttpParams()
      .set('emailScout', emailScout)
      .set('organizationId', organizationId);
    return this.http.post<CustomResponse>(`http://localhost:8080/api/v1/attempt/addAttemptMeritbadge`,attempt,{params : params});
  }


  getAllMyCreatedAttempt():Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/getAllMyCreatedAttempt')
  }

  closeAttempt(attemptId : number, ifAttemptResultPositive : boolean):Observable<CustomResponse>{
    let params = new HttpParams()
      .set('ifAttemptResultIsPositive', ifAttemptResultPositive)
      .set('attemptId', attemptId )
    return this.http.put<CustomResponse>('http://localhost:8080/api/v1/attempt/closeAttempt',{},{params:params});

  }
  getAllScoutRank():Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/scoutranks')
  }
  getAllScoutInstructorRank():Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/scoutinstructorranks')
  }
  getMyAllAppliedAttemptOpen():Observable<CustomResponse>{
    return this.http.get<CustomResponse>('http://localhost:8080/api/v1/attempt/getMyAllAppliedAttemptOpen')
  }









}
