import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Ticket } from 'src/app/models/ticket';
import { TicketFilter } from 'src/app/models/ticketFilter';
import { DataSharingService } from 'src/app/services/dataSharing/data-sharing.service';
import { LoginService } from 'src/app/services/login/login.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { UserService } from 'src/app/services/user/user.service';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-main-table-tickets',
  templateUrl: './main-table-tickets.component.html',
  styleUrls: ['./main-table-tickets.component.css']
})
export class MainTableTicketsComponent implements OnInit, OnDestroy {

  isLoggedIn?: Observable<Boolean>
  username!: string 
  userType!: string
  isAdmin!: boolean

  tickets: Ticket[] = []

  ways: string[] = ["One way", "Two way", "All"]
  // ways: string[] = ["Only depart", "Depart with Return", "Depart & Depart with Return"]
  selectedWay: string = this.ways[2]

  // numberOfAvailableTickets:number[]=[].fill(1)

  numberOfAvailableTickets:Number[] = [1,1,1,1,1,1,1,1,1,1]

  public ticket!: Ticket;

  pageNo:number = 0
  pageSize:number = 5

  ticketFilter: TicketFilter = new TicketFilter;

  constructor(private dataSharingService: DataSharingService,
              private userService: UserService,
              private loginService: LoginService,
              private ticketService: TicketService,
              private router: Router) { }
  
  ngOnDestroy(): void {
  }


  ngOnInit(): void {
    
    this.numberOfAvailableTickets.fill(1);

    this.isLoggedIn = this.loginService.isLoggedIn
    if(this.isLoggedIn){
      this.userType = window.localStorage.getItem("userType")! 
    }else
      this.router.navigate(["/login"]);

    if(this.userType == "USER")
      this.isAdmin = false
    if(this.userType == "ADMIN")
      this.isAdmin = true
    


    this.pageNo = 0
    this.pageSize = 5
    // this.getTickets()
    console.log("OI1: " + this.pageNo +this.pageSize  )
    this.getTicketsPaginated()
    console.log("OI2: " + this.pageNo +this.pageSize  )

  }

  getTickets(){
    this.ticketService.getTickets().subscribe(data => {
      this.tickets = data;
      this.numberOfAvailableTickets.fill(1);
      console.log(this.tickets)
    })
  }

  searchBasedOnWay(){
    this.ticketService.getTicketsPaginatedBasedOnway(this.pageNo,this.pageSize, this.selectedWay).subscribe(data => {
      if(!data.length)
        this.pageNo = this.pageNo > 0?this.pageNo - 1:0
        //this.paginateBack()
      else{
        this.tickets = data;
        this.numberOfAvailableTickets.fill(1);
      }
      // this.tickets = data;
      // this.numberOfAvailableTickets.fill(1);
      console.log(this.tickets)
    })
  }

  getTicketsPaginated(){
    if(this.selectedWay=="All")
      this.ticketService.getTicketsPaginated(this.pageNo,this.pageSize).subscribe(data => {
        if(!data.length)
          this.pageNo = this.pageNo > 0?this.pageNo - 1:0
          //this.paginateBack()
        else{
          this.tickets = data;
        }
        this.numberOfAvailableTickets.fill(1);
        console.log(this.tickets)
      })
    
    this.searchBasedOnWay();
    this.numberOfAvailableTickets.fill(1);
  }

  paginateBack(){
    // this.pageNo = this.pageNo-1
    if(this.pageNo){
      this.pageNo = this.pageNo > 0?this.pageNo - 1:0
      console.log("PB: " + this.pageNo)
      this.getTicketsPaginated()
    }
      this.pageNo = this.pageNo > 0?this.pageNo - 1:0
  }

  paginateNext(){
    this.pageNo = this.pageNo+1
    // this.pageNo = this.pageNo > 0?this.pageNo - 1:0
    console.log("PN: " + this.pageNo)
    this.getTicketsPaginated()
  }

  deleteTicket(id:number){
    this.ticketService.deleteTicket(id).subscribe(data => {
        // this.getTickets()
      this.getTicketsPaginated()
    })
  }

  editTicket(id:number){
    this.router.navigate(["/edit-ticket/"+id]); // /edit-ticket/3
    // this.router.navigate(['/edit-ticket'], { queryParams: { id: id }, queryParamsHandling: 'preserve' });  /edit-ticket&id=3
  }

  reserveTicket(ticket:Ticket){
    // console.log("Rezervisano je "+ this.numberOfAvailableTickets[ticket.id-1] +" karata sa id-jem "+ ticket.id)
   
    this.ticketService.reserveTicket(ticket, this.numberOfAvailableTickets[ticket.id-1]).subscribe(data=>{
        // this.getTickets()
      this.getTicketsPaginated()
      
      this.dataSharingService.sharedNumberOfBookings.next(this.numberOfAvailableTickets[ticket.id-1])
      this.numberOfAvailableTickets[ticket.id-1] = data.numberOfAvailableTickets
      this.numberOfAvailableTickets.fill(1);
    })
  }

  // Ali na ovaj nacin nemam sacuvanu paginaciju i ta sranja vec mi samo filteruje..
  filterTickets(){
    this.ticketService.filterTickets(this.ticketFilter).subscribe(data=>{
      this.tickets = data
    })
  }
  clearFilter(){
    this.ticketFilter = new TicketFilter
    this.pageNo=0
    this.pageSize=5

    this.ticketService.getTicketsPaginated(this.pageNo,this.pageSize).subscribe(data => {
      this.pageNo = this.pageNo > 0?this.pageNo - 1:0

      this.tickets = data;
      this.numberOfAvailableTickets.fill(1);

    })
  }




}
