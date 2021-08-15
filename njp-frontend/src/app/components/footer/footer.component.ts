import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  
  dateObj: number = Date.now();
  constructor(private router: Router) { }

  ngOnInit(): void {
    setTimeout(() => { this.ngOnInit() }, 1000 )
    this.dateObj  = Date.now();
  }
  navigateHome(){
    this.router.navigate(["/home"]);
  }

}
