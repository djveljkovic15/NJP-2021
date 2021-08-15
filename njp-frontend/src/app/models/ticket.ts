import { Company } from './company';
import {Flight} from './flight';
import { TicketType } from './tickettype';

export class Ticket {
    
    id!:number;
    company!:Company;
    ticketType!: TicketType;
    oneWay!: boolean;
    departDate!:Date;
    returnDate!:Date;
    flight!:Flight;
    numberOfAvailableTickets!:Number;
}