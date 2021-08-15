import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './auth/auth-interceptor';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { LoginService } from './services/login/login.service';
import { MainComponent } from './components/main/main.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { BookingsComponent } from './components/bookings/bookings.component';
import { MainAdminCreateuserComponent } from './components/main-admin-createuser/main-admin-createuser.component';
import { MainAdminCreateticketComponent } from './components/main-admin-createticket/main-admin-createticket.component';
import { MainTableTicketsComponent } from './components/main-table-tickets/main-table-tickets.component';
import { EditTicketComponent } from './components/edit-ticket/edit-ticket.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    HeaderComponent,
    FooterComponent,
    BookingsComponent,
    MainAdminCreateuserComponent,
    MainAdminCreateticketComponent,
    MainTableTicketsComponent,
    EditTicketComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    AuthGuard, LoginService, 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
