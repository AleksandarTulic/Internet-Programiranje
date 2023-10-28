import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryFields } from 'src/app/models/categoryFields.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryFieldsService {
  private apiServerUrl = environment.apiBaseUrl;
  
  constructor(private http: HttpClient) { }

  public retrieveCategoryFields(title:string) : Observable<CategoryFields[]>{
    return this.http.get<CategoryFields[]>(`${this.apiServerUrl}/categoryFields/` + title);
  }
}
