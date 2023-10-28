import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SignUpService } from '../services/sign-up.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  public firstNameRegex: string = environment.firstNameRegex;
  public lastNameRegex: string = environment.lastNameRegex;
  public usernameRegex: string = environment.usernameRegex;
  public passwordRegex: string = environment.passwordRegex;
  public emailRegex: string = environment.emailRegex;
  public phoneRegex: string = environment.phoneRegex;
  public cityRegex: string = environment.cityRegex;

  private picture: File | undefined;

  constructor(private signUpService: SignUpService) { }

  ngOnInit(): void {
  }

  public signUp(){
    if ((document.getElementById("signUpForm")as HTMLFormElement).checkValidity()){

      var obj = {
        "firstName": (document.getElementById("signUpFirstName")as HTMLInputElement).value,
        "lastName": (document.getElementById("signUpLastName")as HTMLInputElement).value,
        "username": (document.getElementById("signUpUsername")as HTMLInputElement).value,
        "password": (document.getElementById("signUpPassword")as HTMLInputElement).value,
        "email": (document.getElementById("signUpEmail")as HTMLInputElement).value,
        "phone": (document.getElementById("signUpPhone")as HTMLInputElement).value,
        "city": (document.getElementById("signUpCity")as HTMLInputElement).value,
        "avatar": this.picture
      };

      this.signUpService.register(obj).subscribe(
        (response: any) => {
          if (!isNaN(response)){
            sessionStorage.setItem('username', obj.username);
            sessionStorage.setItem('password', obj.password);
            sessionStorage.setItem('flag', 'false');

            window.location.href = "token-login"
          }else{
            this.badMaessage();
          }
        },
        (error: HttpErrorResponse) => {
          this.badMaessage();
        }
      );
    }else{
      this.badMaessage();
    }
  }

  private badMaessage(){
    var showResult = document.getElementById("message");
				  showResult!.innerHTML = "<div class=\"alert alert-danger\">Operation Failed</div>";
				
				  setTimeout(function(){
					  document.getElementById("message")!.innerHTML = "";
				  }, 3000);
  }

  public addImage() {
    document.getElementById('imageID')!.click();
  }

  public removeImage(){
    document.getElementById("idAddImagesContent")!.innerHTML = "";
    this.picture = undefined;
  }

  public selectedImages(): any{
    for (let i=0;i<(document.getElementById("imageID") as HTMLInputElement)!.files!.length;i++){
      console.log(i);
      this.picture = (document.getElementById("imageID") as HTMLInputElement)!.files![i];
      var reader = new FileReader();

      reader.onload = function(e) {
        document.getElementById("idAddImagesContent")!.insertAdjacentHTML("afterbegin", "<div style=\"padding: 10px;text-align: center;\" class=\"col-sm-12\"><img src=" + e!.target!.result + " style=\"width:140px;height:140px;\"></div>");
      };

      reader.readAsDataURL((document.getElementById("imageID") as HTMLInputElement)!.files![i]);
    }
  }

}
