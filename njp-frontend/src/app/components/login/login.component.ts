import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { LoginService } from 'src/app/services/login/login.service'
import { throwError } from 'rxjs'
import { LoginRequest } from 'src/app/models/loginrequest'


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup
  errorHandler!: boolean
  loginRequest: LoginRequest

  constructor(
              private loginService: LoginService,
              private router: Router
              ){
      

      this.loginForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
      });
      this.loginRequest = {username:"",password:""}
    }

  ngOnInit(): void {
    this.loginService.logout();
  }

  login(){
    
    // console.log("username: " + this.loginForm.get('username')!.value)
    // console.log("password: " + this.loginForm.get('password')!.value)
    
    this.loginRequest.username = this.loginForm.get('username')!.value
    this.loginRequest.password = this.loginForm.get('password')!.value
    this.loginService.login(this.loginRequest).subscribe(data => {
      
      // console.log("Log iz LoginComponent: " + localStorage.getItem("jwt"));

      if(!this.loginService.ifLoggedIn){
        this.loginService.logout()
        this.router.navigate(['/login'])
      }else{
        this.errorHandler = false
        this.router.navigate(['/home'])
      }
    }, error => {
      if(error.status == 403){
        alert("Invalid username and password.")
      }
      this.errorHandler = true
      console.log(error)
    }
    )
  }
}
