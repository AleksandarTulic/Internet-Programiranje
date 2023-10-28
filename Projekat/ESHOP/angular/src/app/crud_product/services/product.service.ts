import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'src/app/models/product.model';
import { ProductFieldValues } from 'src/app/models/productFieldValues.model';
import { ProductResponse } from 'src/app/models/product-response.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public create(obj: Product) : Observable<any>{
    const myData = JSON.stringify(obj);
    const formData = new FormData();
    formData.append("", myData);
    formData.append("description", obj.description);
    formData.append("title", obj.title);
    formData.append("cost", obj.cost + "");
    formData.append("productType", obj.productType);
    formData.append("categoryTitle", obj.categoryTitle);
    formData.append("consumerId", obj.consumerId + "");

    for (let i=0;i<obj.fieldValues.length;i++){
      formData.append("fieldValues[" + i + "].categoryFieldName", obj.fieldValues[i].categoryFieldName);
      formData.append("fieldValues[" + i + "].fieldValue", obj.fieldValues[i].fieldValue);
    }

    obj.pictures.forEach((file) => {formData.append("pictures", file)});
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.post<Object>(`${this.apiServerUrl}/product/create`, formData, {headers, observe: 'response'});
  }

  public update(obj: Product) : Observable<any>{
    const myData = JSON.stringify(obj);
    const formData = new FormData();
    formData.append("", myData);
    formData.append("description", obj.description);
    formData.append("title", obj.title);
    formData.append("cost", obj.cost + "");
    formData.append("productType", obj.productType);
    formData.append("categoryTitle", obj.categoryTitle);
    formData.append("consumerId", obj.consumerId + "");
    formData.append("id", obj.id + "");

    for (let i=0;i<obj.fieldValues.length;i++){
      formData.append("fieldValues[" + i + "].categoryFieldName", obj.fieldValues[i].categoryFieldName);
      formData.append("fieldValues[" + i + "].fieldValue", obj.fieldValues[i].fieldValue);
    }

    obj.pictures.forEach((file) => {formData.append("pictures", file)});
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.put<Object>(`${this.apiServerUrl}/product/update`, formData, {headers, observe: 'response'});
  }

  public retrieveById(id:number) : Observable<Product>{
    return this.http.get<Product>(`${this.apiServerUrl}/product/` + id);
  }

  public retrieveImagesById(id:number) : Observable<Object[]>{
    return this.http.get<Object[]>(`${this.apiServerUrl}/product_pictures/` + id);
  }

  public retrieveFieldValuesOnUpdate(id: number, categoryTitle: string) : Observable<Object[]>{
    return this.http.get<Object[]>(`${this.apiServerUrl}/product_field_values/` + id + `/` + categoryTitle);
  }

  public retrieveAllProducts(obj:any) : Observable<ProductResponse[]>{
    return this.http.post<ProductResponse[]>(`${this.apiServerUrl}/product`, obj);
  }

  public retrieveNumberOf(obj:any) : Observable<number>{
    return this.http.post<number>(`${this.apiServerUrl}/product/retrieveNumberOf`, obj);
  }

  public deleteProduct(id:number) : Observable<any>{
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(sessionStorage.getItem('username') + ':' + sessionStorage.getItem('password'))});
    return this.http.delete<any>(`${this.apiServerUrl}/product/delete/` + id, {headers, observe: 'response'});
  }

  public exists(id:number) : Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/product/exists/` + id);
  }
}
