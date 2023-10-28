import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductBought } from 'src/app/models/product-bought.models';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductBoughtService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public create(obj:ProductBought):Observable<any>{
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.post<any>(`${this.apiServerUrl}/product_bought/create`, obj, {headers, observe: 'response'});
  }

  public existsByProductId(id:number):Observable<boolean>{
    return this.http.get<boolean>(`${this.apiServerUrl}/product_bought/existsByProductId/` + id);
  }
}
