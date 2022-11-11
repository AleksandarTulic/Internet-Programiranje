import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RssService {

  constructor(private http: HttpClient) { }

  public getRss() : Observable<any>{
    return this.http.get<any>(environment.urlRss);
  }
}
