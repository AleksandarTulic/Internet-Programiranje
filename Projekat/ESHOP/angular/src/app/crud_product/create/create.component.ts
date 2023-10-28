import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductFieldValues } from 'src/app/models/productFieldValues.model';
import { environment } from 'src/environments/environment';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateComponent implements OnInit {
  public productTitleRegex: string = environment.productTitleRegex;
  public productDescriptionRegex: string = environment.productDescrptionRegex;
  public productPriceMinValue: number = environment.productPriceMinValue;
  
  private categoryInput: string = "";
  private arrProductFieldValues: ProductFieldValues[] = [];
  private arrPictures: File[] = [];

  constructor(private productService:ProductService) { }

  ngOnInit(): void {
    (document.getElementsByClassName("active")[0] as HTMLElement).className = "";
    (document.getElementById("create_product") as HTMLElement)!.className = "active";
  }

  public addImage(img:any) {
    document.getElementById('imageID')!.click();
  }

  public removeImage(){
    document.getElementById("idAddImagesContent")!.innerHTML = "";
  }

  public selectedImages(): any{
    for (let i=0;i<(document.getElementById("imageID") as HTMLInputElement)!.files!.length;i++){
      var f:File = (document.getElementById("imageID") as HTMLInputElement)!.files![i];
      this.arrPictures.push(f);
      var reader = new FileReader();

      reader.onload = function(e) {
        document.getElementById("idAddImagesContent")!.insertAdjacentHTML("afterbegin", "<div style=\"padding: 10px;text-align: center;\" class=\"col-sm-4\"><img src=" + e!.target!.result + " style=\"width:140px;height:140px;\"></div>");
      };

      reader.readAsDataURL((document.getElementById("imageID") as HTMLInputElement)!.files![i]);
    }
  }

  private check(): boolean{
    var value = (document.getElementById("description") as HTMLTextAreaElement).value;

    return new RegExp(environment.productDescrptionRegex).test(value + "");
  }

  public getSelectComponentValues(values:string[]){
    this.arrProductFieldValues = [];
    for (let i=0;i<values.length;i++){
      var obj = JSON.parse(values[i]);

      var productFields = new ProductFieldValues(this.categoryInput, obj.name, -1, obj.value);
      this.arrProductFieldValues.push(productFields);
    }
  }

  public getSelectedCategory(value:string){
    this.categoryInput = value;
  }

  public addProduct(){
    if (this.check() + "" === "true"){
      var product = new Product(Number(1), (document.getElementById("createProductTitle") as HTMLInputElement)!.value, 
      (document.getElementById("description") as HTMLTextAreaElement).value, 
      Number((document.getElementById("createProductPrice")as HTMLInputElement)!.value), 
      (document.getElementById("createProductProductType") as HTMLSelectElement)!.value, this.categoryInput, Number(sessionStorage.getItem("id"))); //3 je consumer ID to treba iz session storage da dohvatimo
    
      product.pictures = this.arrPictures;
      product.fieldValues = this.arrProductFieldValues;

      //console.log(product);

      
      this.productService.create(product).subscribe(
        (response:any) => {
          //console.log(response);

          var res = {
            "result": response.ok
          };

          //(document.getElementById("createProductForm")as HTMLFormElement).reset();

          var showResult = document.getElementById("message");
				  showResult!.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
				
				  setTimeout(function(){
					  document.getElementById("message")!.innerHTML = "";
				  }, 3000);
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );
    }else{
          var showResult = document.getElementById("message");
				  showResult!.innerHTML = "<div class=\"alert alert-danger\">Operation Failed</div>";
				
				  setTimeout(function(){
					  document.getElementById("message")!.innerHTML = "";
				  }, 3000);
    }
  }

}

