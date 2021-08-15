import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Company } from 'src/app/models/company';
import { Flight } from 'src/app/models/flight';
import { Ticket } from 'src/app/models/ticket';
import { TicketType } from 'src/app/models/tickettype';
import { CompanyService } from 'src/app/services/company/company.service';
import { FlightService } from 'src/app/services/flight/flight.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';

@Component({
  selector: 'app-main-admin-createticket',
  templateUrl: './main-admin-createticket.component.html',
  styleUrls: ['./main-admin-createticket.component.css']
})
export class MainAdminCreateticketComponent implements OnInit {

  ticket:Ticket=new Ticket();
  testTicket:Ticket=new Ticket();

  TypeLabel = new Map<number, string>([
    [TicketType.FIRST_CLASS, 'First class'],
    [TicketType.BUSINESS_CLASS, "Business class"]
  ])
  types:TicketType[]=[TicketType.FIRST_CLASS, TicketType.BUSINESS_CLASS];
  selectedType: TicketType = 0;
  
  

  yesnos: string[]=["No","Yes"];
  selectedOneWay: string = this.yesnos[0];

  flight:Flight=new Flight();
  flights: Flight[] = [];
  selectedFlight: Flight = this.flight;
  selectedFlightId!: number;

  company:Company=new Company();
  companies: Company[] = [];
  selectedCompany: Company = this.company;
  selectedCompanyId!: number;

  departDate!: Date;
  returnDate!: Date;
  
  numberOfAvailableTickets: Number = 1;


  constructor(private ticketService:TicketService,
              private flightService:FlightService,
              private companyService:CompanyService) { }

  ngOnInit(): void {
    
    //this.getTypes();
    this.getFlights();
    this.getCompanies();
  }

  getTypes(){
    this.ticketService.getTypes().subscribe(data => {
      this.types = data;
    })
  }
  getFlights(){
    this.flightService.getFlights().subscribe(data => {
      this.flights = data;
    })
    this.selectedFlight = this.flights[0];
  }
  getCompanies(){
    this.companyService.getCompanies().subscribe(data => {
      this.companies = data;
    })
    this.selectedCompany = this.companies[0];
  }
  createTicket(data:NgForm){
    // this.ticket.flight = this.selectedFlight
    // console.log(this.selectedFlight)

    // this.ticket.company = this.selectedCompany
    // console.log(this.selectedCompany)


    this.ticket.ticketType = this.selectedType
    if(this.selectedOneWay == "Yes"){
      this.ticket.oneWay = true;
      this.ticket.returnDate = this.departDate
    }
    else{
      this.ticket.oneWay = false;
      this.ticket.returnDate = this.returnDate
    }
    this.ticket.departDate = this.departDate
    this.ticket.numberOfAvailableTickets = this.numberOfAvailableTickets
    
    
    this.flightService.getFlightById(this.selectedFlightId).subscribe(flightData=>{
      this.ticket.flight = flightData
      this.companyService.getCompanyById(this.selectedCompanyId).subscribe(companyData=>{
        this.ticket.company = companyData

        this.ticketService.createTicket(this.ticket).subscribe(data=>{
          console.log(data);
          alert("Successfully created ticket.")
          // Refresh tiketa u tabeli?
        },
        error=> {
        if(error.status == 403){
          alert("Forbidden action initiated.")
        }else
          alert("Ticket failed to create. Please check if you've entered Depart date correctly.")
        console.error(console.error())})
      }, error=>{
        if(error.status == 400)
          alert("Please select Company from combobox.")
        console.error(console.error())}
      )
    }, error=>{
      if(error.status == 400)
        alert("Please select Flight from combobox.")
      console.error(console.error())}
      )


    // console.log(this.ticket)


    // this.testTicket.flight = this.flights[1];
    // this.testTicket.company = this.companies[0];
    // this.testTicket.ticketType = TicketType.FIRST_CLASS;
    // this.testTicket.one_way = true;
    // this.testTicket.departDate = new Date;
    // this.testTicket.returnDate = new Date;
    // this.testTicket.numberOfAvailableTickets = 25;

    
    // console.log(this.testTicket)
    // this.ticketService.createTicket(this.testTicket).subscribe(data=>{},error=>console.error(console.error()))


    // this.ticketService.createTicket(this.ticket).subscribe(data=>{
 
    // },
    // error=> console.error(console.error()))
  }

  // createUser(data:NgForm){
  //   console.log("CreateUser: TYPE = " + this.selectedType);
    
  //   if(this.selectedType =="ADMIN"){
  //     this.user.userType = UserType.ADMIN;
  //   } 
  //   else {
  //     this.user.userType = UserType.USER;
  //   }
  //   // console.log("CreateUser: USER = " + this.user);
  //   this.user.bookings = []
  //   this.userService.createUser(this.user).subscribe(data => {
  //     console.log(data);
  //     this.user.username="";
  //     this.user.password="";
  //     this.selectedType="";
  //     // this.getUsers();
  //     // Mozda treba da se ubaci potvrda da je user uspesno napravljen?
  //   },
  //     error=>  console.error(console.error())
  //     // Mozda treba da se ubaci razliciti response za razlicite greske koje mogu da dodju pri kreiranju novog korisnika. 
  //     // Ovde bih citao responsove i u zavisnosti od koda koji bih dobio ispisujem razlicite greske.
  //   );
  }