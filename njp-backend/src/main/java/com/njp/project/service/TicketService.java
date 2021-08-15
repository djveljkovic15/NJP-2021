package com.njp.project.service;


import com.njp.project.entity.Ticket;
import com.njp.project.entity.User;
import com.njp.project.util.TicketFilter;

import java.util.List;

public interface TicketService {
    Ticket save(Ticket ticket);

    Ticket update(Long id, Ticket ticket);

    void deleteById(Long id);

    Ticket findById(Long id);

    List<Ticket> findAll();

    List<String> getTypes();

    Ticket reserve(Ticket ticket, Long numberOfTickets);

    List<Ticket> findAllPaginated(Integer pageNo, Integer pageSize);//, String sortBy);

    List<Ticket> findAllPaginatedBasedOnWay(Integer pageNo, Integer pageSize, boolean oneWay);

    List<Ticket> filterTickets(TicketFilter ticketFilter);


}
