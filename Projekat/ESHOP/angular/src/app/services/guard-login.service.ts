import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardLoginService implements CanActivate {

  constructor() { }

  canActivate(): boolean {
    if (sessionStorage.getItem('flag') + '' === 'true'){
      return true;
    }else{
      window.location.href = "home";
      return false;
    }
  }
}
