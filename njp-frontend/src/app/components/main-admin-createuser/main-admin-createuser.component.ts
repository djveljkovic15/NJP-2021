import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserType } from 'src/app/models/usertype';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-main-admin-createuser',
  templateUrl: './main-admin-createuser.component.html',
  styleUrls: ['./main-admin-createuser.component.css']
})
export class MainAdminCreateuserComponent implements OnInit {


  user:User=new User();
  types:string[]=[];
  selectedType:string="";


  constructor(private userService:UserService) { }

  ngOnInit(): void {
    
    this.getTypes();
  }

  getTypes(){
    this.userService.getTypes().subscribe(data => {
      this.types = data;
    })
  }
  
  createUser(date:NgForm){
    console.log("CreateUser: TYPE = " + this.selectedType);
    
    if(this.selectedType =="ADMIN"){
      this.user.userType = UserType.ADMIN;
    } 
    else {
      this.user.userType = UserType.USER;
    }
    // console.log("CreateUser: USER = " + this.user);
    this.user.bookings = []
    console.log(this.user);
    this.userService.createUser(this.user).subscribe(data => {
      console.log(data);
      alert("User with username: "+this.user.username+" successfuly created.")
      this.user.username="";
      this.user.password="";
      this.selectedType="";
      // this.getUsers();
      // Mozda treba da se ubaci potvrda da je user uspesno napravljen?
    },
      error=> {
        if(error.status == 418){// ja sam tipot!
          alert("User with that username already exists.")
        }else
        if(error.status == 403){
          alert("Forbidden action initiated.")
        }else
          alert("User failed to create.")
        console.error(console.error())
        
        }
      // Mozda treba da se ubaci razliciti response za razlicite greske koje mogu da dodju pri kreiranju novog korisnika. 
      // Ovde bih citao responsove i u zavisnosti od koda koji bih dobio ispisujem razlicite greske.
    );
  }
}
