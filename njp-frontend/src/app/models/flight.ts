
import {Ticket} from './ticket';
import {City} from './city';

export class Flight {
   
   id!:number;
   tickets!: Ticket[];
   origin!:City;
   destination!:City;
}