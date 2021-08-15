import {UserType} from '../models/usertype';
import { Booking } from './booking';

export class User {
    
    id!:number;
    username!:string;
    password!:string;
    userType!:UserType;
    bookings!: Booking[];
}