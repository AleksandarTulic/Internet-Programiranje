import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GuardTokenLoginService implements CanActivate {

  constructor() { }

  canActivate(): boolean {
    if (sessionStorage.getItem('flag') + '' === 'false'){
      return true;
    }else{
      window.location.href = "home";
      return false;
    }
  }
}
