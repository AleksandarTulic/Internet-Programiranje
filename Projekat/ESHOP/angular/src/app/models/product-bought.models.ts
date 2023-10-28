export class ProductBought{
    productId!: number;
    consumerId!: number;
    paymentType!: string;

    constructor(productId:number, consumerId:number, paymentType:string){
        this.productId = productId;
        this.consumerId = consumerId;
        this.paymentType = paymentType;
    }
}