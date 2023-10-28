import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SignUpService } from '../services/sign-up.service';

@Component({
  selector: 'app-token-login',
  templateUrl: './token-login.component.html',
  styleUrls: ['./token-login.component.css']
})
export class TokenLoginComponent implements OnInit {
  public minValue: number = environment.tokenMinValue;
  public maxValue: number = environment.tokenMaxValue;

  constructor(private signUpService: SignUpService) { }

  ngOnInit(): void {
  }

  public tokenLogin(){
    console.log((document.getElementById("tokenLoginForm") as HTMLFormElement).checkValidity())
    if ((document.getElementById("tokenLoginForm") as HTMLFormElement).checkValidity()){
      var obj = {
        "token": (document.getElementById("token") as HTMLInputElement).value,
        "username": sessionStorage.getItem("username")
      };

      this.signUpService.cofirmRegistration(obj).subscribe(
        (response: any) => {
          if (response + '' === 'confirm'){
            sessionStorage.setItem('flag', 'true');

            window.location.href = "profile/" + sessionStorage.getItem("username");
          }else{
            this.badMaessage();
          }
        },
        (error: HttpErrorResponse) => {
          this.badMaessage();
        }
      );
    }
  }

  private badMaessage(){
    var showResult = document.getElementById("messageTokenLogin");
    showResult!.innerHTML = "<div class=\"alert alert-danger\">Operation Failed</div>";
  
    setTimeout(function(){
      document.getElementById("messageTokenLogin")!.innerHTML = "";
    }, 3000);
  }

}
