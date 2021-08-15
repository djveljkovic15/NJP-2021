package com.njp.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.njp.project.model.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
//    @JsonIgnore
    private Company company;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    private Boolean oneWay; // da li je karta u jednom pravcu ili povratna

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date departDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date returnDate; // samo kod povratnih karata

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"tickets"})
//    @JsonBackReference
    @ToString.Exclude
    private Flight flight;

    @Min(value = 0)
    private Long numberOfAvailableTickets; // "count", broj dostupnih karata (>= 0)


    public synchronized void incrementNumberOfAvailableTickets(){
        numberOfAvailableTickets++;
    }
}
