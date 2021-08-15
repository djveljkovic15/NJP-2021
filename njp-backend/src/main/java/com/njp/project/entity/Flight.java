package com.njp.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Entity(name = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"flight"})
//    @JsonManagedReference
    private List<Ticket> tickets;

    @OneToOne//(cascade = {CascadeType.ALL})
    @JoinColumn(name = "originCity_id", referencedColumnName = "id")
    private City origin; //from

    @OneToOne//(cascade = {CascadeType.ALL})
    @JoinColumn(name = "destinationCity_id", referencedColumnName = "id")
    private City destination; //to


}
