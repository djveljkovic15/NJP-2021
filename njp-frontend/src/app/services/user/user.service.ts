import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Booking } from 'src/app/models/booking';
import { Credentials } from 'src/app/models/credentials.model';
import { LoginRequest } from 'src/app/models/loginrequest';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService{

 
  constructor(private httpClient: HttpClient) { }

  private userURL:string = 'http://localhost:8081/user'

  getNumberOfBookings(username: string){
    return this.httpClient.get<Number>(this.userURL+"/getNumberOfBookings/"+username);
  }
  getBookingsFromUser(username: string):Observable<Booking[]>{
    return this.httpClient.get<Booking[]>(this.userURL+"/getBookingsFromUser/"+username);
  }
  getTypes():Observable<any[]> {
    return this.httpClient.get<any[]>(this.userURL+"/types");
  }
  getUserByUsername(username: string):Observable<User> {
    return this.httpClient.get<User>(this.userURL+"/getByUsername/"+username);
  }
  createUser(user:User):Observable<User> {
    return this.httpClient.post<User>(this.userURL+"/save", user);
  }
}
