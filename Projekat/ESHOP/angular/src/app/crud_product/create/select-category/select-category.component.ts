import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Category } from 'src/app/models/category.model';
import { CategoryFields } from 'src/app/models/categoryFields.model';
import { Product } from 'src/app/models/product.model';
import { ProductFieldValues } from 'src/app/models/productFieldValues.model';
import { CategoryFieldsService } from '../../services/category-fields.service';
import { CategoryService } from '../../services/category.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-select-category',
  templateUrl: './select-category.component.html',
  styleUrls: ['./select-category.component.css']
})
export class SelectCategoryComponent implements OnInit {
  public arrCategory:Category[] = [];
  public arrCategoryFields: CategoryFields[] = [];
  public arrSendData: string[] = [];
  @Output() EmitCategoryFieldsInput = new EventEmitter<string[]>();
  @Output() EmitCategoryIntput = new EventEmitter<string>();

  @Input() updateProduct: Product | undefined;
  public arrProductFieldValues: any[] = [];
  public flagUpdate: boolean = false;
  constructor(private categorySerice: CategoryService, private categoryFieldsService: CategoryFieldsService, private productService: ProductService) { }

  public TYPE_FIELD = {
    "ONE": "NUMBER",
    "TWO": "TEXT",
    "THREE": "FIXED"
  };
  
  ngOnInit(): void {
    this.retrieveCategories();
  }

  private emitValuesStart(){
    this.EmitCategoryFieldsInput.emit(this.arrSendData);
  }

  public valueChanged():any{
    this.arrSendData = [];
    this.valueChangedAdding(this.TYPE_FIELD.TWO);
    this.valueChangedAdding(this.TYPE_FIELD.ONE);
    this.valueChangedAdding(this.TYPE_FIELD.THREE);

    this.emitValuesStart();
  }

  private valueChangedAdding(value: string){
    var arr = document.getElementsByClassName(value);
  
    for (let i=0;i<arr.length;i++){
      var obj = {
        "type": this.TYPE_FIELD.TWO,
        "name": arr[i].id,
        "value": (arr[i].nodeName + "" === "INPUT" ? arr[i] as HTMLInputElement : arr[i] as HTMLSelectElement).value
      };

      this.arrSendData.push(JSON.stringify(obj));
    }
  }

  public retrieveCategories():any{
    this.categorySerice.retieveCategories().subscribe(
      (response: Category[]) => {
        this.arrCategory = response;

        if (this.updateProduct + "" !== "undefined"){
          this.productService.retrieveFieldValuesOnUpdate(this.updateProduct!.id, this.updateProduct!.categoryTitle).subscribe(
            (response: any) => {
              this.arrProductFieldValues = response;
              this.flagUpdate = true;

              //console.log(this.arrProductFieldValues);
              this.EmitCategoryIntput.emit(this.updateProduct?.categoryTitle);
              this.onStartValueUpdate();
            },
            (error: HttpErrorResponse) => {
              alert(error);
            }
          );
        }else{
          this.retrieveFields(response[0]!.title);
          this.EmitCategoryIntput.emit(response[0]!.title);
        }
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private retrieveFields(value:string):any{
    this.categoryFieldsService.retrieveCategoryFields(value).subscribe(
      (response: CategoryFields[]) => {
        this.arrCategoryFields = response;
        this.onStartValueAdding();
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  private onStartValueAdding(){
    this.arrSendData = [];

    for (let i=0;i<this.arrCategoryFields.length;i++){
      var obj = {
        "type": this.arrCategoryFields[i].categoryTitle,
        "name": this.arrCategoryFields[i].name,
        "value": this.arrCategoryFields[i].fieldType + "" === this.TYPE_FIELD.ONE ? this.arrCategoryFields[i].regex.split('|')[0] : 
        this.arrCategoryFields[i].fieldType + "" === this.TYPE_FIELD.TWO ?
        "" : this.arrCategoryFields[i].regex.split('|')[0].split('^')[1]
      };

      this.arrSendData.push(JSON.stringify(obj));
    }

    this.emitValuesStart();
  }

  private onStartValueUpdate(){
    this.arrSendData = [];

    for (let i=0;i<this.arrProductFieldValues.length;i++){
      var obj = {
        "type": this.arrProductFieldValues[i].categoryFieldTitle,
        "name": this.arrProductFieldValues[i].categoryFieldName,
        "value": this.arrProductFieldValues[i].fieldValue
      };

      this.arrSendData.push(JSON.stringify(obj));
    }

    this.emitValuesStart();
  }

  public retrieveFieldsSelect(value:any){
    this.flagUpdate = false;
    this.updateProduct = undefined;
    this.retrieveFields((value as HTMLSelectElement).value);
    
    this.EmitCategoryIntput.emit((value as HTMLSelectElement).value);
  }

}
