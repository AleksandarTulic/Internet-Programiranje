import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsumerService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public retrieveByUsername(username:any) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/consumer/` + username, {observe: 'response'});
  }

  public updateconsumer(obj:any) : Observable<any>{
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

    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.put<any>(`${this.apiServerUrl}/consumer/update`, formData, {headers, observe: 'response'});
  }
}
