import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";
  access_token: any = "";
  refresh_token: any = "";
  scoutInstructorColor?: string;

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.scoutInstructorColor = "#efc006";
    document.documentElement.style.setProperty('--scoutInstructorColor', this.scoutInstructorColor);

  }

  login(): void {
    //let params = new H
    //params = params.append('email', this.model.email);
    //params = params.append('password', this.model.password);
    let url = '/api/v1/login';
    this.http.get<any>(url, {headers:{
      email: this.model.email,
      password: this.model.password
      }}).subscribe( res =>{
      if (res) {
        //this.sessionId = res.sessionId;
        this.access_token = res.access_token;
        this.refresh_token = res.refresh_token;

        /*sessionStorage.setItem(
          'token',
          this.sessionId
        );*/
        localStorage.setItem('access_token', this.access_token);
        localStorage.setItem('refresh_token', this.refresh_token);
        console.log("User is logged in");
        this.router.navigate(['home']);
      } else {
        alert("Authentication failed.")
      }
    })
  }

}
