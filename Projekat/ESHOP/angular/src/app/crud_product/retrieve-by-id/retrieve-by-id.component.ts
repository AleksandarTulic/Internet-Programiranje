import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Comment } from 'src/app/models/comment.model';
import { Product } from 'src/app/models/product.model';
import { environment } from 'src/environments/environment';
import { CommentService } from '../services/comment.service';
import { ConsumerService } from '../services/consumer.service';
import { ProductBoughtService } from '../services/product-bought.service';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-retrieve-by-id',
  templateUrl: './retrieve-by-id.component.html',
  styleUrls: ['./retrieve-by-id.component.css']
})
export class RetrieveByIdComponent implements OnInit {
  private routeSub: Subscription | undefined;
  public idProduct: number = 0;
  public arrImages: string[] = [];
  public showLargerPicture: string = 'null';
  public flagUpdate: boolean = false;
  public flagDelete: boolean = false;
  public ownerUsername: string = "";
  public ownerPhoneNumber: string = "";
  public ownerEmail: string = "";
  public ownerAvatar: string = "";
  public arrFields: any[] = [];
  public arrComments : any[] = [];
  public idConsumer: number = -1;

  public left: number = 0;
  public right: number = 10;
  private increase: number = 10;
  private numberOfPages: number = 0;

  public flagBought: boolean = false;
  public flagBuy: boolean = false;
  public flagLogin: boolean = false;

  constructor(private route: ActivatedRoute, private productService:ProductService, private consumerService:ConsumerService, private commentService:CommentService,
    private productBoughtService: ProductBoughtService) { }

  ngOnInit(): void {
    this.flagLogin = sessionStorage.getItem("flag") + '' === 'true' ? true : false;

    (document.getElementsByClassName("active")[0] as HTMLElement).className = "";
    (document.getElementById("retrieve-all") as HTMLElement).className = "active";

    this.routeSub = this.route.params.subscribe(params => {
      this.idProduct = Number(params['id']);
    });

    this.loadElements();

    this.productService.exists(this.idProduct).subscribe(
      (response: any) => {
        if (response + "" === "false"){
          window.location.href = "retrieve-all";
        }
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );

    (document.getElementById("defaultTab") as HTMLHRElement).click();

    //console.log(this.idProduct);

    this.productService.retrieveById(this.idProduct).subscribe(
    (response: any) => {
      //console.log(response);
      this.ownerUsername = response.consumerEntity.userEntity.username;
      this.ownerPhoneNumber = response.consumerEntity.phone;
      this.ownerEmail = response.consumerEntity.email;
      this.idConsumer = Number(sessionStorage.getItem("id"));
      console.log("ID: " + sessionStorage.getItem("id"));

      (document.getElementById("productTitle") as HTMLInputElement).value = response.title;
      (document.getElementById("productType") as HTMLInputElement).value = response.productType;//productPrice
      (document.getElementById("productPrice") as HTMLInputElement).value = response.cost;
      (document.getElementById("categoryTitle") as HTMLInputElement).value = response.categoryTitle;
      (document.getElementById("description") as HTMLTextAreaElement).value = response.description;

      this.productService.retrieveFieldValuesOnUpdate(this.idProduct, response.categoryTitle).subscribe(
        (response: any) => {
          this.arrFields = response;
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );

      this.productService.retrieveImagesById(this.idProduct).subscribe(
        (response: Object[]) => {
          for (let i=0;i<response.length;i++){
            this.arrImages.push(response[i] + "");
            
          }
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );

      var username = sessionStorage.getItem('username');
      if (response.consumerEntity.userEntity.username + "" == username){
        this.flagUpdate = true && sessionStorage.getItem('flag') + '' === 'true';
        this.flagDelete = true && sessionStorage.getItem('flag') + '' === 'true';
      }else{
        this.flagBuy = true && sessionStorage.getItem('flag') + '' === 'true';
      }

      this.productBoughtService.existsByProductId(this.idProduct).subscribe(
        (response: boolean) => {
          //console.log(response);
          this.flagBought = response;
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );

      if (response.consumerEntity.avatar + "" !== "null"){
        this.consumerService.retrieveAvatar(response.consumerEntity.userEntity.username, response.consumerEntity.avatar).subscribe(
          (response: any) => {
            this.ownerAvatar = response[0];
          },
          (error: HttpErrorResponse) => {
            alert(error);
          }
        );
      }
    },
    (error:HttpErrorResponse) => {
      alert(error);
    });    
  }

  ngOnDestroy() {
    this.routeSub!.unsubscribe();
  }

  public showLargerImage(a:any){
    this.showLargerPicture = a;
    (document.getElementById("forShowingModal") as HTMLButtonElement).click();
  }

  public buyProduct(){
  
  }

  public deleteProduct(){
    var r = confirm("Are you sure");

    if (r + "" === "true"){
      this.productService.deleteProduct(this.idProduct).subscribe(
        (response: any) => {
          console.log(response);
          if (response.body + "" === "true"){
            alert("Operation successful");
            window.location.href = "retrieve-all";
          }else{
            var showResult = document.getElementById("message");
            showResult!.innerHTML = "<div class=\"alert alert-danger" + "\">Operation Failed</div>";
          
            setTimeout(function(){
              document.getElementById("message")!.innerHTML = "";
            }, 3000);
          }
        },
        (error: HttpErrorResponse) => {
          console.log(error);
        }
      );
    }
  }

  public addComment(){
    //console.log((document.getElementById("addComment") as HTMLTextAreaElement).value);
    var commentValue = (document.getElementById("addComment") as HTMLTextAreaElement).value;
    if (commentValue.length >= 1 && commentValue.length <= environment.commentMaxLength){
      var obj = new Comment(this.idProduct, this.idConsumer, commentValue);

      this.commentService.createComment(obj).subscribe(
        (response: any) => {
          if (response.status + "" === "201"){
            this.left = 0;
            this.right = this.increase;
            this.numberOfPages = 0;

            this.loadElements();
            (document.getElementById("addComment") as HTMLTextAreaElement).value = "";
          }

          var showResult = document.getElementById("messageComment");
				  showResult!.innerHTML = "<div class=\"" + ((response.status + "") === "201" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((response.status + "") === "201" ? "Operation Successful" : "Operation Failed") + "</div>";
				
				  setTimeout(function(){
					  document.getElementById("messageComment")!.innerHTML = "";
				  }, 3000);
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );
    }
  }

  public showComment(a:any){
    (document.getElementById("showComment") as HTMLTextAreaElement).value = a;
    (document.getElementById("forShowingModalcomment") as HTMLButtonElement).click();
  }

  private loadElements():any{
    this.checkPagination();

    this.commentService.retrieveByProductId(this.idProduct, this.left, this.right).subscribe(
      (response: any) => {
        this.arrComments = response;

        for (let i=0;i<response.length;i++){
          this.arrComments[i].datetime = new Date(response[i].datetime).getDate() + "-" + (new Date(response[i].datetime).getMonth() + 1) + "-" + new Date(response[i].datetime).getFullYear() + "_"
          + new Date(response[i].datetime).getHours() + ":" + new Date(response[i].datetime).getMinutes() + ":" + new Date(response[i].datetime).getSeconds();
        }
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  public getNext(a:any, value:number):boolean{
    var parent = (a as HTMLElement).parentElement;
    if (!parent!.className.includes("disabled")){
			this.left += Number(value) * this.increase;
			this.right = this.left + this.increase;
			
			this.loadElements();
		}
    return false;
  }

  private checkPagination(){
    var myPagination = document.getElementById("myPagination");

    //console.log(obj);

    this.commentService.retrieveNumberOfComments(this.idProduct).subscribe(
      (response: number) => {
        this.numberOfPages = Math.round(response / this.increase) + (response % this.increase === 0 ? 0 : 1);

        if (this.numberOfPages === 0){
          myPagination!.style.display = "none";
        }else{
          myPagination!.style.display = "block";
          if (this.numberOfPages === 1){
            document.getElementById("previous")!.className = "page-item disabled";
            document.getElementById("next")!.className = "page-item disabled";
            (document.getElementById("previous")!.children[0] as HTMLElement).style.color = "gray";
            (document.getElementById("next")!.children[0] as HTMLElement).style.color = "gray";
          }else{
            document.getElementById("previous")!.children[0].className = this.left === 0 ? "page-item disabled" : "page-item";
            (document.getElementById("previous")!.children[0] as HTMLElement).style.color = this.left === 0 ? "gray" : "black";
            document.getElementById("previous")!.className = this.left === 0 ? "page-item disabled" : "page-item";
            document.getElementById("next")!.className = this.right >= response ? "page-item disabled" : "page-item";
            document.getElementById("next")!.children[0].className = this.right >= response ? "page-item" : "page-item normalButton";
            (document.getElementById("next")!.children[0] as HTMLElement).style.color = this.right >= response ? "gray" : "black";
            
            document.getElementById("leftIndex")!.innerHTML = ((this.left / this.increase) + 1) + "";
          }
        }
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  public productBought(a:boolean):any{
    this.flagBought = a;
    this.flagBuy = !a;
  }
}
