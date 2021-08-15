import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  public sharedNumberOfBookings: BehaviorSubject<Number> = new BehaviorSubject<Number>(1);

  constructor() { }
}
