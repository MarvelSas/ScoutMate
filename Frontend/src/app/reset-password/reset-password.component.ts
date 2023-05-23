import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  model: any = {};

  constructor(
    private router: Router,
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
  }

  resetPassword(): void {
    let url = "/api/v1/forgetPassword"
    this.http.post<any>(url, this.model.email).subscribe();
    //console.log(this.model.email)
    this.router.navigate(['']);
  }

}
