import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SignUpService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public register(obj:any) : Observable<any>{
    const formData = new FormData();
    formData.append("firstName", obj.firstName);
    formData.append("lastName", obj.lastName);
    formData.append("username", obj.username);
    formData.append("password", obj.password);
    formData.append("email", obj.email);
    formData.append("phone", obj.phone);
    formData.append("city", obj.city);
    if (obj.avatar + '' !== 'undefined'){
      formData.append("avatar", obj.avatar);
    }

    return this.http.post<any>(`${this.apiServerUrl}/registration`, formData);
  }

  public cofirmRegistration(obj:any) : Observable<any>{
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.post(`${this.apiServerUrl}/registration/confirm`, obj, {headers, responseType: 'text'});
  }
}
