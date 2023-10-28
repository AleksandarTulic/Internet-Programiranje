import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SupportService } from '../services/support.service';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {
  public titleRegex: string = environment.supportMessageTitleRegex;

  constructor(private supportService: SupportService) { }

  ngOnInit(): void {
    (document.getElementsByClassName("active")[0] as HTMLElement).className = "";
    (document.getElementById("support") as HTMLElement).className = "active";
  }

  public sendMessage(){
    //console.log(this.check());
    if (this.check() && (document.getElementById("supportMessageForm") as HTMLFormElement).checkValidity()){
      var value = (document.getElementById("supportMessageTextArea") as HTMLTextAreaElement).value;
      
      var obj = {
        "title": (document.getElementById("supportMessageTitle") as HTMLTextAreaElement).value,
        "message": value
      };

      this.supportService.sendMessage(obj).subscribe(
        (response: any) => {
          if (response.body + '' !== 'null'){
            this.showResultMessage({"result": true});
            (document.getElementById("supportMessageForm") as HTMLFormElement).reset();
          }else{
            this.showResultMessage({"result": false});
          }
        },
        (error: HttpErrorResponse) => {
          this.showResultMessage({"result": false});
        }
      );

    }else{
      this.showResultMessage({"result": false});
    }
  }

  private showResultMessage(res: any){
    var showResult = document.getElementById("supportMessageResult");
    showResult!.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
  
    setTimeout(function(){
      document.getElementById("supportMessageResult")!.innerHTML = "";
    }, 3000);
  }

  private check():any{
    var value = (document.getElementById("supportMessageTextArea") as HTMLTextAreaElement).value;

    return new RegExp(environment.supportMessageRegex).test(value + "");
  }

}
