import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { MainComponent } from './components/main/main.component';
import { BookingsComponent } from './components/bookings/bookings.component';
import { EditTicketComponent } from './components/edit-ticket/edit-ticket.component';

const routes: Routes = [
  { path: '', component: LoginComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent},
  { path: 'home', component: MainComponent},
  { path: 'bookings', component: BookingsComponent},
  // { path: 'edit-ticket', component: EditTicketComponent},
  { path: 'edit-ticket/:ticketId', component: EditTicketComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
