package com.njp.project.repository;

import com.njp.project.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>, JpaRepository<Ticket, Long> {

//    @Query(value = "SELECT DISTINCT ticket_type from ticket WHERE ticket_type LIKE 'FIRST_CLASS'  OR ticket_type LIKE 'BUSINESS_CLASS' ", nativeQuery = true)
    @Query(value = "SELECT DISTINCT ticket_type from ticket", nativeQuery = true)
    List<String> getTypes();

    Page<Ticket> findAllByOneWay(Pageable pageable, boolean one_way);



    @Query(value = "SELECT * FROM ticket t " +
            "JOIN flight f on t.flight_id = f.id " +
            "JOIN city destination on destination.id = f.destination_city_id " +
            "JOIN city origin on origin.id = f.origin_city_id " +
            "WHERE (:originName IS NULL OR origin.name = :originName) " +
            "AND (:destinationName IS NULL OR destination.name = :destinationName)" +
            "AND (:departDate IS NULL OR t.depart_date >= :departDate)" +
            "AND (:returnDate IS NULL OR t.return_date <= :returnDate)" , nativeQuery = true)
    List<Ticket> findBookingsByFilters(@Param("originName") String originName, @Param("destinationName") String destinationName,
                                        @Param("departDate") Date departDate, @Param("returnDate") Date returnDate);
}
/*
    private String originName;          // ticket.flight.origin.name
    private String destinationName;     // ticket.flight.destination.name
    private Date departDate;            // ticket.departDate
    private Date returnDate;
 */
