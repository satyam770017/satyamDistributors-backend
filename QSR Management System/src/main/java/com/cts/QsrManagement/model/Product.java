package com.cts.QsrManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class
git config --global user.name "satyam770017"Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    private String category;

    private double price;
    private int stockAvailable;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<Cart> cart = new HashSet<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems = new HashSet<>();
}
