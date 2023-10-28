import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/models/comment.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public retrieveByProductId(id:number, left:number, right:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/comment/` + id + `/` + left + `/` + right);
  }

  public retrieveNumberOfComments(id:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/comment/retrieveNumberOfComments/` + id);
  }

  public createComment(obj:Comment) : Observable<any>{
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.post<any>(`${this.apiServerUrl}/comment/create`, obj, {headers, observe: 'response'});
  }
}
