import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { Booking } from 'src/app/models/booking';
import { BookingService } from 'src/app/services/booking/booking.service';
import { DataSharingService } from 'src/app/services/dataSharing/data-sharing.service';
import { LoginService } from 'src/app/services/login/login.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {


  isLoggedIn?: Observable<Boolean>
  userType!: string
  username!: string 
  
  bookings:Booking[]=[]


  TypeLabel = new Map<boolean, string>([
    [true, "Available"],
    [false, "Not available"]
  ])
  constructor(private dataSharingService: DataSharingService,
              private loginService: LoginService,
              private ticketService: TicketService,
              private bookingService: BookingService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn
    if(this.isLoggedIn){
      this.userType = window.localStorage.getItem("userType")! 
      this.username = window.localStorage.getItem("username")! 
      if(this.userType!="USER")
        this.router.navigate(["/login"]);

    }else
      this.router.navigate(["/login"]);

    this.getBookings()

    

  }


  
  getBookings(){
    // this.userService.getUserByUsername(this.username).subscribe(data=>{
    //   this.bookings = data.bookings
    //   console.log(this.bookings)

    // })

    this.bookingService.getBookingsByUsername(this.username).subscribe(data=>{
      this.bookings = data
      console.log(this.bookings);
    })
  }
  // getBookings(){
  //   this.userService.getBookingsFromUser(this.username).subscribe(data=>{
  //     if(data==null)
  //       this.bookings = []
  //     else
  //       this.bookings = data
  //   })
  // }

  cancelBooking(bookingId: number){  //this.username, this.booking
    this.bookingService.cancelBooking(this.username, bookingId).subscribe(data=>{
      this.getBookings()
      if(data)
        console.log("TRUE: " + data)
      else
        console.log("FALSE: " + data)
      this.refreshHeader()

    }, error=>{
      if(error.status == 406){
        alert("You can only cancel booking before 24hours till flight.")
      }else
        alert("Couldn't cancel(delete) booking.")
    })

  }

  buyoutBooking(bookingId: number){
    this.bookingService.buyBooking(this.username, bookingId).subscribe(data=>{
      this.getBookings()
      if(data)
        console.log("TRUE: " + data)
      else
        console.log("FALSE: " + data)
      this.refreshHeader()

    }, error=>{
      if(error.status == 406){
        alert("Not acceptable.")
      }else
        alert("Couldn't buy booking.")
    })
  }

  refreshHeader(){
    this.dataSharingService.sharedNumberOfBookings.next(this.bookings.length)
  }

}
