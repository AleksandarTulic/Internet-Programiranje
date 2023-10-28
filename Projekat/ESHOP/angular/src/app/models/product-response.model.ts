export class ProductResponse{
    id!: number;
    image!: string;
    title!: string;
    category!: string;
    cost!: number;

    constructor(id:number, image:string, title:string, category: string, cost: number){
        this.id = id;
        this.image = image;
        this.title = title;
        this.category = category;
        this.cost = cost;
    }
}