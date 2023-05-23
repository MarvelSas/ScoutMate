import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  model: any = {};

  constructor(
    private router: Router,
    private http: HttpClient,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  changePassword(): void {
    let url = '/api/v1/forgetPassword/change';
    this.route.queryParams.subscribe(params => {this.http.post<any>(url, this.model.password, {params:params}).subscribe()})
    this.router.navigate(['']);
  }

}
