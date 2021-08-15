import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Booking } from 'src/app/models/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {


  constructor(private httpClient: HttpClient) { }

  private bookingURL:string = 'http://localhost:8081/booking'


  createBooking(booking:Booking):Observable<Booking> {
    return this.httpClient.post<Booking>(this.bookingURL+"/save", booking);
  }

  getBookings():Observable<Booking[]>{
    return this.httpClient.get<Booking[]>(this.bookingURL+"/all");
  }
  
  getBookingsById(bookingId:number):Observable<Booking>{
    return this.httpClient.get<Booking>(this.bookingURL+"/get/"+bookingId);
  }
  deleteBookingById(bookingId:number){
    return this.httpClient.delete(this.bookingURL+"/delete/"+bookingId);
  }
  cancelBooking(username:string, bookingId:number):Observable<boolean>{
    return this.httpClient.delete<boolean>(this.bookingURL+"/cancel/"+username+"/"+bookingId);
  }
  buyBooking(username:string, bookingId:number):Observable<boolean>{
    return this.httpClient.delete<boolean>(this.bookingURL+"/buy/"+username+"/"+bookingId);
  }
  getBookingsByUsername(username:string):Observable<Booking[]>{
    return this.httpClient.get<Booking[]>(this.bookingURL+"/username/"+username);
  }
}
