import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ConsumerService } from '../services/consumer.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  public firstNameRegex: string = environment.firstNameRegex;
  public lastNameRegex: string = environment.lastNameRegex;
  public passwordRegex: string = environment.passwordRegex;
  public emailRegex: string = environment.emailRegex;
  public phoneRegex: string = environment.phoneRegex;
  public cityRegex: string = environment.cityRegex;

  private picture: File | undefined;

  @Output() emitUpdateProfile = new EventEmitter<string>();

  constructor(private consumerService: ConsumerService) { }

  ngOnInit(): void {
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
      this.picture = (document.getElementById("imageID") as HTMLInputElement)!.files![i];
      var reader = new FileReader();

      reader.onload = function(e) {
        document.getElementById("idAddImagesContent")!.insertAdjacentHTML("afterbegin", "<div style=\"padding: 10px;text-align: center;\" class=\"col-sm-12\"><img src=" + e!.target!.result + " style=\"width:140px;height:140px;\"></div>");
      };

      reader.readAsDataURL((document.getElementById("imageID") as HTMLInputElement)!.files![i]);
    }
  }

  public update(){
    if ((document.getElementById("updateProfileForm")as HTMLFormElement).checkValidity()){
      var obj = {
        "firstName": (document.getElementById("signUpFirstName")as HTMLInputElement).value,
        "lastName": (document.getElementById("signUpLastName")as HTMLInputElement).value,
        "password": (document.getElementById("signUpPassword")as HTMLInputElement).value,
        "email": (document.getElementById("signUpEmail")as HTMLInputElement).value,
        "phone": (document.getElementById("signUpPhone")as HTMLInputElement).value,
        "city": (document.getElementById("signUpCity")as HTMLInputElement).value,
        "avatar": this.picture
      };

      this.consumerService.updateconsumer(obj).subscribe(
        (response: any) => {
          if (response.body + '' !== 'null'){
            this.showResultMessage({"result": true});
            sessionStorage.setItem("password", (document.getElementById("signUpPassword")as HTMLInputElement).value);
            (document.getElementById("updateProfileForm") as HTMLFormElement).reset();
            this.removeImage();
            this.emitUpdateProfile.emit("");
          }else{
            this.showResultMessage({"result": false});
          }
        },
        (error:HttpErrorResponse) => {
          this.showResultMessage({"result": false});
        }
      );
    }
  }

  private showResultMessage(res: any){
    var showResult = document.getElementById("updateProfileMessage");
    showResult!.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
  
    setTimeout(function(){
      document.getElementById("updateProfileMessage")!.innerHTML = "";
    }, 3000);
  }

}
