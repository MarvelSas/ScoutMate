import { NgModule } from '@angular/core';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component"
import {HomeComponent} from "./home/home.component";
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationGuard} from "./authentication.guard";
import {OrgownerGuard} from "./orgowner.guard";
import {AdminGuard} from "./admin.guard";
import {ConfirmemailComponent} from "./confirmemail/confirmemail.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {ManageProfileComponent} from "./manage-profile/manage-profile.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {CreateOrgComponent} from "./create-org/create-org.component";
import {CreateOrderComponent} from "./create-order/create-order.component";
import {CreateInstructorattemptComponent} from "./create-instructorattempt/create-instructorattempt.component";
import { CreateMeritbadgeattemptComponent } from './create-meritbadgeattempt/create-meritbadgeattempt.component';
import { CreateScoutattemptComponent } from './create-scoutattempt/create-scoutattempt.component';
import { EditOrgComponent } from './edit-org/edit-org.component';
import { BadgesrankDashboardComponent } from './badgesrank-dashboard/badgesrank-dashboard.component';
import { ManageOrgComponent } from './manage-org/manage-org.component';
import {ManageSuborgComponent} from './manage-suborg/manage-suborg.component';
import {ManageOrgCommanderComponent} from './manage-org-commander/manage-org-commander.component';
import {ManageScoutProfileComponent} from './manage-scout-profile/manage-scout-profile.component';
import {ManageAdminRightsComponent} from './manage-admin-rights/manage-admin-rights.component';
import {OrderDashboardComponent} from './order-dashboard/order-dashboard.component';
import {ManageOrgMembersComponent} from './manage-org-members/manage-org-members.component';
import {BadgesrankHomeComponent} from './badgesrank-home/badgesrank-home.component';

const routes: Routes = [
  {path: 'forgetpassword', component:ResetPasswordComponent},
  {path: 'login', component:LoginComponent},
  {path: 'register',component:RegisterComponent},
  {path: 'confirm', component: ConfirmemailComponent},
  {path: 'changepassword', component: ChangePasswordComponent},
  {path: '', canActivate:[AuthenticationGuard], children: [
      {path: '', canActivate: [AdminGuard], children:[
          {path: 'manageadminrights' , component:ManageAdminRightsComponent}
        ]},
      {path: '', canActivate: [OrgownerGuard], children: [
          {path: 'badgesrankhome' ,component:BadgesrankHomeComponent},
          {path: 'manageorg' ,component:ManageOrgComponent},
          {path: 'badgesrankdashboard' ,component:BadgesrankDashboardComponent},
          {path: 'editorg' ,component:EditOrgComponent},
          {path: 'createorg' ,component:CreateOrgComponent},
          {path: 'manageorgmembers' ,component:ManageOrgMembersComponent},
          {path: 'orderdashboard' ,component:OrderDashboardComponent},
          {path: 'createorder' ,component:CreateOrderComponent},
          {path: 'createinsturctorattempt' ,component:CreateInstructorattemptComponent},
          {path: 'createscouttempt' ,component:CreateScoutattemptComponent},
          {path: 'createmeritbadgeattempt' ,component:CreateMeritbadgeattemptComponent},
          {path: 'managesuborg' ,component:ManageSuborgComponent},
          {path: 'manageorgcommander' ,component:ManageOrgCommanderComponent},
          {path: 'managescoutprofile' ,component:ManageScoutProfileComponent}
        ]},
      {path: 'home' ,component:HomeComponent},
      {path: 'manageprofile' ,component:ManageProfileComponent},
      {path: 'userprofile' ,component:UserProfileComponent},
      {path: '' ,component:HomeComponent}
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
