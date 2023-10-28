import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GuardSignUpService implements CanActivate {

  constructor() { }

  canActivate(): boolean {
    if (sessionStorage.getItem('username') + '' === '' || sessionStorage.getItem('username') + '' === 'null'){
      return true;
    }else{
      window.location.href = "home";
      return false;
    }
  }
}
