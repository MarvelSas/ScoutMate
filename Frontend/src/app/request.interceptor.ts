import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    /*let token = sessionStorage.getItem('token');
    if(token){
      request = request.clone({headers: request.headers.set('Authorization', token)});
    }*/

    const access_token = localStorage.getItem('access_token');
    if(access_token) {
      request = request.clone({headers: request.headers.set('Authorization', "Bearer " + access_token)})
      return next.handle(request);
    } else {
      return next.handle(request);
    }
  }
}
