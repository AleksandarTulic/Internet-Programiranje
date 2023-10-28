import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsumerService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public retrieveAvatar(username:string, avatar:string) : Observable<any>{
    return this.http.get<string>(`${this.apiServerUrl}/consumer/` + username + `/` + avatar);
  }
}
