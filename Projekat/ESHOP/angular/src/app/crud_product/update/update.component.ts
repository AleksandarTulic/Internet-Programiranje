import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/product.model';
import { ProductFieldValues } from 'src/app/models/productFieldValues.model';
import { environment } from 'src/environments/environment';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UpdateComponent implements OnInit {
  public productTitleRegex: string = environment.productTitleRegex;
  public productDescriptionRegex: string = environment.productDescrptionRegex;
  public productPriceMinValue: number = environment.productPriceMinValue;
  public arrImages: string[] = [];
  public updateProduct: Product | undefined;

  private categoryInput: string = "";
  private arrProductFieldValues: ProductFieldValues[] = [];
  private arrPictures: File[] = [];
  private routeSub: Subscription | undefined;
  private idProduct: number = 0;
  
  constructor(private route: ActivatedRoute, private productService:ProductService) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.idProduct = Number(params['id']);
    });

    this.productService.retrieveById(this.idProduct).subscribe(
      (response: Product) => {
        this.updateProduct = response;

        (document.getElementById("createProductTitle") as HTMLInputElement).value = response.title;
        (document.getElementById("createProductProductType") as HTMLSelectElement).value = response.productType;
        (document.getElementById("createProductPrice") as HTMLInputElement).value = response.cost + "";
        (document.getElementById("selectCategory") as HTMLSelectElement).value = response.categoryTitle;
        (document.getElementById("description") as HTMLTextAreaElement).value = response.description;

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
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  public addImage(img:any) {
    document.getElementById('imageID')!.click();
  }

  public removeImage(){
    this.arrPictures = [];
    document.getElementById("idAddImagesContent")!.innerHTML = "";
  }

  public selectedImages(): any{
    for (let i=0;i<(document.getElementById("imageID") as HTMLInputElement)!.files!.length;i++){
      var f:File = (document.getElementById("imageID") as HTMLInputElement)!.files![i];
      this.arrPictures.push(f);
      var reader = new FileReader();

      reader.onload = function(e) {
        document.getElementById("idAddImagesContent")!.insertAdjacentHTML("afterbegin", "<div style=\"padding: 10px;text-align: center;\" class=\"col-sm-4\"><img class=\"importedImages\" src=" + e!.target!.result + " style=\"width:140px;height:140px;\"></div>");
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
    this.getRandomAcceptableName();
    if (this.check() + "" === "true"){
      var product = new Product(this.updateProduct!.id, (document.getElementById("createProductTitle") as HTMLInputElement)!.value, 
      (document.getElementById("description") as HTMLTextAreaElement).value, 
      Number((document.getElementById("createProductPrice")as HTMLInputElement)!.value), 
      (document.getElementById("createProductProductType") as HTMLSelectElement)!.value, this.categoryInput, Number(sessionStorage.getItem("id"))); //3 je consumer ID to treba iz session storage da dohvatimo

      //console.log("V1: " + this.arrPictures);
      product.pictures = this.arrPictures;
      product.fieldValues = this.arrProductFieldValues;
      
      this.productService.update(product).subscribe(
        (response:any) => {

          var res = {
            "result": response.ok
          }

          
          var showResult = document.getElementById("message");
				  showResult!.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
				
				  setTimeout(function(){
					  document.getElementById("message")!.innerHTML = "";
				  }, 3000);
        
          this.addPreviousPictures(0);
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

  ngOnDestroy() {
    this.routeSub!.unsubscribe();
  }

  public async addPreviousPictures(j:number){
    this.arrPictures = [];
    var arrImportedImages = document.getElementsByClassName("importedImages");
    if (j==0){
      for (let i=0;i<arrImportedImages.length;i++){
          await fetch((arrImportedImages[i] as HTMLImageElement).src).then(a1 => a1.blob()).then(blob => {
          this.arrPictures.push(new File([blob], this.getRandomAcceptableName() + "_" + i + ".png", blob));
        });
      }
    }
  }

  private getRandomAcceptableName():string{
    var result = "";

    const arrString: string[] = [];
    this.arrPictures.forEach(t => {
      arrString.push(t.name);
    });

    do{
      result = this.makeid(8);
    }while(arrString.includes(result));

    return result;
  }

  private makeid(length:number):string {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;

    for ( var i = 0; i < length; i++ )
        result += characters.charAt(Math.floor(Math.random() * charactersLength));

    return result;
}

}
