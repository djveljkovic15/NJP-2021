import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flight } from 'src/app/models/flight';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private httpClient: HttpClient) { }

  private flightURL:string = 'http://localhost:8081/flight'


  createFlight(flight:Flight):Observable<Flight> {
    return this.httpClient.post<Flight>(this.flightURL+"/save", flight);
  }

  getFlights():Observable<Flight[]>{
    return this.httpClient.get<Flight[]>(this.flightURL+"/all");
  }
  
  getFlightById(flightId:number):Observable<Flight>{
    return this.httpClient.get<Flight>(this.flightURL+"/get/"+flightId);
  }
}
