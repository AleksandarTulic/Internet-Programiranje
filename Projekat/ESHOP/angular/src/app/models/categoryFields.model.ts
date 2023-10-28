export class CategoryFields{
    categoryTitle!: string;
    name!: string;
    fieldType!: string;
    regex!: string;
    flagFixedMultipleValues!: number;

    constructor(categoryTitle: string, name: string, fieldType:string, regex: string, flagFixedMultipleValues:number){
        this.categoryTitle = categoryTitle;
        this.name = name;
        this.fieldType = fieldType;
        this.regex = regex;
        this.flagFixedMultipleValues = flagFixedMultipleValues;
    }
}