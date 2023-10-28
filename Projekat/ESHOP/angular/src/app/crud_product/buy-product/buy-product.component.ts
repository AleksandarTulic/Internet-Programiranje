import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ProductBought } from 'src/app/models/product-bought.models';
import { environment } from 'src/environments/environment';
import { ProductBoughtService } from '../services/product-bought.service';

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {
  public arr: any[] = [];
  @Input() idProduct: number = 0;
  @Input() idConsumer: number = 0;

  @Output() EmitFlagBought = new EventEmitter<boolean>();

  constructor(private productBoughtService: ProductBoughtService) { }

  ngOnInit(): void {
    this.arr = JSON.parse(environment.paymentMethods);
  }

  public buyProduct(){
    (document.getElementById("message") as HTMLElement).innerHTML = "Poruka od Buy Product";
    var success = environment.paymentSuccessful;

    var valueRandom = this.getRandomInteger();
    if (valueRandom < success){
      var objBuy = new ProductBought(this.idProduct, this.idConsumer, (document.getElementById("idTypeBuyProduct") as HTMLSelectElement).value);
      this.EmitFlagBought.emit(true);

      
      this.productBoughtService.create(objBuy).subscribe(
        (response: any) => {
          //console.log(response);
        },
        (error: HttpErrorResponse) => {
          alert(error);
        }
      );
    }

    var showResult = document.getElementById("message");
    showResult!.innerHTML = "<div class=\"" + (valueRandom < success ? "alert alert-success" : "alert alert-danger") + "\"> " + (valueRandom < success ? "Operation Successful" : "Operation Failed") + "</div>";
  
    setTimeout(function(){
      document.getElementById("message")!.innerHTML = "";
    }, 3000);
  }

  private getRandomInteger():number{
    const maxValue = 100;
    return Math.floor(Math.random() * maxValue)
  }

}
