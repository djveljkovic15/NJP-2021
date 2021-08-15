import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ticket } from 'src/app/models/ticket';
import { TicketFilter } from 'src/app/models/ticketFilter';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
 
 
  constructor(private httpClient: HttpClient) { }

  private ticketURL:string = 'http://localhost:8081/ticket'


  getTypes():Observable<any[]> {
    return this.httpClient.get<any[]>(this.ticketURL+"/types");
  }

  createTicket(ticket:Ticket):Observable<Ticket> {
    return this.httpClient.post<Ticket>(this.ticketURL+"/save", ticket);
  }
  updateTicket( ticketId: number, ticket:Ticket):Observable<Ticket> {
    return this.httpClient.put<Ticket>(this.ticketURL+"/update/"+ticketId, ticket);
  }
  getTicketById(ticketId:number):Observable<Ticket>{
    return this.httpClient.get<Ticket>(this.ticketURL+"/get/"+ticketId);
  }

  getTickets():Observable<Ticket[]> {
    return this.httpClient.get<Ticket[]>(this.ticketURL+"/all")
  }

  deleteTicket(id:number) {
    return this.httpClient.delete(this.ticketURL+"/delete/"+id);
  }

  reserveTicket(ticket:Ticket,numberOfReservedTickets:Number):Observable<Ticket> {
    return this.httpClient.post<Ticket>(this.ticketURL+"/reserve/"+numberOfReservedTickets, ticket);
  }

  getTicketsPaginated(pageNo:number, pageSize:number):Observable<Ticket[]>{
    // pageNo = pageNo > 0?pageNo - 1:0
    return this.httpClient.get<Ticket[]>(this.ticketURL+"/paginated?pageSize="+pageSize+"&pageNo="+pageNo)
  }
  
  getTicketsPaginatedBasedOnway(pageNo: number, pageSize: number, selectedWay: string) {
    // ["One way", "Two way", "All"]
    if(selectedWay=="One way")
      return this.httpClient.get<Ticket[]>(this.ticketURL+"/paginated?pageSize="+pageSize+"&pageNo="+pageNo+"&way="+1)
    if(selectedWay=="Two way")
      return this.httpClient.get<Ticket[]>(this.ticketURL+"/paginated?pageSize="+pageSize+"&pageNo="+pageNo+"&way="+2)
    if(selectedWay=="All")
      return this.httpClient.get<Ticket[]>(this.ticketURL+"/paginated?pageSize="+pageSize+"&pageNo="+pageNo+"&way="+0)

    return this.httpClient.get<Ticket[]>(this.ticketURL+"/paginated?pageSize="+pageSize+"&pageNo="+pageNo)
  }

  filterTickets(ticketFilter:TicketFilter):Observable<Ticket[]>{
    return this.httpClient.post<Ticket[]>(this.ticketURL+"/filter", ticketFilter);
  }


}
