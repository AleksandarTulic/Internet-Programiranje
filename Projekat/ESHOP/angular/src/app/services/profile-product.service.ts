import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProfileProductService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public retrieveProductsNotBought(idConsumer:number, left: number, right:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveProductsNotBought/` + idConsumer + "/" + left + "/" + right);
  }

  public retrieveNumberOfProductsNotBought(idConsumer:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveNumberOfProductsNotBought/` + idConsumer);
  }

  public retrieveProductsBought(idConsumer:number, left: number, right:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveProductsBought/` + idConsumer + "/" + left + "/" + right);
  }

  public retrieveNumberOfProductsBought(idConsumer:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveNumberOfProductsBought/` + idConsumer);
  }

  public retrieveProductsUserBought(idConsumer:number, left: number, right:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveProductsUserBought/` + idConsumer + "/" + left + "/" + right);
  }

  public retrieveNumberOfProductsUserBought(idConsumer:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/retrieveNumberOfProductsBought/` + idConsumer);
  }
}
