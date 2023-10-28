import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { CreateComponent } from './crud_product/create/create.component';
import { UpdateComponent } from './crud_product/update/update.component';
import { SelectCategoryComponent } from './crud_product/create/select-category/select-category.component';
import { HttpClientModule } from '@angular/common/http';
import { RetrieveAllComponent } from './crud_product/retrieve-all/retrieve-all.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { RetrieveByIdComponent } from './crud_product/retrieve-by-id/retrieve-by-id.component';
import { BuyProductComponent } from './crud_product/buy-product/buy-product.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoginComponent } from './login/login.component';
import { TokenLoginComponent } from './token-login/token-login.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { SupportComponent } from './support/support.component';
import { ProductsPaginationComponent } from './profile/products-pagination/products-pagination.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CreateComponent,
    UpdateComponent,
    SelectCategoryComponent,
    RetrieveAllComponent,
    HomeComponent,
    ProfileComponent,
    RetrieveByIdComponent,
    BuyProductComponent,
    SignUpComponent,
    LoginComponent,
    TokenLoginComponent,
    UpdateProfileComponent,
    SupportComponent,
    ProductsPaginationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
