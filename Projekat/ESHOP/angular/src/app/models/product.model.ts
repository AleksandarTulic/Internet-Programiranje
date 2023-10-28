import { ProductFieldValues } from "./productFieldValues.model";

export class Product{
    id!: number;
    title!: string;
    description!: string;
    cost!: number;
    productType!: string;
    categoryTitle!: string;
    consumerId!: number;
    pictures!: File[];
    fieldValues!: ProductFieldValues[];

    constructor(id: number, title: string, description: string, cost: number, productType: string, categoryTitle: string, consumerId: number){
        this.id = id;
        this.title = title;
        this.description = description;
        this.consumerId = consumerId;
        this.cost = cost;
        this.productType = productType;
        this.categoryTitle = categoryTitle;
    }
}