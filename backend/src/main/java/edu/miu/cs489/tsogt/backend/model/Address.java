package edu.miu.cs489.tsogt.backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "* address is required")
    private String address;
    @Column(nullable = false)
    @NotBlank(message = "* city is required")
    private String city;
    @Column(nullable = false)
    @NotBlank(message = "* state is required")
    private String state;
    @Column(nullable = false)
    @NotNull(message = "* zip code is required")
    private int zipCode;

    public Address(String address, String city, String state, int zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
