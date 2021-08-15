package com.njp.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAvailable; //da li je rezervacija dostupna ili je istekla (ako je prosao datum polaska)

    @ManyToOne
    @ToString.Exclude
    //@JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight; //Rezervisani let

    @ManyToOne
    @ToString.Exclude
    //@JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket; //Rezervisana karta

}
