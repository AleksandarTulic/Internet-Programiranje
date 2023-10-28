import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Category } from 'src/app/models/category.model';
import { ProductResponse } from 'src/app/models/product-response.model';
import { environment } from 'src/environments/environment';
import { CategoryService } from '../services/category.service';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-retrieve-all',
  templateUrl: './retrieve-all.component.html',
  styleUrls: ['./retrieve-all.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RetrieveAllComponent implements OnInit {
  public productType1: string = environment.product_type_1;
  public productType2: string = environment.product_type_2;
  public productType3: string = environment.product_type_3;
  public productTitleRegex: string = environment.productTitleRegex;
  public cityRegex: string = environment.cityRegex;
  public arrCategory: Category[] = [];

  public left: number = 0;
  public right: number = 10;
  private increase: number = 10;
  private numberOfPages: number = 0;

  public arrProductResponse: ProductResponse[] = [];
  constructor(private productService: ProductService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    (document.getElementsByClassName("active")[0] as HTMLElement).className = "";
    (document.getElementById("retrieve-all") as HTMLElement).className = "active";

    this.categoryService.retieveCategories().subscribe(
      (response: Category[]) => {
        this.arrCategory = response;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );

    this.loadElements();
  }

  public loadElementsButton():any{
    this.left = 0;
    this.right = this.increase;
    this.numberOfPages = 0;
    (document.getElementById("leftIndex") as HTMLElement).innerHTML = "1";

    if ((this.check() || (document.getElementById("description") as HTMLTextAreaElement).value + "" === "") && document.forms[1].checkValidity()){
      var obj = this.createObject();
      this.checkPagination();

      this.productService.retrieveAllProducts(obj).subscribe(
        (response: ProductResponse[]) => {
          this.arrProductResponse = response;
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );
    }
  }

  private check(): boolean{
    var value = (document.getElementById("description") as HTMLTextAreaElement).value;

    return new RegExp(environment.productDescrptionRegex).test(value + "");
  }

  public viewMore(id:number):any{
    window.location.href = window.location.href + "/../retrieve-by-id/" + id;
  }

  private loadElements():any{
    this.checkPagination();
    var obj = this.createObject();

    this.productService.retrieveAllProducts(obj).subscribe(
      (response: ProductResponse[]) => {
        this.arrProductResponse = response;
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
    var obj = this.createObject();

    //console.log(obj);

    this.productService.retrieveNumberOf(obj).subscribe(
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

  private createObject():any{
    var obj = {
      "productTitle": (document.getElementById("productTitle") as HTMLInputElement).value,
      "categoryTitle": (document.getElementById("categoryTitle") as HTMLInputElement).value,
      "productType": (document.getElementById("productType") as HTMLSelectElement).value,
      "city": (document.getElementById("city") as HTMLInputElement).value,
      "lowerCost": (document.getElementById("lowerCost") as HTMLInputElement).value + "" === "" ? null : (document.getElementById("lowerCost") as HTMLInputElement).value,
      "upperCost": (document.getElementById("upperCost") as HTMLInputElement).value + "" === "" ? null : (document.getElementById("upperCost") as HTMLInputElement).value,
      "description": (document.getElementById("description") as HTMLTextAreaElement).value,
      "left": this.left,
      "right": this.right
    };

    return obj;
  }

}
