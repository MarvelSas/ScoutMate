import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  constructor(private http:HttpClient) { }

  public login(username:string,password:string){
    const headers = new HttpHeaders({Authorization:'Basic' + btoa(username+":"+password)});
    this.http.get(`${environment.backendUrl}`,{headers, responseType:'json'});
  }
}
