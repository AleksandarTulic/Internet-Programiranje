import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public login(obj: any) : Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/user/login`, obj, {observe: 'response'});
  }
}
