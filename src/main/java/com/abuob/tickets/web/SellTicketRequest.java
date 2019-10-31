package com.abuob.tickets.web;

import java.math.BigDecimal;

public class SellTicketRequest {

    private Long sellerId;

    private Long eventId;

    private Integer section;

    private Integer quantity;

    private BigDecimal price;

    private String row;

    public SellTicketRequest() {
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "SellTicketRequest{" +
                "sellerId=" + sellerId +
                ", eventId=" + eventId +
                ", section=" + section +
                ", quantity=" + quantity +
                ", price=" + price +
                ", row='" + row + '\'' +
                '}';
    }
}
