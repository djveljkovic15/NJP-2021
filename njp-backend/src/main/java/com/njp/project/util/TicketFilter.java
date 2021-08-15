package com.njp.project.util;

import lombok.Data;

import java.util.Date;

@Data
public class TicketFilter{

    private String originName;          // ticket.flight.origin.name
    private String destinationName;     // ticket.flight.destination.name
    private Date departDate;            // ticket.departDate
    private Date returnDate;

}
