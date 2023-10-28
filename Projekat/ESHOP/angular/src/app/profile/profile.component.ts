import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ConsumerService } from '../services/consumer.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public tabId1: string = "activeProducts";
  public tabId2: string = "finishedProducts";
  public tabId3: string = "productsBought";

  public idConsumer: number = 0;
  public username: string = sessionStorage.getItem("username") + "";
  public firstName: string = "";
  public lastName: string = "";
  public email: string = "";
  public phone: string = "";
  public city: string = "";
  public image: string = "";

  public flagUpdate: boolean = false;
  private routeSub: Subscription | undefined;

  constructor(private consumerService: ConsumerService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    (document.getElementsByClassName("active")[0] as HTMLElement).className = "";
    (document.getElementById("profile") as HTMLElement).className = "active";

    this.routeSub = this.route.params.subscribe(params => {
      this.username = params['username'];
      
      this.flagUpdate = sessionStorage.getItem('username') + '' === 'null' ? false : sessionStorage.getItem('username') + '' === this.username + '' ? true : false;
    });

    this.consumerService.retrieveByUsername(this.username).subscribe(
      (response: any) => {

        //console.log(response);
        if (response.body + '' === 'null'){
          window.location.href = "home";
        }

        this.idConsumer = response.body.id;
        this.firstName = response.body.firstName;
        this.lastName = response.body.lastName;
        this.email = response.body.email;
        this.phone = response.body.phone;
        this.city = response.body.city;
        this.image = response.body.avatar;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  ngOnDestroy() {
    this.routeSub!.unsubscribe();
  }

  public profileChanged(){
    this.consumerService.retrieveByUsername(this.username).subscribe(
      (response: any) => {

        //console.log(response);
        if (response.body + '' === 'null'){
          window.location.href = "home";
        }

        this.idConsumer = response.body.id;
        this.firstName = response.body.firstName;
        this.lastName = response.body.lastName;
        this.email = response.body.email;
        this.phone = response.body.phone;
        this.city = response.body.city;
        this.image = response.body.avatar;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

}
