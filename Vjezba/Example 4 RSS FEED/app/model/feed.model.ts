export class Feed{
    author!: string;
    content!: string;
    linkImg!: string;
    linkRest!: string;
    pubDate!: string;
    title!: string;

    constructor(author: string, content: string, linkImg: string, linkRest: string, pubDate: string, title: string){
        this.author = author;
        this.content = content;
        this.linkImg = linkImg;
        this.linkRest = linkRest;
        this.pubDate = pubDate;
        this.title = title;
    }
}