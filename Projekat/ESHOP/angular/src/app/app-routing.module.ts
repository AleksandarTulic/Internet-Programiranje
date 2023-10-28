import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './crud_product/create/create.component';
import { RetrieveAllComponent } from './crud_product/retrieve-all/retrieve-all.component';
import { RetrieveByIdComponent } from './crud_product/retrieve-by-id/retrieve-by-id.component';
import { UpdateComponent } from './crud_product/update/update.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { GuardLoginService } from './services/guard-login.service';
import { GuardSignUpService } from './services/guard-sign-up.service';
import { GuardTokenLoginService } from './services/guard-token-login.service';
import { SignUpComponent } from './sign-up/sign-up.component';
import { SupportComponent } from './support/support.component';
import { TokenLoginComponent } from './token-login/token-login.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';

const routes: Routes = [
  {
    path: 'create_product',
    component: CreateComponent,
    canActivate: [GuardLoginService]
  },
  {
    path: 'update_product/:id',
    component: UpdateComponent,
    canActivate: [GuardLoginService]
  },
  {
    path: 'retrieve-all',
    component: RetrieveAllComponent
  },
  {
    path: 'retrieve-by-id/:id',
    component: RetrieveByIdComponent
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [GuardLoginService]
  },
  {
    path: 'profile/:username',
    component: ProfileComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [GuardSignUpService]
  },
  {
    path: 'token-login',
    component: TokenLoginComponent,
    canActivate: [GuardTokenLoginService]
  },
  {
    path: 'support',
    component: SupportComponent,
    canActivate: [GuardLoginService]
  },
  {
    path: 'update-profile',
    component: UpdateProfileComponent,
    canActivate: [GuardLoginService]
  },
  {
    path: '**',
    component: HomeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
