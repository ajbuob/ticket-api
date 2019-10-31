package com.abuob.tickets.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "visit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate;

    @Column(name = "source")
    private String source;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    protected Referral() {
    }

    public Referral(Date visitDate, String source, Customer customer) {
        this.visitDate = visitDate;
        this.source = source;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("visitDate", visitDate)
                .add("source", source)
                .add("customer", customer)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referral referral = (Referral) o;
        return Objects.equal(id, referral.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
