import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ProductResponse } from 'src/app/models/product-response.model';
import { ProfileProductService } from 'src/app/services/profile-product.service';

@Component({
  selector: 'app-products-pagination',
  templateUrl: './products-pagination.component.html',
  styleUrls: ['./products-pagination.component.css']
})
export class ProductsPaginationComponent implements OnInit {
  public arr: ProductResponse[] = [];
  public _idConsumer: number = 0;
  public _whichOperation: string = "activeProducts"
  
  @Input() 
  set whichOperation(value: string){
    this._whichOperation = value;
    this.executeLoad();
  }
  get whichOperation(): string{
    return this._whichOperation;
  }
  
  @Input()
  set idConsumer(value: number){
    this._idConsumer = value;
    this.executeLoad();
  }
  get idConsumer(): number {
    return this._idConsumer;
  }

  public left: number = 0;
  public right: number = 10;
  private increase: number = 10;
  private numberOfPages: number = 0;

  constructor(private profileProductService: ProfileProductService) { }

  ngOnInit(): void {
  }

  private executeLoad(){
    if (this._whichOperation + '' === "activeProducts"){
      this.loadElementsNotBought();
    }else if (this._whichOperation + '' === "finishedProducts"){
      this.loadElementsBought();
    }else if (this._whichOperation + '' === "productsBought"){
      this.loadElementsUserBought();
    }
  }

  private loadElementsUserBought():any{
    this.checkPaginationUserBought();

    //console.log("Doslo ...");
    this.profileProductService.retrieveProductsUserBought(this._idConsumer, this.left, this.right).subscribe(
      (response: ProductResponse[]) => {
        this.arr = response;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private loadElementsBought():any{
    this.checkPaginationBought();

    this.profileProductService.retrieveProductsBought(this._idConsumer, this.left, this.right).subscribe(
      (response: ProductResponse[]) => {
        this.arr = response;
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private loadElementsNotBought():any{
    this.checkPaginationNotBought();

    this.profileProductService.retrieveProductsNotBought(this._idConsumer, this.left, this.right).subscribe(
      (response: ProductResponse[]) => {
        this.arr = response;
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
			

      if (this._whichOperation + '' === "activeProducts"){
        this.loadElementsNotBought();
      }else if (this._whichOperation + '' === "finishedProducts"){
        this.loadElementsBought();
      }else if (this._whichOperation + '' === "productsBought"){
        this.loadElementsUserBought();
      }
		}
    return false;
  }

  private checkPaginationNotBought(){
    this.profileProductService.retrieveNumberOfProductsNotBought(this._idConsumer).subscribe(
      (response: number) => {
        this.numberOfPages = Math.round(response / this.increase) + (response % this.increase === 0 ? 0 : 1);

        this.checkPaginationElements(response);
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private checkPaginationUserBought(){
    this.profileProductService.retrieveNumberOfProductsUserBought(this._idConsumer).subscribe(
      (response: number) => {
        this.numberOfPages = Math.round(response / this.increase) + (response % this.increase === 0 ? 0 : 1);

        this.checkPaginationElements(response);
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private checkPaginationBought(){
    this.profileProductService.retrieveNumberOfProductsBought(this._idConsumer).subscribe(
      (response: number) => {
        this.numberOfPages = Math.round(response / this.increase) + (response % this.increase === 0 ? 0 : 1);

        this.checkPaginationElements(response);
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private checkPaginationElements(response:number){
    var myPagination = document.getElementById("myPagination_" + this._whichOperation);

    if (this.numberOfPages === 0){
      myPagination!.style.display = "none";
    }else{
      myPagination!.style.display = "block";
      if (this.numberOfPages === 1){
        document.getElementById("previous_" + this._whichOperation)!.className = "page-item disabled";
        document.getElementById("next_" + this._whichOperation)!.className = "page-item disabled";
        (document.getElementById("previous_" + this._whichOperation)!.children[0] as HTMLElement).style.color = "gray";
        (document.getElementById("next_" + this._whichOperation)!.children[0] as HTMLElement).style.color = "gray";
      }else{
        document.getElementById("previous_" + this._whichOperation)!.children[0].className = this.left === 0 ? "page-item disabled" : "page-item";
        (document.getElementById("previous_" + this._whichOperation)!.children[0] as HTMLElement).style.color = this.left === 0 ? "gray" : "black";
        document.getElementById("previous_" + this._whichOperation)!.className = this.left === 0 ? "page-item disabled" : "page-item";
        document.getElementById("next_" + this._whichOperation)!.className = this.right >= response ? "page-item disabled" : "page-item";
        document.getElementById("next_" + this._whichOperation)!.children[0].className = this.right >= response ? "page-item" : "page-item normalButton";
        (document.getElementById("next_" + this._whichOperation)!.children[0] as HTMLElement).style.color = this.right >= response ? "gray" : "black";
        
        document.getElementById("leftIndex_" + this._whichOperation)!.innerHTML = ((this.left / this.increase) + 1) + "";
      }
    }
  }

  public viewMore(id:number){
    window.location.href = "retrieve-by-id/" + id;
  }

}
