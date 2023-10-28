export class ProductFieldValues{
    categoryFieldTitle!: string;
    categoryFieldName!: string;
    productId!: number;
    fieldValue!: string;

    constructor(categoryFieldTitle:string, categoryFieldName:string, productId:number, fieldValue:string){
        this.categoryFieldName = categoryFieldName;
        this.categoryFieldTitle = categoryFieldTitle;
        this.productId = productId;
        this.fieldValue = fieldValue;
    }
}