import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public username: string = "";
  public flagLogin: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.username = sessionStorage.getItem("username") + "";
    this.flagLogin = sessionStorage.getItem("username") + "" === "null" ? false : true;
  }

  public logoutUser(){
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('password');
    sessionStorage.removeItem('flag');
    sessionStorage.removeItem('id');

    window.location.href = "home";
  }

}
