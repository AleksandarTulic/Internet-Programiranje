import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public usernameRegex: string = environment.usernameRegex;
  public passwordRegex: string = environment.passwordRegex;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  public login(){
    var obj = {
      "username": (document.getElementById("myModalUsername") as HTMLInputElement).value,
      "password": (document.getElementById("myModalPassword") as HTMLInputElement).value
    };

    this.loginService.login(obj).subscribe(
      (response: any) => {
        //console.log(response);
        if (response.body + '' !== 'null'){
          console.table(response.body);
          sessionStorage.setItem("username", response.body.username);
          sessionStorage.setItem("password", response.body.password);
          sessionStorage.setItem("flag", response.body.flag);
          sessionStorage.setItem("id", response.body.id);
          
          if (response.body.flag + '' === 'true')
            window.location.href = "home";
          else
            window.location.href = "token-login";         
        }else{
          this.badMaessage("Operation Failed");
        }
      },
      (error: HttpErrorResponse) => {
        this.badMaessage("Operation Failed");
      }
    );
  }

  private badMaessage(message:any){
    var showResult = document.getElementById("messageLogin");
    showResult!.innerHTML = "<div class=\"alert alert-danger\">Operation Failed</div>";
  
    setTimeout(function(){
      document.getElementById("messageLogin")!.innerHTML = "";
    }, 3000);
  }

}
