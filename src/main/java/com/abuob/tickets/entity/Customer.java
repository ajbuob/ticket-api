package com.abuob.tickets.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "cust_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private Integer zip;

    @OneToMany(mappedBy = "seller", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Inventory> inventorySold = Sets.newHashSet();

    @OneToMany(mappedBy = "buyer", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Inventory> inventoryBought = Sets.newHashSet();


    protected Customer() {
    }

    //For data loading purposes
    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Set getInventorySold() {
        return inventorySold;
    }

    public void setInventorySold(Set inventorySold) {
        this.inventorySold = inventorySold;
    }

    public Set getInventoryBought() {
        return inventoryBought;
    }

    public void setInventoryBought(Set inventoryBought) {
        this.inventoryBought = inventoryBought;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("city", city)
                .add("state", state)
                .add("zip", zip)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equal(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
