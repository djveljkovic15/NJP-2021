import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Company } from 'src/app/models/company';
import { Flight } from 'src/app/models/flight';
import { Ticket } from 'src/app/models/ticket';
import { TicketType } from 'src/app/models/tickettype';
import { CompanyService } from 'src/app/services/company/company.service';
import { FlightService } from 'src/app/services/flight/flight.service';
import { LoginService } from 'src/app/services/login/login.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';

@Component({
  selector: 'app-edit-ticket',
  templateUrl: './edit-ticket.component.html',
  styleUrls: ['./edit-ticket.component.css']
})
export class EditTicketComponent implements OnInit {


  ticket!:Ticket

  id!:any
  
  ways: string[]=["Yes","No"];

  TypeLabel = new Map<number, string>([
    [TicketType.FIRST_CLASS, 'First class'],
    [TicketType.BUSINESS_CLASS, "Business class"]
  ])
  ticketTypes:TicketType[]=[TicketType.FIRST_CLASS, TicketType.BUSINESS_CLASS];
  //selectedType: TicketType = 0;
  
  companies : Company[] = []
  flights : Flight[] = []

  company!:Company
  selectedTicketType:TicketType = TicketType.BUSINESS_CLASS
  selectedOneWay!: string 
  departDate!: Date
  returnDate!: Date
  flight!: Flight
  numberOfAvailableTickets!:Number


  constructor(private route: ActivatedRoute,
              private router: Router,
              private ticketService: TicketService,
              private flightService:FlightService,
              private companyService:CompanyService) {
    this.route.params.subscribe( params => {
      this.id = params.ticketId
      console.log(params)
      console.log(this.id)
    } );
}
  ngOnInit(): void {
    this.findTicketById()
    this.getFlights()
    this.getCompanies()
  }

  findTicketById() {
    this.ticketService.getTicketById(this.id).subscribe(data=>{
      this.ticket = data
        
      this.company = this.ticket.company
      this.departDate = this.ticket.departDate 
      this.returnDate = this.ticket.returnDate
      this.flight = this.ticket.flight
      this.numberOfAvailableTickets = this.ticket.numberOfAvailableTickets

      if(this.ticket.oneWay)
        this.selectedOneWay = this.ways[0]
      else
        this.selectedOneWay = this.ways[1]

      if(this.ticket.ticketType == TicketType.BUSINESS_CLASS)
        this.selectedTicketType = TicketType.BUSINESS_CLASS
      else
        this.selectedTicketType= TicketType.FIRST_CLASS
      console.log("Editing ticket: " + this.ticket.ticketType)
      console.log("Editing ticket: " + this.selectedTicketType)
    })
  }
  
  updateTicket(){
    if(this.selectedOneWay == "Yes"){
      this.ticket.oneWay = true;
      this.ticket.returnDate = this.departDate
    }
    else{
      this.ticket.oneWay = false;
      this.ticket.returnDate = this.returnDate
    }
    
    if(this.selectedTicketType == TicketType.BUSINESS_CLASS)
      this.ticket.ticketType = TicketType.BUSINESS_CLASS
    else
      this.ticket.ticketType = TicketType.FIRST_CLASS

    this.ticket.company = this.company
    this.ticket.departDate = this.departDate
    this.ticket.flight = this.flight
    this.ticket.numberOfAvailableTickets = this.numberOfAvailableTickets
  
    console.log(this.ticket)

    this.ticketService.updateTicket(this.id, this.ticket).subscribe(data =>{
      this.ticket = data
      console.log(this.ticket)
      alert("Ticket successfuly updated.")
    },error=>{
      if(error.status == 403){
        alert("Forbidden action initiated.")
      }else
      if(error.status == 409){
        alert("Conflict happened.")
      }else
      if(error.status == 418){
        alert("You're trying to edit non-existant ticket.")
      }else
        alert("Ticket failed to update.")
    })

  }

  cancelEdit(){
    this.router.navigate(['/home']);
  }
  getFlights(){
    this.flightService.getFlights().subscribe(data => {
      this.flights = data;
      console.log("Flights: " + this.flights)
    })
    // this.selectedFlight = this.flights[0];
  }
  getCompanies(){
    this.companyService.getCompanies().subscribe(data => {
      this.companies = data;
      console.log("Companies: " + this.companies)
    })
    // this.selectedCompany = this.companies[0];
  }


  // isLoggedIn?: Observable<Boolean>
  // username!: string 
  // userType!: string

  // id!: any;

  // constructor(private loginService: LoginService,
  //             private router: Router,
  //             private route: ActivatedRoute) {}
  //             ​
  //             ​
       
  // ngOnInit() {
  //   this.route.queryParams.subscribe(params => {
  //     this.id = params['id'];
  //     console.log(this.id)
  //   });
  // }
  // ngOnInit(): void {

  //   this.isLoggedIn = this.loginService.isLoggedIn
  //   if(this.isLoggedIn){
  //     this.userType = window.localStorage.getItem("userType")! 
  //   }else
  //     this.router.navigate(["/login"]);
  //   if(this.userType == "USER")
  //     this.router.navigate(["/home"]);


  //   this.id = this.route.snapshot.paramMap.get('id')
  //   console.log(this.id)
  // }


  getTicketById(){}

}
