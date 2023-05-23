import { Component, OnInit } from '@angular/core';
import {Router,ActivatedRoute} from "@angular/router";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-confirmemail',
  templateUrl: './confirmemail.component.html',
  styleUrls: ['./confirmemail.component.css']
})
export class ConfirmemailComponent implements OnInit {

  constructor(
    private router: Router,
    private http: HttpClient,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    let url = '/api/v1/registration/confirm';
    this.route.queryParams.subscribe(params => {this.http.get<any>(url, {params:params}).subscribe();
    console.log(params);
    console.log('params')})
  }

}
