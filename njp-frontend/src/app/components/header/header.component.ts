import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { DataSharingService } from 'src/app/services/dataSharing/data-sharing.service';
import { LoginService } from 'src/app/services/login/login.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn?: Observable<Boolean>
  username!: string 
  userType!: string
  numberOfBookings!: Number
  isAdmin!:boolean

  constructor(private dataSharingService: DataSharingService,
              private loginService: LoginService,
              private userService: UserService,
              private router: Router) {

    this.isLoggedIn = this.loginService.isLoggedIn
    if(this.isLoggedIn){
      setTimeout(() => { this.ngOnInit() }, 1000 * 10)
      this.username = window.localStorage.getItem("username")!
      this.userType = window.localStorage.getItem("userType")!

      if(this.userType == "USER"){
        this.getNumberOfBookings()
        this.isAdmin = false
      }
      if(this.userType == "ADMIN")
        this.isAdmin = true

        this.dataSharingService.sharedNumberOfBookings.subscribe(value=>{
          this.numberOfBookings = value
          console.log("Header-> value: " + this.numberOfBookings)
          this.getNumberOfBookings()
          console.log("Header-> getNumberOfBookings: " + this.numberOfBookings);
        })
    }
    
  }
  getNumberOfBookings() {
    this.userService.getNumberOfBookings(this.username).subscribe(data =>{
      this.numberOfBookings = data
    })
  }

  logout(){
    this.isLoggedIn = this.loginService.isLoggedIn
    this.username = ""
    this.userType = ""
    this.loginService.logout()
    this.router.navigate(["/login"]);
  }

  showUserBookings(){
    this.router.navigate(["/bookings"]);
  }

  ngOnInit(): void {
  }


}

// localStorage.removeItem("jwt")
// localStorage.removeItem("userType")
// localStorage.removeItem("username")