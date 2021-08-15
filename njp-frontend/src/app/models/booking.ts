import {Flight} from './flight';
import {Ticket} from './ticket';

export class Booking {
    
    id!:number;
    isAvailable!:boolean;
    flight!:Flight;
    ticket!:Ticket;
}