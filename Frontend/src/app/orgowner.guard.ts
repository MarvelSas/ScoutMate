import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class OrgownerGuard implements CanActivate {

  constructor(private router: Router, private jwtService: JwtHelperService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let answer:boolean=false;
    jwt_decode<any>(this.jwtService.tokenGetter()).roles.forEach((r:String)=>{
      if (r === 'NACZELNIK' ||r === "KOMENDANT CHORAGWI" ||r === "KOMENDANT HUFCA"||r ==="DRUŻYNOWY"||r === "ZASTĘPOWY"){
        answer=true;
        return;
      }
    })

    if(answer){
      return true;
    }else {
      this.router.navigate(['home']);
      return false
    }
  }

}
