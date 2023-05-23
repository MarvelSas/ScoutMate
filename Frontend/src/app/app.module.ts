import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {RequestInterceptor} from "./request.interceptor";
import { JwtModule } from "@auth0/angular-jwt";
import { ClarityModule } from "@clr/angular";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ConfirmemailComponent } from './confirmemail/confirmemail.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SideNavComponent } from './side-nav/side-nav.component';

import '@cds/core/icon/register.js';
import '@cds/core/select/register.js';
import {logoutIcon, noteIcon, ClarityIcons, userIcon, tasksIcon, cogIcon, infoCircleIcon, bullseyeIcon, timesCircleIcon, flameIcon, plusIcon, loginIcon} from '@cds/core/icon';
import { ManageProfileComponent } from './manage-profile/manage-profile.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { CreateOrgComponent } from './create-org/create-org.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { CreateInstructorattemptComponent } from './create-instructorattempt/create-instructorattempt.component';
import { CreateMeritbadgeattemptComponent } from './create-meritbadgeattempt/create-meritbadgeattempt.component';
import { CreateScoutattemptComponent } from './create-scoutattempt/create-scoutattempt.component';
import { EditOrgComponent } from './edit-org/edit-org.component';
import { BadgesrankDashboardComponent } from './badgesrank-dashboard/badgesrank-dashboard.component';
import {SecurePipe} from "./SecurePipe";
import { ManageOrgComponent } from './manage-org/manage-org.component';
import { ManageSuborgComponent } from './manage-suborg/manage-suborg.component';
import { ManageOrgCommanderComponent } from './manage-org-commander/manage-org-commander.component';
import { ManageScoutProfileComponent } from './manage-scout-profile/manage-scout-profile.component';
import { ManageAdminRightsComponent } from './manage-admin-rights/manage-admin-rights.component';
import { OrderDashboardComponent } from './order-dashboard/order-dashboard.component';
import { ManageOrgMembersComponent } from './manage-org-members/manage-org-members.component';
import { BadgesrankHomeComponent } from './badgesrank-home/badgesrank-home.component';
import {CdsFileModule} from "@cds/angular";


ClarityIcons.addIcons(logoutIcon, noteIcon, userIcon, tasksIcon, cogIcon, infoCircleIcon, bullseyeIcon, timesCircleIcon, flameIcon, plusIcon, loginIcon,);
ClarityIcons.addIcons(['plus-circle','<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>']);
ClarityIcons.addIcons(['x','<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>']);
ClarityIcons.addIcons(['img','<svg xmlns="http://www.w3.org/2000/svg" width="19" height="19" viewBox="0 0 24 25" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/><path d="M20.4 14.5L16 10 4 20"/></svg>']);
ClarityIcons.addIcons(['man','<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>']);

export function tokenGetter() {
  return localStorage.getItem("access_token");
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ResetPasswordComponent,
    ConfirmemailComponent,
    ChangePasswordComponent,
    SideNavComponent,
    ManageProfileComponent,
    UserProfileComponent,
    CreateOrgComponent,
    CreateOrderComponent,
    CreateInstructorattemptComponent,
    CreateMeritbadgeattemptComponent,
    CreateScoutattemptComponent,
    EditOrgComponent,
    BadgesrankDashboardComponent,
    SecurePipe,
    ManageOrgComponent,
    ManageSuborgComponent,
    ManageOrgCommanderComponent,
    ManageScoutProfileComponent,
    ManageAdminRightsComponent,
    OrderDashboardComponent,
    ManageOrgMembersComponent,
    BadgesrankHomeComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        JwtModule.forRoot({
            config: {
                tokenGetter: tokenGetter,
                allowedDomains: ['localhost:8080']
            }
        }),
        BrowserAnimationsModule,
        ClarityModule,
        CdsFileModule
    ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
