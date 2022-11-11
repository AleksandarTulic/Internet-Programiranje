import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RssService } from '../rss.service';
import { Feed } from 'src\\app\\model\\feed.model';

@Component({
  selector: 'app-rss',
  templateUrl: './rss.component.html',
  styleUrls: ['./rss.component.css']
})
export class RssComponent implements OnInit {
  public feed: Feed [] = [];
  constructor(private rssService: RssService) { }

  ngOnInit(): void {
    this.getRss();
  }

  private getRss(){
    this.rssService.getRss().subscribe(
      (result: any) => {
        for (var i=0;i<result.items.length;i++){
          this.feed.push(new Feed(result.items[i].author, result.items[i].content, result.items[i].enclosure.link, result.items[i].link, 
            result.items[i].pubDate, result.items[i].title));
        }
      },
      (error: HttpErrorResponse) => {
        alert(error);
      }
    );
  }

  public openNav(){
    document.getElementById("mySidenav")!.style.width = "250px";
    document.getElementById("mySidenav")!.style.borderLeft = "3px solid cyan";
    document.body.style.backgroundColor = "rgba(0,0,0,0)";
  }

  public closeNav(){
    document.getElementById("mySidenav")!.style.width = "0";
    document.getElementById("mySidenav")!.style.borderLeft = "none";
    document.body.style.backgroundColor = "white";
  }

}
