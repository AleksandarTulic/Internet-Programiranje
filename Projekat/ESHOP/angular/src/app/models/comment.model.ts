export class Comment{
    productId!: number;
    consumerId!: number;
    value!: string;

    constructor(productId:number, consumerId:number, value:string){
        this.productId = productId;
        this.consumerId = consumerId;
        this.value = value;
    }
}