import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { Credentials } from 'src/app/models/credentials.model';
import { LoginRequest } from 'src/app/models/loginrequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService implements OnDestroy{

  constructor(private httpClient: HttpClient) { }

  private loginURL:string = 'http://localhost:8081/auth/login'

  loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false)

  get isLoggedIn(){
    return this.loggedIn.asObservable()
  }

  ngOnDestroy(): void {
    this.logout()
  }

  login(credentials:LoginRequest){
    localStorage.setItem("username", credentials.username)
    let httpParams = new HttpParams()

    httpParams.append("username", credentials.username)
    httpParams.append("password", credentials.password)

    return this.httpClient.post<Credentials>(this.loginURL, credentials).pipe(map( responseData => {
        this.loggedIn.next(true);
        window.localStorage.setItem("jwt", responseData.jwt)
        window.localStorage.setItem("userType", responseData.userType)
        //window.localStorage.setItem("username", responseData.username)
        // console.log("LoginService: JWT(responsedata) = " +  responseData.jwt)
        // console.log("LoginService: JWT(window.localstor) = " +  window.localStorage.getItem("jwt"))
        // console.log("LoginService: JWT(localstor) = " +  localStorage.getItem("jwt"))
      }))

  }

  logout(){
    this.loggedIn.next(false)
    localStorage.removeItem("jwt")
    localStorage.removeItem("userType")
    localStorage.removeItem("username")
  }

  ifLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  getJwtToken() {
    return window.localStorage.retrieve('jwt');
  }
}
