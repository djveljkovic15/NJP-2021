package com.njp.project.entity;

import com.njp.project.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String username;

    //Implementirati enkripciju, npr odavde: https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)
    //@JoinColumn(name = "booking_id", referencedColumnName = "id")
    private List<Booking> bookings;


}
