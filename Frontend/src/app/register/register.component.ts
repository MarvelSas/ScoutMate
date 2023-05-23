import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: any = {};
  sessionId: any = "";

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  register(): void {
    let url = '/api/v1/registration';
    this.http.post<any>(url, {
      email: this.model.email,
      password: this.model.password,
      name: this.model.name,
      surname: this.model.surname,
      authorized: null,
      active: null,
      birthday: this.model.birthday + 'T20:05:54.9304303',
      nickName: this.model.nickname,
      scoutAlias: null
    }).subscribe( )
    this.router.navigate(['']);
  }
}
