import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from 'src/app/services/login/login.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  
  isLoggedIn?: Observable<Boolean>
  username!: string 
  userType!: string

  showUserSearchForm: boolean = false;
  showAdminCreateUser: boolean = false;
  showAdminCreateTicket: boolean = false;

  constructor(private userService: UserService,
              private loginService: LoginService,
              private router: Router) { }


  ngOnInit(): void {

    this.showUserSearchForm = false
    this.showAdminCreateUser = false
    this.showAdminCreateTicket = false


    this.isLoggedIn = this.loginService.isLoggedIn
    if(this.isLoggedIn){
      this.username = window.localStorage.getItem("username")!
      this.userType = window.localStorage.getItem("userType")! 
    }else
      this.router.navigate(["/login"]);

    if(this.userType == "USER"){
      this.showUserSearchForm = true
    }
    if(this.userType == "ADMIN"){
      this.showAdminCreateUser = true
      this.showAdminCreateTicket = true
    }
    
  }


}



// constructor(private loginService: LoginService,
//   private userService: UserService,
//   private router: Router) {
// this.isLoggedIn = this.loginService.isLoggedIn
// if(this.isLoggedIn){
// this.username = window.localStorage.getItem("username")!
// this.userType = window.localStorage.getItem("userType")!

// if(this.userType == "USER")
// this.getNumberOfBookings()
// else
// this.numberOfBookings = 666
// }

// }